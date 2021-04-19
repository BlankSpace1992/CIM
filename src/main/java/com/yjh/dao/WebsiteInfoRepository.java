package com.yjh.dao;

import com.yjh.web.admin.domain.po.WebsiteInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by limi on 2017/10/20.
 */
public interface WebsiteInfoRepository extends JpaRepository<WebsiteInfo, String>{

    public WebsiteInfo findByValueName(String valueName);

}
