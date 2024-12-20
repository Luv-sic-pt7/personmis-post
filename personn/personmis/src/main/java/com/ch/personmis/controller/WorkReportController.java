package com.ch.personmis.controller;

import com.ch.personmis.entity.WorkReport;
import com.ch.personmis.service.WorkReportService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/workreport")
public class WorkReportController {
    @Resource
    private WorkReportService workReportService;

    @PostMapping("/addWorkReport")
    public String addWorkReport(@RequestBody WorkReport workReport) {
        return workReportService.addWorkReport(workReport);
    }
}