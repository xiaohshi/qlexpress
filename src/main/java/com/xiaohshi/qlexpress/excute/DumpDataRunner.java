package com.xiaohshi.qlexpress.excute;

import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import com.xiaohshi.qlexpress.loader.QLExpressContext;
import com.xiaohshi.qlexpress.loader.WorkflowLoader;
import com.xiaohshi.qlexpress.model.StepModel;
import com.xiaohshi.qlexpress.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 下载数据的模板执行器
 * 需要设置beanName
 */
@Slf4j
@Component("DumpDataRunner")
public class DumpDataRunner extends Runner {

    @Autowired
    private ExpressRunner runner;

    @Override
    public void execute(String workflow, String step, String text, Object...params) {
        Map<String, Object> map = new HashMap<>();
        IExpressContext<String,Object> expressContext = new QLExpressContext(map);

        Map<String, StepModel> stepModelMap = WorkflowLoader.getWorkflowMap();

        StepModel stepModel = stepModelMap.get(workflow + "_" + step);
        try{
            log.info("执行下载数据");
            if (runner.getFunciton(workflow + "_" + step) == null) {
                runner.addFunctionOfServiceMethod(workflow + "_" + step
                        , SpringUtil.getContext().getBean(stepModel.getClazz())
                        , stepModel.getMethodName(), new Class[]{},null);
            }
            runner.execute(text, expressContext, null, true, false);
        } catch (Exception e) {
            log.error("qlExpress运行出错！", e);
        }
    }
}
