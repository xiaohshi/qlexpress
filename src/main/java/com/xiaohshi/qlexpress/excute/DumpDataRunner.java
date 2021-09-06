package com.xiaohshi.qlexpress.excute;

import com.ql.util.express.ExpressRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 下载数据的模板执行器
 * 需要设置beanName
 */
@Slf4j
@Component("DumpDataRunner")
public class DumpDataRunner implements Runner {

    @Autowired
    private ExpressRunner runner;

    @Override
    public void execute(String workflow, String step, String text, Object...params) {
        // 这里需要想象是一个模板方法，这块是公共的业务
        log.info("执行下载数据");
        try {
            executeMyTask(runner, workflow, step, text, params);
        } catch (Exception e) {
            log.error("qlExpress运行出错！", e);
        }
    }
}
