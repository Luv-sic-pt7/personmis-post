package com.ch.personmis.service;

import com.ch.personmis.entity.Post;
import com.ch.personmis.entity.PostByCon;
import com.ch.personmis.repository.PostRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements  PostService{
    @Resource
    private PostRepository postRepository;
    @Override
    public String addPost(Post post) {
        // 确保插入时包含 departmentId
        if (post.getDepartmentId() == null) {
            return "no: departmentId cannot be null";
        }
        if (postRepository.addPost(post) > 0)
            return "ok";
        return "no";
    }

    @Override
    public Map<String, Object> selectPostByPage(PostByCon postByCon) {
        Map<String, Object> map = new HashMap<String, Object>();
        postByCon.setAct("byPage");
        List<Post> posts = postRepository.selectPost(postByCon);
        map.put("posts", posts);
        postByCon.setAct("byNoPage");
        map.put("total", postRepository.selectPost(postByCon).size());
        return map;
    }

    @Override
    public Map<String, Object> selectPostByCon(PostByCon postByCon) {
        Map<String, Object> map = new HashMap<String, Object>();
        postByCon.setAct("byPage");
        List<Post> posts = postRepository.selectPostByCon(postByCon);
        map.put("posts", posts);
        postByCon.setAct("byNoPage");
        map.put("total", postRepository.selectPostByCon(postByCon).size());
        return map;
    }

    @Override
    public Post getPostDetail(int id) {
        Post post = postRepository.selectAPost(id);
        if (post == null) {
            throw new RuntimeException("Post not found with id: " + id);
        }
        // 确保 departmentId 有值
        if (post.getDepartmentId() == null) {
            throw new RuntimeException("Post with id " + id + " does not have an associated departmentId");
        }
        return post;
    }

    @Override
    public String updatePost(Post post) {
        if (post.getDepartmentId() == null) {
            return "no: departmentId cannot be null";
        }
        if (postRepository.updatePost(post) > 0)
            return "ok";
        return "no";
    }

    @Override
    public List<Post> getPost() {
        return postRepository.selectPost(null);
    }

    @Override
    public ResponseEntity<String> deletePost(int id) {
        try {
            // 查询是否有关联数据
            List<Map<String, Object>> listMap = postRepository.selectAssociatePost(id);

            if (listMap.size() > 0) {
                // 存在关联数据，不能删除
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("无法删除岗位，存在关联数据！");
            }

            // 如果没有关联数据，执行删除操作
            if (postRepository.deletePost(id) > 0) {
                System.out.println("返回: ok"); // 调试用，打印返回值
                return ResponseEntity.ok("ok");
            } else {
                // 删除失败
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败，请稍后再试！");
            }
        } catch (DataIntegrityViolationException e) {
            // 处理外键约束错误
            System.out.println("捕获到外键约束异常: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("无法删除岗位，存在关联数据！");
        } catch (Exception e) {
            // 其他异常
            System.out.println("发生异常: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败，请稍后再试！");
        }
    }

    @Override
    public Map<String, Object> getDepartmentDetail(int departmentId) {
        Map<String, Object> departmentDetail = postRepository.findDepartmentById(departmentId);
        if (departmentDetail == null || departmentDetail.isEmpty()) {
            throw new RuntimeException("Department not found with ID: " + departmentId);
        }
        return departmentDetail;
    }

    @Override
    public List<Map<String, Object>> getPostDetailsAcrossDepartments(String pname) {
        System.out.println("查询岗位名称：" + pname);
        List<Map<String, Object>> result = postRepository.findPostDetailsAcrossDepartments(pname);
        System.out.println("返回的结果：" + result);
        return result;
    }

    @Override
    public int getPostCount() {
        return postRepository.getPostCount();
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.getAllPosts();
    }

    @Override
    public List<Map<String, Object>> getWorkloadByPost(int postId) {
        return postRepository.getWorkloadByPost(postId);
    }

    @Override
    public List<Map<String, Object>> getAverageSalaryByPost() {
        return postRepository.getAverageSalaryByPost();
    }
}
