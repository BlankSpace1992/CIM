package com.yjh.web.blog.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * websocket 存储message实体
 *
 * @author yujunhong
 * @date 2021/5/18 17:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketMessage {
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 博客主键ID
     */
    @ApiModelProperty(value = "博客主键ID")
    private Long blogId;


}
