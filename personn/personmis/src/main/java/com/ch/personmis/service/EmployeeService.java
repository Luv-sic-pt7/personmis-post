package com.ch.personmis.service;

import com.ch.personmis.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee getEmployeeById(int id);
    void updateEmployeeSkills(int id, String skills); // 新增方法


    List<Employee> searchEmployeesBySkills(String skills);

}