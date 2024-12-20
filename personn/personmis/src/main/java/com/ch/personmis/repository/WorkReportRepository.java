package com.ch.personmis.repository;

import com.ch.personmis.entity.WorkReport;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkReportRepository {
    int addWorkReport(WorkReport workReport);
}