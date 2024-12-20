package com.ch.personmis.repository;

import com.ch.personmis.entity.Post;
import com.ch.personmis.entity.PostByCon;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PostRepository {
    List<Post> selectPost(PostByCon postByCon);
    int addPost(Post post);
    List<Post> selectPostByCon(PostByCon postByCon);
    Post selectAPost(int id);
    int updatePost(Post post);
    List<Map<String, Object>> selectAssociatePost(int id);
    int deletePost(int id);
    int getPostCount();
    Map<String, Object> findDepartmentById(int departmentId);
    List<Map<String, Object>> findPostDetailsAcrossDepartments(@Param("pname") String pname);
    @Select("SELECT id, pname FROM post")
    List<Post> getAllPosts();
    List<Map<String, Object>> getWorkloadByPost(@Param("postId") int postId);
    List<Map<String, Object>> getAverageSalaryByPost();
}
