package com.xiaohshi.qlexpress.excute;

import com.ql.util.express.ExpressRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 第一次执行的模板执行器
 * 需要设置beanName
 */
@Slf4j
@Component("FirstComputeRunner")
public class FirstComputeRunner implements Runner {

    @Autowired
    private ExpressRunner runner;

    @Override
    public void execute(String workflow, String step, String text, Object...params) {
        log.info("第一次计算");
        try {
            executeMyTask(runner, workflow, step, text, params);
        } catch (Exception e) {
            log.error("qlExpress运行出错！", e);
        }
    }
}
