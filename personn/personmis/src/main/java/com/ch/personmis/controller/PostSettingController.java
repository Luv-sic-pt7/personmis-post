package com.ch.personmis.controller;

import com.ch.personmis.entity.PostSetting;
import com.ch.personmis.service.PostSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostSettingController {

    @Autowired
    private PostSettingService postSettingService;

    /**
     * 获取部门的岗位设置情况
     * @param departmentId 部门ID
     * @return 岗位设置情况列表
     */
    @GetMapping("/getPostSettingsByDepartment")
    public List<PostSetting> getPostSettingsByDepartment(@RequestParam int departmentId) {
        return postSettingService.getPostSettingsByDepartment(departmentId);
    }
}
