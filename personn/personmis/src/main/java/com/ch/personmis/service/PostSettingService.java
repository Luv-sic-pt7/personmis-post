package com.ch.personmis.service;

import com.ch.personmis.entity.PostSetting;
import com.ch.personmis.repository.PostSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostSettingService {

    @Autowired
    private PostSettingMapper postSettingMapper;

    /**
     * 获取部门的岗位设置情况
     * @param departmentId 部门ID
     * @return 岗位设置情况列表
     */
    public List<PostSetting> getPostSettingsByDepartment(int departmentId) {
        return postSettingMapper.getPostSettingsByDepartment(departmentId);
    }
}