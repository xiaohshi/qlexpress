package com.xiaohshi.qlexpress.model;

import lombok.*;

/**
 * 函数参数对象
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ParamModel {
    // 参数名称
    private String name;

//    private String type;

    // 参数类型
    private Class<?> paramClass;

}
