package com.ch.personmis.controller;

import com.ch.personmis.entity.Employee;
import com.ch.personmis.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/employee/{id}")
    public void updateEmployeeSkills(@PathVariable int id, @RequestBody SkillUpdateRequest request) {
        employeeService.updateEmployeeSkills(id, request.getSkills());
    }
    @GetMapping("/employees")
    public List<Employee> searchEmployeesBySkills(@RequestParam String skills) {
        return employeeService.searchEmployeesBySkills(skills);
    }

}
// 新建一个请求对象类
class SkillUpdateRequest {
    private String skills;

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}