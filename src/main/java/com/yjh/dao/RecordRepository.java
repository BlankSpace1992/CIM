package com.yjh.dao;

import com.yjh.web.admin.domain.po.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by limi on 2017/10/15.
 */
public interface RecordRepository extends JpaRepository<Record,Long> {

    public Record findByIp(String ip);

    List<Record> findByAddressLike(String address);
}
