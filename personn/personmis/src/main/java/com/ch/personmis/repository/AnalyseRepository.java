package com.ch.personmis.repository;

import com.ch.personmis.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface AnalyseRepository {
    BigDecimal getAverageTaskDuration();
    BigDecimal getTotalMonthlySalary();
    List<Map<String, Object>> getAgeStructureByPostName(@Param("postName") String postName);
    List<Map<String, Object>> getEducationDistributionByPostName(@Param("postName") String postName);

    @Select("SELECT COUNT(*) / (SELECT COUNT(*) FROM staff WHERE post_id = #{postId}) FROM quit WHERE staff_id IN (SELECT id FROM staff WHERE post_id = #{postId})")
    BigDecimal getTurnoverRate(@Param("postId") String postId);

    @Select("SELECT COUNT(*) / (SELECT COUNT(*) FROM staff WHERE post_id = #{postId}) FROM transfer WHERE beforepost_id = #{postId}")
    BigDecimal getTransferRate(@Param("postId") String postId);
}