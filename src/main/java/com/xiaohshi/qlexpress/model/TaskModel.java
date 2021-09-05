package com.xiaohshi.qlexpress.model;

import lombok.*;

/**
 * 工作流执行步骤信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskModel {
    private String name;

    private String handler;

    private String bind;

//    private List<ParamModel> inputs;
}
