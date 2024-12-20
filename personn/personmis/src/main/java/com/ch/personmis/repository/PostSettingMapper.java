package com.ch.personmis.repository;

import com.ch.personmis.entity.PostSetting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostSettingMapper {

    /**
     * 获取部门的岗位设置情况
     * @param departmentId 部门ID
     * @return 岗位设置列表
     */
    List<PostSetting> getPostSettingsByDepartment(@Param("departmentId") int departmentId);
}