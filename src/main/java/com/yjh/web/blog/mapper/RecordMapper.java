package com.yjh.web.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjh.web.blog.domain.Record;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yujunhong
 * @date 2021/4/20 15:56
 */
public interface RecordMapper extends BaseMapper<Record> {
    /**
     * 获取所有访问记录
     * @param address 访问地址
     * @return 所有记录信息
     * @author yujunhong
     * @date 2021/4/20 16:18
     */
    List<Record> getListRecordsByAddress(@Param(value = "address") String address);
}
