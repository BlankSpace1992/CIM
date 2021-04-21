package com.yjh.web.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjh.common.constants.Constants;
import com.yjh.web.blog.domain.WebsiteInfo;
import com.yjh.web.blog.mapper.WebsiteInfoMapper;
import com.yjh.web.blog.service.IWebsiteInfoService;
import org.springframework.stereotype.Service;

/**
 * @author yujunhong
 * @date 2021/4/21 14:22
 */
@Service
public class WebsiteInfoServiceImpl extends ServiceImpl<WebsiteInfoMapper, WebsiteInfo> implements IWebsiteInfoService {
    @Override
    public Long addOneForViews() {
        // 获取当前的访问量
        LambdaQueryWrapper<WebsiteInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WebsiteInfo::getValueName, Constants.VIEWS);
        WebsiteInfo websiteInfo = this.getOne(wrapper);
        long views = Long.parseLong(websiteInfo.getValue());
        // 访问量加1
        views++;
        websiteInfo.setValue(String.valueOf(views));
        this.update(websiteInfo,wrapper);
        return views;
    }

    @Override
    public String getAboutMeImageUrl() {
        // 获取首页图片地址
        LambdaQueryWrapper<WebsiteInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WebsiteInfo::getValueName, Constants.ABOUT_IMAGE_URL);
        return this.getOne(wrapper).getValue();
    }

    @Override
    public String updateAboutMeImageUrl(String aboutMeImageUrl) {
        // 获取首页图片地址
        LambdaQueryWrapper<WebsiteInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WebsiteInfo::getValueName, Constants.ABOUT_IMAGE_URL);
        WebsiteInfo websiteInfo = this.getOne(wrapper);
        websiteInfo.setValue(aboutMeImageUrl);
        this.update(websiteInfo,wrapper);
        return aboutMeImageUrl;
    }

    @Override
    public String getTopTitle() {
        // 获取首页标题
        LambdaQueryWrapper<WebsiteInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WebsiteInfo::getValueName, Constants.TOP_TITLE);
        return this.getOne(wrapper).getValue();
    }

    @Override
    public String updateTopTitle(String topTitle) {
        // 获取首页标题
        LambdaQueryWrapper<WebsiteInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WebsiteInfo::getValueName, Constants.TOP_TITLE);
        WebsiteInfo websiteInfo = this.getOne(wrapper);
        websiteInfo.setValue(topTitle);
        this.update(websiteInfo,wrapper);
        return topTitle;
    }

    @Override
    public String getAboutMeContent() {
        // 获取首页内容
        LambdaQueryWrapper<WebsiteInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WebsiteInfo::getValueName, Constants.ABOUT_CONTENT);
        return this.getOne(wrapper).getValue();
    }
}
