package com.yjh.web.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.web.blog.domain.WebsiteInfo;

/**
 * @author yujunhong
 * @date 2021/4/21 14:21
 */
public interface IWebsiteInfoService extends IService<WebsiteInfo> {
    /**
     * 网站总访问量加1
     *
     * @return 累加之后的访问量
     * @author yujunhong
     * @date 2021/4/21 14:23
     */
    Long addOneForViews();

    /**
     * 获取页面图片地址
     *
     * @return 页面图片地址
     * @author yujunhong
     * @date 2021/4/21 14:24
     */
    String getAboutMeImageUrl();

    /**
     * 更新图片地址
     *
     * @param aboutMeImageUrl 图片地址
     * @author yujunhong
     * @date 2021/4/21 14:25
     */
    String updateAboutMeImageUrl(String aboutMeImageUrl);

    /**
     * 获取顶部头标志
     *
     * @return 网站标题
     * @author yujunhong
     * @date 2021/4/21 14:27
     */
    String getTopTitle();

    /**
     * 更新头部标题
     *
     * @param topTitle 需要更新的头部标题
     * @author yujunhong
     * @date 2021/4/21 14:28
     */
    String updateTopTitle(String topTitle);

    /**
     * 获取作业文本内容
     *
     * @return 作业文本内容
     * @author yujunhong
     * @date 2021/4/21 14:29
     */
    String getAboutMeContent();
}
