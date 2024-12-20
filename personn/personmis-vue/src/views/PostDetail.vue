<template>
  <NavMain></NavMain>
  <div class="box1">
    <br>
    <el-form ref="selectForm" :model="selectForm" style="width:50%;" label-width="50%">
      <el-form-item label="岗位名称" prop="pname">
        <el-input v-model="selectForm.pname" placeholder="请输入岗位名称"></el-input>
      </el-form-item>
      <el-form-item style="margin-top: 30px;">
        <el-button type="primary" @click="getPostDetailsByName">查询岗位设置详情</el-button>
      </el-form-item>
    </el-form>
  </div>

  <!-- 岗位设置详情表格 -->
  <div class="box2">
    <el-table
        :data="postDetails"
        :header-cell-style="headClass"
        :cell-style="{ textAlign: 'center' }"
        :default-sort="{ prop: 'departmentId', order:'descending' }"
        empty-text="暂无数据"
    >
      <el-table-column prop="departmentId" label="所属部门ID" sortable></el-table-column>
      <el-table-column prop="departmentName" label="所属部门名称"></el-table-column>
      <el-table-column prop="pname" label="岗位名称"></el-table-column>
      <el-table-column prop="ptype" label="岗位类型"></el-table-column>
      <el-table-column prop="organization" label="编制人数" sortable></el-table-column>
      <el-table-column prop="currentCount" label="岗位现有人数" sortable></el-table-column>
      <el-table-column prop="responsibilities" label="岗位职责"></el-table-column>
      <el-table-column prop="skills" label="所需技能"></el-table-column>
    </el-table>
  </div>
</template>

<script>
import NavMain from '@/components/NavMain.vue';

export default {
  components: {
    NavMain
  },
  data() {
    return {
      selectForm: {
        pname: '' // 岗位名称
      },
      postDetails: [] // 初始化为空数组
    };
  },
  methods: {
    // 表头样式
    headClass() {
      return {
        textAlign: 'center',
        background: 'rgb(242, 242, 242)',
        color: 'rgb(140, 138, 140)'
      };
    },
    // 查询岗位详情（按名称）
    getPostDetailsByName() {
      if (!this.selectForm.pname) {
        this.$alert('请输入岗位名称进行查询', '提示', { confirmButtonText: '确定' });
        return;
      }
      this.$axios
          .get('getPostDetailsAcrossDepartments', { params: { pname: this.selectForm.pname } })
          .then((response) => {
            if (response.data.length === 0) {
              this.$alert('没有找到相关岗位信息，请检查岗位名称是否正确', '提示', { confirmButtonText: '确定' });
            }
            this.postDetails = response.data;
          })
          .catch((error) => {
            console.error('查询失败:', error);
            this.$alert('获取数据失败，请稍后重试！', '错误', { confirmButtonText: '确定' });
          });
    }
  }
};
</script>

<style scoped>
.box1 {
  margin-top: 25px;
  width: 80%;
  height: 160px;
  margin-left: 20%;
}
.box2 {
  margin-left: 5px;
  margin-right: 5px;
}
</style>