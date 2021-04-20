package com.yjh.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 博客查询条件
 *
 * @author yujunhong
 * @date 2021/4/20 11:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogQuery {
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;
    /**
     * 类型id
     */
    @ApiModelProperty(value = "类型id")
    private Long typeId;
    /**
     * 是否推荐
     */
    @ApiModelProperty(value = "是否推荐")
    private String recommend;

    /**
     * 查询条件(标题或者内容)
     */
    @ApiModelProperty(value = "查询条件(标题或者内容)")
    private String query;

}
