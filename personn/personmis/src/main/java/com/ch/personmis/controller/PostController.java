package com.ch.personmis.controller;

import com.ch.personmis.entity.Post;
import com.ch.personmis.entity.PostByCon;

import com.ch.personmis.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PostController {
    @Resource
    private PostService postService;
    @GetMapping("/getPostByPage")
    public Map<String, Object> selectPostByPage(PostByCon postByCon) {
        return postService.selectPostByPage(postByCon);
    }
    @PostMapping("/addPost")
    public  String addPost(@RequestBody Post post){
        return  postService.addPost(post);
    }
    @PostMapping("/selectPostByCon")
    public Map<String, Object> selectPostByCon(@RequestBody PostByCon postByCon) {
        return postService.selectPostByCon(postByCon);
    }
    @GetMapping("/getPostDetail")
    public Post getPostDetail(int id) {
        return postService.getPostDetail(id);
    }
    @PostMapping("/updatePost")
    public  String updatePost(@RequestBody Post post){
        return  postService.updatePost(post);
    }
    @GetMapping("/getPost")
    public List<Post> getPost() {
        return postService.getPost();
    }
    @PostMapping("/deletePost")
    public ResponseEntity<String> deletePost(int id){
        return postService.deletePost(id);
    }
    @GetMapping("/getPostCount")
    public int getPostCount() {
        return postService.getPostCount();
    }
    @GetMapping("/getPosts")
    public List<Post> getPosts() {
        return postService.getAllPosts();
    }
    @GetMapping("/getDepartmentByDepartmentId")
    public Map<String, Object> getDepartmentByDepartmentId(@RequestParam int departmentId) {
        try {
            // 调用 Service 获取部门详情
            return postService.getDepartmentDetail(departmentId);
        } catch (RuntimeException e) {
            // 处理异常，返回友好的提示
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return errorResponse;
        }
    }

    // 查询某个岗位在不同部门的实际人数和岗位详情
    @GetMapping("/getPostDetailsAcrossDepartments")
    public List<Map<String, Object>> getPostDetailsAcrossDepartments(@RequestParam String pname) {
        System.out.println("接收到 pname 参数：" + pname);
        return postService.getPostDetailsAcrossDepartments(pname);
    }
    @GetMapping("/getWorkloadByPost")
    public List<Map<String, Object>> getWorkloadByPost(@RequestParam int postId) {
        return postService.getWorkloadByPost(postId);
    }
    @GetMapping("/getAverageSalaryByPost")
    public List<Map<String, Object>> getAverageSalaryByPost() {
        return postService.getAverageSalaryByPost();
    }
}
