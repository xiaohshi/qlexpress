package com.xiaohshi.qlexpress.execute.handler;

import com.ql.util.express.ExpressRunner;
import com.xiaohshi.qlexpress.execute.Runner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 第一次执行的模板执行器
 * 需要设置beanName
 */
@Slf4j
@Component("SettleRunner")
public class SettleRunner extends Runner {

    private ExpressRunner runner;

    @Autowired
    public void setRunner(ExpressRunner runner) {
        this.runner = runner;
    }

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
