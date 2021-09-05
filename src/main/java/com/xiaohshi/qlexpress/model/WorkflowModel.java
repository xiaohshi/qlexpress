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

    @NonNull
    private String workflowName;

    private List<TaskModel> steps;
}
