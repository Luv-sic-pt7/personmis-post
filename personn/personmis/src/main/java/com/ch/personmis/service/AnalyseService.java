package com.ch.personmis.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AnalyseService {
    BigDecimal getAverageTaskDuration();
    BigDecimal getTotalMonthlySalary();
    List<Map<String, Object>> getAgeStructureByPostName(String postName);
    List<Map<String, Object>> getEducationDistributionByPostName(String postName);
    BigDecimal getTurnoverRate(String postId);
    BigDecimal getTransferRate(String postId);
}