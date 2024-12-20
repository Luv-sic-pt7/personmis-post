<template>
  <NavMain></NavMain>
  <div class="box">
    <br>
    <el-form ref="skillForm" :model="skillForm" :rules="rules" style="width:100%;" label-width="30%">
      <el-row>
        <el-col :span="8">
          <el-form-item label="技能关键词" prop="skillKeyword">
            <el-input v-model="skillForm.skillKeyword" placeholder="请输入技能关键词"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item>
            <el-button type="primary" @click="searchEmployeesBySkills" :loading="loading">{{loadingText}}</el-button>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="showNoResults">
        <el-col :span="24">
          <p>没有会该技能的员工</p>
        </el-col>
      </el-row>
      <el-row v-if="searchResults.length > 0">
        <el-col :span="24">
          <table class="results-table">
            <thead>
            <tr>
              <th>ID</th>
              <th>姓名</th>
              <th>技能</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="result in searchResults" :key="result.id">
              <td>{{ result.id }}</td>
              <td>{{ result.sname }}</td>
              <td>{{ result.skills }}</td>
            </tr>
            </tbody>
          </table>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="员工ID" prop="employeeId">
            <el-input v-model="skillForm.employeeId" placeholder="请输入员工ID"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item>
            <el-button type="primary" @click="fetchEmployeeInfo">查询</el-button>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="employeeInfo">
        <el-col :span="24">
          <table class="info-table">
            <tr>
              <th>ID</th>
              <td>{{ employeeInfo.id }}</td>
            </tr>
            <tr>
              <th>姓名</th>
              <td>{{ employeeInfo.sname }}</td>
            </tr>
            <tr>
              <th>技能</th>
              <td>
                <el-select v-model="selectedSkill" @change="updateSelectedSkill" placeholder="请选择或手动输入">
                  <el-option v-for="skill in skillOptions" :key="skill" :label="skill" :value="skill"></el-option>
                </el-select>
                <el-input v-model="updatedSkills" placeholder="手动输入技能"></el-input>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <el-button type="primary" @click="updateEmployeeSkills">更新技能</el-button>
              </td>
            </tr>
          </table>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios';
import NavMain from '@/components/NavMain.vue';

export default {
  components: {
    NavMain,
  },
  data() {
    return {
      skillForm: {
        skillKeyword: '',
        employeeId: '',
      },
      employeeInfo: null,
      updatedSkills: '',
      selectedSkill: '',
      skillOptions: ['招聘', '软件测试', '财务审计', '数据挖掘', '项目管理'],
      searchResults: [],
      showNoResults: false,
      loading: false,
      loadingText: '搜索',
      rules: {
        skillKeyword: [{ required: true, message: '请输入技能关键词', trigger: 'blur' }],
        employeeId: [{ required: true, message: '请输入员工ID', trigger: 'blur' }],
      },
    };
  },
  methods: {
    async fetchEmployeeInfo() {
      try {
        const response = await axios.get(`/api/employee/${this.skillForm.employeeId}`);
        this.employeeInfo = response.data;
        this.updatedSkills = this.employeeInfo.skills;
      } catch (error) {
        console.error('查询员工信息失败', error);
        alert('查询失败，请检查输入的员工ID');
      }
    },
    updateSelectedSkill() {
      if (this.selectedSkill) {
        this.updatedSkills = this.selectedSkill;
      }
    },
    async updateEmployeeSkills() {
      try {
        const response = await axios.put(`/api/employee/${this.skillForm.employeeId}`, {
          skills: this.updatedSkills,
        }, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        this.employeeInfo.skills = response.data.skills;
        alert('技能更新成功');
      } catch (error) {
        console.error('更新员工技能失败', error);
        alert('更新失败，请稍后再试');
      }
    },
    async searchEmployeesBySkills() {
      try {
        this.loading = true;
        this.loadingText = '搜索中...';
        const response = await axios.get('/api/employees', { params: { skills: this.skillForm.skillKeyword } });
        this.searchResults = response.data;
        this.showNoResults = this.searchResults.length === 0 && this.skillForm.skillKeyword !== '';
        this.loading = false;
        this.loadingText = '搜索';
      } catch (error) {
        console.error('搜索员工信息失败', error);
        alert('搜索失败，请稍后再试');
        this.loading = false;
        this.loadingText = '搜索';
      }
    },
  },
};
</script>

<style scoped>
.box {
  width: 90%;
  margin-left: 5%;
}
.el-select {
  width: 100%;
}
.results-table, .info-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}
.results-table th, .results-table td, .info-table th, .info-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}
.results-table th, .info-table th {
  background-color: #f2f2f2;
}
</style>