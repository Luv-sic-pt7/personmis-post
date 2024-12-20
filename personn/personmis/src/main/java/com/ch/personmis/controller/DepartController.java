package com.ch.personmis.controller;

import com.ch.personmis.entity.Depart;
import com.ch.personmis.entity.DepartByCon;
import com.ch.personmis.service.DepartService;
import com.ch.personmis.service.PostService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class DepartController {
    @Resource
    private DepartService departService;

    @Resource
    private PostService postService;  // 注入 PostService

    // 获取所有部门
    @GetMapping("/getDepartment")
    public List<Depart> selectDepart() {
        return departService.selectDepart();
    }

    // 分页查询部门
    @GetMapping("/getDepartmentByPage")
    public Map<String, Object> selectDepartByPage(DepartByCon departByCon) {
        return departService.selectDepartByPage(departByCon);
    }

    // 添加部门
    @PostMapping("/addDepartment")
    public String addDepartment(@RequestBody Depart depart) {
        return departService.addDepartment(depart);
    }

    // 根据条件查询部门
    @PostMapping("/selectDepartmentsByCon")
    public Map<String, Object> selectDepartmentsByCon(@RequestBody DepartByCon departByCon) {
        return departService.selectDepartmentsByCon(departByCon);
    }

    // 获取部门详情
    @GetMapping("/getDepartmentDetail")
    public Map<String, Object> getDepartmentDetail(int id) {
        return departService.getDepartmentDetail(id);
    }

    // 更新部门信息
    @PostMapping("/updateDepartment")
    public String updateDepartment(@RequestBody Depart depart) {
        return departService.updateDepartment(depart);
    }

    // 删除部门
    @PostMapping("/deleteDepartment")
    public String deleteDepartment(int id) {
        return departService.deleteDepartment(id);
    }
}