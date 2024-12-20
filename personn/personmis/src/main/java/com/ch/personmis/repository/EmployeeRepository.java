package com.ch.personmis.repository;

import com.ch.personmis.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository {


    Employee selectEmployeeById(@Param("id") int id);
    void updateEmployeeSkills(@Param("id") int id, @Param("skills") String skills); // 新增方法

    List<Employee> searchEmployeesBySkills(@Param("skills") String skills);

}
