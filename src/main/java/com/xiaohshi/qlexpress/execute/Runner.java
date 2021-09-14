package com.xiaohshi.qlexpress.execute;

import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import com.xiaohshi.qlexpress.loader.QLExpressContext;
import com.xiaohshi.qlexpress.loader.WorkflowLoader;
import com.xiaohshi.qlexpress.model.ParamModel;
import com.xiaohshi.qlexpress.model.StepModel;
import com.xiaohshi.qlexpress.utils.SpringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 方便使用策略模式
 */
public abstract class Runner {

    public abstract void execute(String workflow, String step, String text, Object...params);

    protected void executeMyTask(ExpressRunner runner
            , String workflow, String step, String text, Object...params) throws Exception {
        // 保存业务运行的函数参数
        Map<String, Object> map = new HashMap<>();

        Map<String, StepModel> stepModelMap = WorkflowLoader.getWorkflowMap();

        StepModel stepModel = stepModelMap.get(workflow + "_" + step);

        // 相应函数的参数的Class对象数组
        Class<?>[] classes;
        List<ParamModel> inputList = stepModel.getInputList();
        if (inputList != null) {
            classes = inputList.stream().map(ParamModel::getParamClass)
                    .collect(Collectors.toList())
                    .toArray(new Class[]{});

            for (int i = 0; i < params.length; i ++) {
                map.put(inputList.get(i).getName(), params[i]);
            }
        } else {
            classes = new Class[]{};
        }

        // 利用规则引擎QLExpress，可以通过workflowName+ "_" + stepName这种字符串去获得相应的函数并执行
        // QLExpress的上下文，用于存放函数的参数的具体值
        IExpressContext<String,Object> expressContext = new QLExpressContext(map);
        // 只需要第一次运行的时候将函数加入到runner中
        if (runner.getFunciton(workflow + "_" + step) == null) {
            runner.addFunctionOfServiceMethod(workflow + "_" + step
                    , SpringUtil.getContext().getBean(stepModel.getClazz())
                    , stepModel.getMethodName(), classes, null);
        }
        runner.execute(text, expressContext, null, true, false);
    }

}
