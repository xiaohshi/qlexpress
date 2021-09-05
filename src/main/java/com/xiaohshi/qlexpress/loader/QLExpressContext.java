package com.xiaohshi.qlexpress.loader;

import com.ql.util.express.IExpressContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 构造QLExpress上下文对象
 */
public class QLExpressContext extends HashMap<String,Object> implements IExpressContext<String,Object> {

    public QLExpressContext(Map<String,Object> aProperties){
        super(aProperties);
    }

}
