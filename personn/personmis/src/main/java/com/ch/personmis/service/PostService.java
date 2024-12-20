package com.ch.personmis.service;

import com.ch.personmis.entity.Depart;
import com.ch.personmis.entity.DepartByCon;
import com.ch.personmis.entity.Post;
import com.ch.personmis.entity.PostByCon;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface PostService {
    String addPost(Post post);
    Map<String, Object> selectPostByPage(PostByCon postByCon);
    Map<String, Object> selectPostByCon(PostByCon postByCon);
    Post getPostDetail(int id);
    String updatePost(Post post);
    List<Post> getPost();
    ResponseEntity<String> deletePost(int id);
    Map<String, Object> getDepartmentDetail(int departmentId);
    List<Map<String, Object>> getPostDetailsAcrossDepartments(String pname);
    int getPostCount();
    List<Post> getAllPosts();
    List<Map<String, Object>> getWorkloadByPost(int postId);
    List<Map<String, Object>> getAverageSalaryByPost();
}
