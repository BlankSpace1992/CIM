package com.yjh.common.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjh.common.constants.HttpStatus;
import com.yjh.common.page.PageStatistics;
import com.yjh.common.page.TableDataInfo;
import com.yjh.config.StatisticsSupport;
import com.yjh.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yujunhong
 * @date 2021/4/20 10:57
 */
public class BaseController {
    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("search.success");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        // 填充 统计值
        Map<String, BigDecimal> statisticsMap = new HashMap<>();
        PageStatistics pageStatistics =  StatisticsSupport.getLocalPageStatistic();
        if(StringUtils.isNotNull(pageStatistics)){
            pageStatistics.entrySet().stream().forEach(statistics ->{
                statisticsMap.put("total" + StringUtils.toUpperCamelCaseByAlias(statistics.getKey()) + "Statistics", pageStatistics.getBigDecimal(statistics.getKey()));
                statisticsMap.put("page" +StringUtils.toUpperCamelCaseByAlias(statistics.getKey()) + "Statistics" ,
                        BigDecimal.valueOf(list.stream().mapToDouble(obj ->{
                            return  JSONObject.parseObject(JSON.toJSONString(obj)).getDouble(StringUtils.toCamelCaseByAlias(statistics.getKey()));
                        }).sum()));
            });
        }
        rspData.setStatisticsMap(statisticsMap);
        // 清除本地线程中存储的分页参数、统计参数
        PageHelper.clearPage();
        StatisticsSupport.clearPageStatistic();
        return rspData;
    }
}
