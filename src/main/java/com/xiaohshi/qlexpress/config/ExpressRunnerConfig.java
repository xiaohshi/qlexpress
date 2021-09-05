package com.xiaohshi.qlexpress.config;

import com.ql.util.express.ExpressRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 将ExpressRunner注入到spring中
 */
@Configuration
public class ExpressRunnerConfig {

    @Bean
    public ExpressRunner getRunner() {
        return new ExpressRunner();
    }

}
