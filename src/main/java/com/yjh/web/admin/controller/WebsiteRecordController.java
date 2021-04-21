package com.yjh.web.admin.controller;

import com.yjh.web.blog.domain.Record;
import com.yjh.web.blog.service.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 网站浏览量 Controller
 *
 * @author yujunhong
 * @date 2021/4/21 15:13
 */
@Controller
public class WebsiteRecordController {

    @Autowired
    private IRecordService recordService;

    @GetMapping("/admin/websiteRecord")
    public String toWebsiteRecordPage(Model model) {
        List<Record> recordList = recordService.listRecords();
        model.addAttribute("recordList", recordList);
        model.addAttribute("recordListSize", recordList.size());
        model.addAttribute("address", "");
        return "admin/websiteRecord";
    }

    @GetMapping("/admin/searchWebsiteRecordByAddress")
    public String searchWebsiteRecordByAddress(Model model, String address) {
        List<Record> recordList = recordService.getListRecordsByAddress(address);
        model.addAttribute("recordList", recordList);
        model.addAttribute("recordListSize", recordList.size());
        model.addAttribute("address", address);
        return "admin/websiteRecord";
    }

}
