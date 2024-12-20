package com.ch.personmis.service;

import com.ch.personmis.repository.AnalyseRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class AnalyseServiceImpl implements AnalyseService {
    @Resource
    private AnalyseRepository analyseRepository;

    @Override
    public BigDecimal getAverageTaskDuration() {
        return analyseRepository.getAverageTaskDuration();
    }

    @Override
    public BigDecimal getTotalMonthlySalary() {
        return analyseRepository.getTotalMonthlySalary();
    }

    @Override
    public List<Map<String, Object>> getAgeStructureByPostName(String postName) {
        return analyseRepository.getAgeStructureByPostName(postName);
    }

    @Override
    public List<Map<String, Object>> getEducationDistributionByPostName(String postName) {
        return analyseRepository.getEducationDistributionByPostName(postName);
    }

    @Override
    public BigDecimal getTurnoverRate(String postId) {
        return analyseRepository.getTurnoverRate(postId);
    }

    @Override
    public BigDecimal getTransferRate(String postId) {
        return analyseRepository.getTransferRate(postId);
    }
}