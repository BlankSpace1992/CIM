package com.yjh.web.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjh.util.IPUtils;
import com.yjh.web.blog.domain.Record;
import com.yjh.web.blog.mapper.RecordMapper;
import com.yjh.web.blog.service.IRecordService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author yujunhong
 * @date 2021/4/20 15:55
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {
    @Override
    public void recording(HttpServletRequest httpServletRequest) {
        // 获取访问IP
        String ip = IPUtils.getIpAddr(httpServletRequest);
        // 通过ip查找此ip是否已存在
        LambdaQueryWrapper<Record> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Record::getIp, ip);
        Record record = this.getOne(wrapper);
        // 为空则表示该ip从未登录网站,需要新增
        if (null == record) {
            record = new Record();
            // ip地址
            record.setIp(ip);
            // 最后一次访问时间
            record.setLastVisitTime(new Date());
            // 总的访问次数
            record.setTotalNumberOfVisits(1L);
            // 获取地址信息
         /*   String result = HttpClient.doGet(Constants.IP_REQUEST_URL_END + record.getIp());
            record.setAddress(result.substring(result.indexOf(":") + 1, result.length() - 1));*/
            this.save(record);
        } else {
            record.setLastVisitTime(new Date());
            record.setTotalNumberOfVisits(record.getTotalNumberOfVisits() + 1);
            this.updateById(record);
        }
    }

    @Override
    public List<Record> listRecords() {
        LambdaQueryWrapper<Record> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Record::getTotalNumberOfVisits);
        return this.list(wrapper);
    }

    @Override
    public List<Record> getListRecordsByAddress(String address) {
        return baseMapper.getListRecordsByAddress(address);
    }
}
