package com.xiaohshi.qlexpress.loader;

import com.xiaohshi.qlexpress.model.ParamModel;
import com.xiaohshi.qlexpress.model.StepModel;
import com.xiaohshi.qlexpress.model.WorkflowModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.yaml.snakeyaml.Yaml;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * 扫描workflow包里面的yml文件，构建出工作流信息，也可以注入到spring中，利用@PostConstruct或者init启动时执行
 */
@Slf4j
public class WorkflowLoader{

    // 所有workflow对象
    @Getter
    private final static Map<String, WorkflowModel> modelMap = new ConcurrentHashMap<>();

    // 执行步骤的映射，其中key的构造为：workflowName + "_" + stepName，后续函数的执行通过这个命名去获得对象并执行
    @Getter
    private final static Map<String, StepModel> workflowMap = new ConcurrentHashMap<>();

    // 函数参数的信息
    @Getter
    private final static Map<String, List<ParamModel>> paramMap = new ConcurrentHashMap<>();

    public static void init() {
        List<String> workflowList = new ArrayList<>();

        // 通过反射获得workflow包下面所有的yml文件
        Reflections rfls = new Reflections("workflow", new ResourcesScanner());
        Set<String> ymlFiles = rfls.getResources(Pattern.compile(".*\\.yml"));
        log.info("workflowDefinition: " + ymlFiles.toString());

        Yaml yaml = new Yaml();

        // 将yml中的文件转化为workflowModel对象
        ymlFiles.stream()
                .map(file -> WorkflowLoader.class.getResourceAsStream("/" + file))
                .filter(Objects::nonNull)
                .map(inputStream -> yaml.loadAs(inputStream, WorkflowModel.class))
                .forEach(workflowModel -> {
                    String workflowName = workflowModel.getWorkflowName();
                    if (workflowList.contains(workflowName)) {
                        throw new IllegalStateException("workflow:" + workflowName + " already exists");
                    }
                    if (StringUtils.isBlank(workflowModel.getType()) || StringUtils.isBlank(workflowModel.getApp())) {
                        throw new IllegalStateException("app or type is null, please check");
                    }
                    workflowList.add(workflowName);
                    modelMap.put(workflowName, workflowModel);
                });

        // 构造StepModel对象
        Consumer<WorkflowModel> consumer = workflow -> workflow.getSteps().forEach(step -> {
            // 利用workflowName_stepName这种形式可以获取到确定的步骤
            String template = workflow.getWorkflowName() + "_" + step.getName();

            String[] handler = step.getHandler().split("::");
            try {
                Class<?> clazz = Class.forName(handler[0]);

                Arrays.stream(clazz.getMethods())
                        .filter(method -> handler[1].equals(method.getName()))
                        .forEach(method -> setParamMap(template, method));

                StepModel stepModel = StepModel.builder()
                        .workflowName(workflow.getWorkflowName())
                        .stepName(step.getName())
                        .className(clazz.getName())
                        .methodName(handler[1])
                        .clazz(clazz)
                        .bind(step.getBind())
                        .inputList(paramMap.get(template))
                        .build();

                workflowMap.put(template, stepModel);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e.getMessage());
            }
        });

        modelMap.values().forEach(consumer);
    }

    // 根据方法反射获得方法的参数名和参数类型
    private static void setParamMap(String template, Method method) {
        Parameter[] parameters = method.getParameters();
        Class<?>[] parameterTypes = method.getParameterTypes();
        List<ParamModel> inputList = new ArrayList<>();
        for (int i = 0; i < parameters.length; i ++) {
            ParamModel paramModel = ParamModel.builder().name(parameters[i].getName())
//                    .type(parameterTypes[i].getName())
                    .paramClass(parameterTypes[i])
                    .build();
            inputList.add(paramModel);
        }
        if (!inputList.isEmpty()) {
            paramMap.put(template, inputList);
        }
    }

    // 根据名称去获得相应的Class对象
    @Deprecated
    private static Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Fail to get Class object\n" + e.getMessage());
        }
    }

}
