package com.ch.personmis.service;

import com.ch.personmis.entity.Employee;
import com.ch.personmis.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepository.selectEmployeeById(id);
    }
    @Override
    public void updateEmployeeSkills(int id, String skills) {
        System.out.println("Updating skills for employee ID: " + id + ", Skills: " + skills);
        employeeRepository.updateEmployeeSkills(id, skills); // 调用仓库层的方法
    }
    @Override
    public List<Employee> searchEmployeesBySkills(String skills) {
        return employeeRepository.searchEmployeesBySkills(skills);
    }


}