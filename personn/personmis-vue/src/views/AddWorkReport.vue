<template>
  <NavMain></NavMain>
  <div class="box">
    <br>
    <el-form ref="addForm" :model="addForm" :rules="rules" style="width:50%;" label-width="120px">
      <el-form-item label="请选择日期" prop="reportDate">
        <el-date-picker v-model="addForm.reportDate" type="date" placeholder="选择日期" style="width: 100%;" :clearable="false" :picker-options="pickerOptions" :disabled-date="disabledDate" />
      </el-form-item>
      <el-form-item label="任务类型" prop="taskType">
        <el-radio-group v-model="addForm.taskType">
          <el-radio label="采购管理"></el-radio>
          <el-radio label="采购优化"></el-radio>
          <el-radio label="测试"></el-radio>
          <el-radio label="法务审查"></el-radio>
          <el-radio label="法务事务"></el-radio>
          <el-radio label="开发"></el-radio>
          <el-radio label="人力资源"></el-radio>
          <el-radio label="人事管理"></el-radio>
          <el-radio label="软件测试"></el-radio>
          <el-radio label="市场策划"></el-radio>
          <el-radio label="市场调研"></el-radio>
          <el-radio label="市场推广"></el-radio>
          <el-radio label="数据测试"></el-radio>
          <el-radio label="数据分析"></el-radio>
          <el-radio label="销售"></el-radio>
          <el-radio label="行政管理"></el-radio>
          <el-radio label="招聘"></el-radio>
          <el-radio label="其他">
            <el-input v-if="addForm.taskType === '其他'" v-model="otherTaskType" placeholder="请输入其他任务类型" style="width: 200px; margin-left: 10px;"></el-input>
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="任务描述" prop="taskDescription">
        <el-input v-model="addForm.taskDescription" placeholder="请输入任务描述" maxlength="255" style="width: 100%;" />
      </el-form-item>
      <el-form-item label="任务耗时（h）" prop="taskDuration">
        <el-input-number v-model="addForm.taskDuration" :min="0" :step="0.5" placeholder="请输入任务耗时" style="width: 40%;" />
      </el-form-item>
      <el-form-item label="任务难度" prop="taskDifficulty">
        <el-radio-group v-model="addForm.taskDifficulty">
          <el-radio label="低" style="margin-right: 15px;">低</el-radio>
          <el-radio label="中" style="margin-right: 15px;">中</el-radio>
          <el-radio label="高" style="margin-right: 15px;">高</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="完成任务数量" prop="tasksCompleted">
        <el-input-number v-model="addForm.tasksCompleted" :min="0" :step="1" placeholder="请输入任务数量" style="width: 40%;" />
      </el-form-item>
      <el-form-item label="详细工作内容" prop="workDetails">
        <el-input v-model="addForm.workDetails" type="textarea" placeholder="请输入详细工作内容" rows="4" maxlength="500" style="width: 100%;" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="add" :loading="loadingbut">{{loadingbuttext}}</el-button>
        <el-button type="danger" @click="cancel">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import NavMain from '@/components/NavMain.vue'
export default {
  components: {
    NavMain
  },
  data() {
    return {
      addForm: {
        reportDate: "",
        taskType: "",
        taskDescription: "",
        taskDuration: 0,
        taskDifficulty: "低",
        tasksCompleted: 0,
        workDetails: ""
      },
      rules: {
        reportDate: [{ required: true, message: "请选择日期", trigger: "blur" }],
        taskType: [{ required: true, message: "请选择任务类型", trigger: "change" }],
        taskDescription: [{ required: true, message: "请输入任务描述", trigger: "blur" }],
        taskDuration: [
          { required: true, message: "请输入任务耗时", trigger: "blur" },
          { type: "number", message: "任务耗时必须为数字", trigger: "blur" }
        ],
        taskDifficulty: [{ required: true, message: "请选择任务难度", trigger: "change" }],
        tasksCompleted: [
          { required: true, message: "请输入完成任务数量", trigger: "blur" },
          { type: "number", message: "任务数量必须为数字", trigger: "blur" }
        ],
        workDetails: [{ required: true, message: "请输入详细工作内容", trigger: "blur" }]
      },
      loadingbut: false,
      loadingbuttext: '新增',
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      }
    };
  },
  methods: {
    add() {
      if (this.addForm.taskType === '其他' && this.otherTaskType) {
        this.addForm.taskType = this.otherTaskType;
      }
      this.$refs['addForm'].validate((valid) => {
        if (valid) {
          this.loadingbut = true;
          this.loadingbuttext = '添加中...';
          this.addForm.reportDate = new Date(this.addForm.reportDate);
          console.log(this.addForm);
          this.$axios
              .post('/workreport/addWorkReport', this.addForm)
              .then(successResponse => {
                if (successResponse.data === "ok") {
                  this.$alert('添加成功', { confirmButtonText: '确定' });
                  this.$router.replace({ path: '/workreport' });
                } else {
                  this.$alert('添加失败', { confirmButtonText: '确定' });
                  this.loadingbut = false;
                  this.loadingbuttext = '新增';
                }
              })
              .catch(failResponse => {
                this.$alert(failResponse.response.status, { confirmButtonText: '确定' });
              });
        } else {
          this.$alert('表单验证失败', { confirmButtonText: '确定' });
          return false;
        }
      });
    },
    cancel() {
      this.$refs['addForm'].resetFields();
    }
  }
};
</script>

<style scoped>
.box {
  width: 80%;
  margin-left: 20%;
}
.el-select {
  width: 100%;
}
</style>