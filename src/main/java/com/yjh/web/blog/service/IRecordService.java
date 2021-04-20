package com.yjh.web.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.web.blog.domain.Record;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yujunhong
 * @date 2021/4/20 15:54
 */
public interface IRecordService extends IService<Record> {

    /**
     * 保存访问记录
     *
     * @param httpServletRequest http请求
     * @author yujunhong
     * @date 2021/4/20 15:59
     */
    void recording(HttpServletRequest httpServletRequest);

    /**
     * 获取所有访问记录
     *
     * @return 所有记录信息
     * @author yujunhong
     * @date 2021/4/20 16:18
     */
    List<Record> listRecords();


    /**
     * 获取所有访问记录
     * @param address 访问地址
     * @return 所有记录信息
     * @author yujunhong
     * @date 2021/4/20 16:18
     */
    List<Record> getListRecordsByAddress(String address);
}
