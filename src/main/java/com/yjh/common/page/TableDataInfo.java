package com.yjh.common.page;


import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.LongCodec;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 表格分页数据对象
 *
 * @author waylon
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("表格分页数据")
public class TableDataInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    @ApiModelProperty(value = "总记录数" ,position = 1)
    @JSONField(serializeUsing = LongCodec.class)
    private long total;

    /** 列表数据 */
    @ApiModelProperty(value = "列表数据" ,position = 2)
    private List<?> rows;

    /** 消息状态码 */
    @ApiModelProperty(value = "消息状态码" ,position = 3)
    private int code;

    /** 消息内容 */
    @ApiModelProperty(value = "消息内容" ,position = 4)
    private String msg;
    /** 统计数据存储 */
    private Map<String, BigDecimal> statisticsMap ;

    /**
     * 分页
     *
     * @param list 列表数据
     * @param total 总记录数
     */
    public TableDataInfo(List<?> list, long total)
    {
        this.rows = list;
        this.total = total;
    }
}
