package com.yjh.web.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 爬取模块Processor
 *
 * @author yujunhong
 * @date 2021/5/20 10:36
 */
public class WebMagicProcessor implements PageProcessor {

    private final Site site = Site.me()
            .setSleepTime(100)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) " +
                    "Chrome/52.0.2743.116 Safari/537.36");

    public static final String URL = "https://www.jianshu.com";

    @Override
    public void process(Page page) {
        // 判断网页路径是否匹配
        if (page.getUrl().regex(URL).match()) {
            // 解析标题
            page.putField("title", page.getHtml().xpath("//h1[@class='_2zeTMs']/text()"));
            // 解析作者
            page.putField("author", page.getHtml().xpath("//div[@class='_26qd_C']/a/span/text()"));
            // 解析内容
            page.putField("content",page.getHtml().xpath("//article[@class='_2rhmJa']"));
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
