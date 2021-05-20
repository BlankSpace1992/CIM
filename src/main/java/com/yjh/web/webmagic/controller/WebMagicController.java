package com.yjh.web.webmagic.controller;

import com.yjh.common.exception.ResultBody;
import com.yjh.web.webmagic.WebMagicPipeline;
import com.yjh.web.webmagic.WebMagicProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

/**
 * @author yujunhong
 * @date 2021/5/20 10:54
 */
@RestController
@RequestMapping(value = "/web-magic")
public class WebMagicController {
    @Autowired
    private WebMagicPipeline webMagicPipeline;

    /**
     * @param url 拉去文章地址
     * @return 成功/失败
     * @author yujunhong
     * @date 2021/5/20 10:54
     */
    @GetMapping(value = "/get-one-blog")
    public ResultBody getOneBlog(String url) {
        Spider spider = Spider.create(new WebMagicProcessor());
        spider.addUrl(url);
        spider.addPipeline(webMagicPipeline);
        spider.thread(5);
        spider.setExitWhenComplete(true);
        spider.start();
        return ResultBody.success();
    }
}
