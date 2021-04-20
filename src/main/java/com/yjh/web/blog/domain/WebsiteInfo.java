package com.yjh.web.blog.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 网站信息
 *
 * @author yujunhong
 * @date 2021/04/20 02:31:58
 */
@ApiModel(value = "网站信息")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_website_info")
public class WebsiteInfo {
    /**
     * 内容类别
     */
    @ApiModelProperty(value = "内容类别")
    @Excel(name = "内容类别")
    @TableId(value = "value_name", type = IdType.AUTO)
    private String valueName;
    /**
     * 内容信息
     */
    @ApiModelProperty(value = "内容信息")
    @Excel(name = "内容信息")
    private String value;
}
