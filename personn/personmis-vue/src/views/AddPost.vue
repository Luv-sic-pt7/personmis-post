<template>
  <NavMain></NavMain>
  <div class="box">
    <br>
    <el-form ref="addForm" :model="addForm" :rules="rules" style="width:50%;" label-width="50%">
      <!-- 岗位名称 -->
      <el-form-item label="岗位名称" prop="pname">
        <el-input v-model="addForm.pname" placeholder="请输入岗位名"></el-input>
      </el-form-item>

      <!-- 岗位类型 -->
      <el-form-item label="岗位类型" prop="ptype">
        <el-select v-model="addForm.ptype" placeholder="请选择岗位类型">
          <el-option v-for="(item, index) in ptypes" :key="index" :label="item" :value="item"></el-option>
        </el-select>
      </el-form-item>

      <!-- 编制人数 -->
      <el-form-item label="编制人数" prop="organization">
        <el-input v-model="addForm.organization" placeholder="请输入编制人数"></el-input>
      </el-form-item>

      <!-- 部门选择 -->
      <el-form-item label="所属部门" prop="departmentId">
        <el-select v-model="addForm.departmentId" placeholder="请选择部门">
          <el-option v-for="(item, index) in departments" :key="index" :label="item.dname" :value="item.id"></el-option>
        </el-select>
      </el-form-item>

      <!-- 岗位职责 -->
      <el-form-item label="岗位职责" prop="responsibilities">
        <el-input type="textarea" v-model="addForm.responsibilities" placeholder="请输入岗位职责"></el-input>
      </el-form-item>

      <!-- 所需技能 -->
      <el-form-item label="所需技能" prop="skills">
        <el-input type="textarea" v-model="addForm.skills" placeholder="请输入所需技能"></el-input>
      </el-form-item>

      <!-- 操作按钮 -->
      <el-form-item>
        <el-button type="primary" @click="add(addForm)" :loading="loadingbut">{{ loadingbuttext }}</el-button>
        <el-button type="danger" @click="cancel">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import NavMain from "@/components/NavMain.vue";

export default {
  components: {
    NavMain,
  },
  data() {
    return {
      // 岗位类型选项
      ptypes: ["管理", "技术", "营销", "市场"],
      // 新增岗位表单
      addForm: {
        pname: "", // 岗位名称
        ptype: "", // 岗位类型
        organization: "", // 编制人数
        departmentId: "", // 所属部门ID
        responsibilities: "", // 岗位职责
        skills: "", // 所需技能
      },
      // 部门列表
      departments: [], // 存储从后端加载的部门数据
      // 表单验证规则
      rules: {
        pname: [{ required: true, message: "请输入岗位名称", trigger: "blur" }],
        ptype: [{ required: true, message: "请选择岗位类型", trigger: "change" }],
        organization: [{ required: true, message: "请输入编制人数", trigger: "blur" }],
        departmentId: [{ required: true, message: "请选择部门", trigger: "change" }],
      },
      loadingbut: false, // 按钮加载状态
      loadingbuttext: "新增",
    };
  },
  created() {
    this.loadDepartment(); // 加载部门列表
  },
  methods: {

    // 新增岗位
    add() {
      this.$refs["addForm"].validate((valid) => {
        if (valid) {
          this.loadingbut = true;
          this.loadingbuttext = "添加中...";
          this.$axios
              .post("/addPost", this.addForm) // 提交新增岗位表单数据
              .then((response) => {
                if (response.data === "ok") {
                  this.$alert("添加成功", { confirmButtonText: "确定" });
                  this.$router.replace({ path: "/post" });
                } else {
                  this.$alert("添加失败", { confirmButtonText: "确定" });
                  this.loadingbut = false;
                  this.loadingbuttext = "新增";
                }
              })
              .catch((error) => {
                console.error("提交失败:", error);
                this.$alert("提交失败，请稍后重试", "错误", { confirmButtonText: "确定" });
              });
        } else {
          this.$alert("表单验证失败", { confirmButtonText: "确定" });
          return false;
        }
      });
    },

    // 重置表单
    cancel() {
      this.$refs["addForm"].resetFields();
    },
    // 加载部门数据
    loadDepartment() {
      this.$axios
          .get('/getDepartment')
          .then(successResponse => {
            this.departments = successResponse.data
          })
          .catch(failResponse => {
            this.$alert(failResponse.response.status)
          })
    },

  },
};
</script>

<style scoped>
.box {
  width: 80%;
  height: 200px;
  margin-left: 20%;
}
.el-select{
  width:100%
}
</style>