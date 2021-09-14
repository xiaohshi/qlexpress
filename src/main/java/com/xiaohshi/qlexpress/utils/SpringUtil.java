package com.xiaohshi.qlexpress.utils;

import com.xiaohshi.qlexpress.execute.Runner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获得ApplicationContext，根据bean name获得spring bean
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }

    // 根据beanName获得相应的bean
    public static Runner getBean(String beanName) {
        if(applicationContext.containsBean(beanName)){
            return (Runner) applicationContext.getBean(beanName);
        }else{
            return null;
        }
    }
}