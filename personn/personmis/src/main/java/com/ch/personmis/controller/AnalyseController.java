package com.ch.personmis.controller;

import com.ch.personmis.service.AnalyseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
public class AnalyseController {
    @Resource
    private AnalyseService analyseService;

    @GetMapping("/getAverageTaskDuration")
    public BigDecimal getAverageTaskDuration() {
        return analyseService.getAverageTaskDuration();
    }

    @GetMapping("/getTotalMonthlySalary")
    public BigDecimal getTotalMonthlySalary() {
        return analyseService.getTotalMonthlySalary();
    }

    @GetMapping("/getPieChartData1")
    public List<Map<String, Object>> getPieChartData1(@RequestParam String postName) {
        return analyseService.getAgeStructureByPostName(postName);
    }

    @GetMapping("/getEducationDistribution")
    public List<Map<String, Object>> getEducationDistribution(@RequestParam String postName) {
        return analyseService.getEducationDistributionByPostName(postName);
    }

    @GetMapping("/getTurnoverRate")
    public BigDecimal getTurnoverRate(@RequestParam String postId) {
        return analyseService.getTurnoverRate(postId);
    }

    @GetMapping("/getTransferRate")
    public BigDecimal getTransferRate(@RequestParam String postId) {
        return analyseService.getTransferRate(postId);
    }
}