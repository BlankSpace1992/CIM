package com.yjh.web.webmagic.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yujunhong
 * @date 2021/5/20 10:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebMagicEntity {
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 作者
     */
    @ApiModelProperty(value = "作者")
    private String author;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;

}
