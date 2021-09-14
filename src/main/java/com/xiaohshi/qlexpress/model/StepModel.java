package com.xiaohshi.qlexpress.model;

import lombok.*;

import java.util.List;

/**
 * 工作流所有信息汇总
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NonNull
public class StepModel {

    private Class<?> clazz;

    @NonNull
    private String workflowName;

    @NonNull
    private String stepName;

    private String className;

    private String methodName;

    @NonNull
    private String bind;

    private List<ParamModel> inputList;

}
