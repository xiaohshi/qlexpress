package com.xiaohshi.qlexpress.task;

import org.springframework.stereotype.Component;

/**
 * 测试 cpi月结
 * 必须要注入到spring容器中
 */
@Component
public class CpiMonthlyTask {

    public void dumpData() {
        System.out.println("cpi monthly 下载数据");
    }

    public void firstCompute() {
        System.out.println("cpi monthly 第一次计算");
    }

}

