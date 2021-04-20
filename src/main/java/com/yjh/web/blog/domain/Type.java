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
 * 分类表
 *
 * @author yujunhong
 * @date 2021/04/20 02:31:57
 */
@ApiModel(value = "分类表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_type")
public class Type {
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @Excel(name = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    @Excel(name = "分类名称")
    private String name;
}
