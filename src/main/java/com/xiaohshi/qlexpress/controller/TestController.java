package com.xiaohshi.qlexpress.controller;

import com.xiaohshi.qlexpress.excute.Runner;
import com.xiaohshi.qlexpress.loader.WorkflowLoader;
import com.xiaohshi.qlexpress.model.ParamModel;
import com.xiaohshi.qlexpress.model.WorkflowModel;
import com.xiaohshi.qlexpress.utils.SpringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 测试
 */
@Controller
public class TestController {

    // 测试使用，该项目并不是一个web项目，只是测试系统是否可以正确运行
    // 测试url:http://localhost:8080/test/cpimonthly对应CpiMonthlyTask
    // 测试url:http://localhost:8080/test/cpsmonthly对应CpsMonthlyTask
    @GetMapping("/test/{workflow}")
    @ResponseBody
    public String test(@PathVariable String workflow) {
        WorkflowModel workflowModel = WorkflowLoader.getModelMap().get(workflow);

        workflowModel.getSteps().forEach(step -> {

            String template = workflow + "_" + step.getName();

            StringBuilder methodName = new StringBuilder(template + "(");

            // 构造执行方法名
            Object[] params = null;
            List<ParamModel> inputs = WorkflowLoader.getParamMap().get(template);
            if (inputs != null) {
                params = new Object[]{123, "xiaohshi"};
                inputs.forEach(input -> methodName.append(input.getName()).append(","));
                methodName.deleteCharAt(methodName.length() - 1);
            }
            methodName.append(")");

            Runner runner = SpringUtil.getBean(step.getBind());
            runner.execute(workflow, step.getName(),  methodName.toString(), params);
        });
        return "test";
    }

}
