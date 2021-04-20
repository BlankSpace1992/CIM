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

import java.util.Date;

/**
 * 人数纪录主表
 *
 * @author yujunhong
 * @date 2021/04/20 02:31:57
 */
@ApiModel(value = "人数纪录主表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_record")
public class Record {
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @Excel(name = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    @Excel(name = "地址")
    private String address;
    /**
     * ip地址
     */
    @ApiModelProperty(value = "ip地址")
    @Excel(name = "ip地址")
    private String ip;
    /**
     * 最后一次访问时间
     */
    @ApiModelProperty(value = "最后一次访问时间")
    @Excel(name = "最后一次访问时间")
    private Date lastVisitTime;
    /**
     * 总的访问量
     */
    @ApiModelProperty(value = "总的访问量")
    @Excel(name = "总的访问量")
    private Long totalNumberOfVisits;
}
