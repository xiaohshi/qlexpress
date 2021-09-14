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

    private String task;

    private String handler;

    // 表示运行状态
    private String status;

//    private List<ParamModel> inputs;
}
