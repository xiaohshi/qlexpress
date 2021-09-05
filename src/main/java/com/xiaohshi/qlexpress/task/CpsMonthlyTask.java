package com.xiaohshi.qlexpress.task;

import org.springframework.stereotype.Component;

/**
 * 测试 cps月结
 * 必须要注入到spring容器中
 */
@Component
public class CpsMonthlyTask {

    public void dumpData() {
        System.out.println("cps monthly 下载数据");
    }

    public void firstCompute(int id, String name) {
        System.out.println("cps monthly 第一次计算 from " + name + "::" + id);
    }

}
