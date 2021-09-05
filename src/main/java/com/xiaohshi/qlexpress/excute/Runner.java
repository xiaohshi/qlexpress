package com.xiaohshi.qlexpress.excute;

/**
 * 方便使用策略模式
 */
public abstract class Runner {

    public abstract void execute(String workflow, String Step, String text, Object...params);

}
