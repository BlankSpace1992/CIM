package com.yjh.web.webmagic;

import com.overzealous.remark.Remark;
import com.yjh.web.blog.domain.Blog;
import com.yjh.web.blog.service.IBlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.HtmlNode;
import us.codecraft.webmagic.selector.PlainText;

import java.util.Date;

/**
 * @author yujunhong
 * @date 2021/5/20 10:56
 */
@Slf4j
@Component
public class WebMagicPipeline implements Pipeline {
    @Autowired
    private IBlogService blogService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        // 获取主要内容
        HtmlNode content = resultItems.get("content");
        // 获取Remark对象
        Remark remark = new Remark();
        // 获取内容-markdown
        String fragment = remark.convertFragment(content.toString());
        // 获取标题
        PlainText title = resultItems.get("title");
        // 保存博客内容
        Blog blog = new Blog();
        blog.setContent(fragment);
        blog.setDescription(title.toString());
        blog.setTitle(title.toString());
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setTypeId(2L);
        blogService.save(blog);
    }
}
