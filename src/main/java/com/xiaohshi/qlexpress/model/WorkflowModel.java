package com.xiaohshi.qlexpress.model;

import lombok.*;

import java.util.List;

/**
 * 工作流信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowModel {

    private String workflowName;

    private String app;

    private String type;

    private List<TaskModel> steps;
}
