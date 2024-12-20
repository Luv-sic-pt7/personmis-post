package com.ch.personmis.service;

import com.ch.personmis.entity.WorkReport;
import com.ch.personmis.repository.WorkReportRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WorkReportServiceImpl implements WorkReportService {
    @Resource
    private WorkReportRepository workReportRepository;

    @Override
    public String addWorkReport(WorkReport workReport) {
        if (workReportRepository.addWorkReport(workReport) > 0)
            return "ok";
        return "no";
    }
}