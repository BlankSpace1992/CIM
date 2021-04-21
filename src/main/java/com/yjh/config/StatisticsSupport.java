package com.yjh.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yjh.common.page.PageStatistics;
import com.yjh.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;


@Slf4j
public class StatisticsSupport {

    protected static final ThreadLocal<PageStatistics> LOCAL_PAGE_STATISTICS = new ThreadLocal<>();

    /**
     * 设置 PageStatistics 参数
     *
     * @param pageStatistics
     */
    public static void setLocalPageStatistics(PageStatistics pageStatistics)
    {
        LOCAL_PAGE_STATISTICS.set(pageStatistics);
    }

    /**
     * 获取 PageStatistics 参数
     *
     * @return
     */
    public static <T> PageStatistics getLocalPageStatistic() {
        return LOCAL_PAGE_STATISTICS.get();
    }

    /**
     * 移除本地变量
     */
    public static void clearPageStatistic() {
        LOCAL_PAGE_STATISTICS.remove();
    }


    /**
    * @Description: 统计参数总量
    * @param: [connection, parameterHandler, boundSql]
    * @return: void
    * @Author: waylon
    * @Date: 2020/6/22
    */
    public void statisticTotal(Connection connection, ParameterHandler parameterHandler, BoundSql boundSql)
    {
        String statisticSql = getStatisticSql(boundSql.getSql());

        if(StringUtils.isEmpty(statisticSql)){
            return;
        }
        PreparedStatement statisticStmt = null;
        ResultSet rs = null;
        Map<String,BigDecimal> paramsMap = getSumParams();
        try {
            statisticStmt = connection.prepareStatement(statisticSql);
            parameterHandler.setParameters(statisticStmt);
            rs = statisticStmt.executeQuery();
            if(rs.next()){
                for (String paramsKey : paramsMap.keySet()) {
                     paramsMap.put(paramsKey,rs.getBigDecimal(StringUtils.toCamelCaseByAlias(paramsKey)));
                }
            }
            setLocalPageStatistics(JSONObject.parseObject(JSON.toJSONString(paramsMap),PageStatistics.class));
        } catch (SQLException e) {
            log.error("StatisticsSupport statisticPage exception ,e:{}",e);
        }finally {
            try{
                if(null != statisticStmt){
                    statisticStmt.close();
                }
                if(null != rs){
                    rs.close();
                }

            }catch (SQLException e){
                log.error("StatisticsSupport statisticPage finally close exception ,e:{}",e);
            }
        }


    }

    /**
    * @Description: 获取统计参数
    * @param: []
    * @return: java.util.Map<java.lang.String,java.lang.String>
    * @Author: waylon
    * @Date: 2020/6/22
    */
    public Map<String,BigDecimal> getSumParams(){
        // 统计页本地变量
        PageStatistics pageStatistics = getLocalPageStatistic();
        if(StringUtils.isNull(pageStatistics)){
            pageStatistics = new PageStatistics();
        }
        Map<String,BigDecimal> paramsMap = new LinkedHashMap<>();
        pageStatistics.entrySet().forEach(statistic ->{
                paramsMap.put(statistic.getKey(),BigDecimal.ZERO);

        });
        return paramsMap;
    }
    /**
    * @Description:  动态拼接统计sql
    * @param: [sql]
    * @return: java.lang.String
    * @Author: waylon
    * @Date: 2020/6/23
    */
    public String getStatisticSql(String sql)
    {
       // 需要替换的参数map
       Map<String,BigDecimal> paramsMap = getSumParams();
       if(StringUtils.isEmpty(paramsMap)){
            return null;
       }
       if(!sql.contains("count(0)")){
           return null;
       }
       // 统计sql 片段
       StringBuilder statisticSub = new StringBuilder(100);
       paramsMap.keySet().forEach(params ->{
           statisticSub.append(" sum(").append(params).append(")").append(" as ").append(StringUtils.toCamelCaseByAlias(params)).append(",");
       });
       if(statisticSub.lastIndexOf(",") != -1){
            statisticSub.deleteCharAt(statisticSub.lastIndexOf(","));
       }
       return sql.toLowerCase().trim().replace("count(0)",statisticSub.toString());
    }


}
