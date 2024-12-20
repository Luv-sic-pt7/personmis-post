<template>
  <NavMain></NavMain>
  <div class="box">
    <br>
    <el-form ref="filterForm" :model="filter" :rules="rules" style="width:100%;" label-width="120px">
      <el-form-item label="请选择日期" prop="date">
        <el-date-picker v-model="filter.date" type="date" placeholder="选择日期" style="width: 100%;" :clearable="false"
                        :picker-options="pickerOptions" :disabled-date="disabledDate"/>
      </el-form-item>
      <el-form-item label="任务类型" prop="taskType">
        <el-input v-model="filter.taskType" placeholder="输入任务类型搜索" clearable style="width: 100%;"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="fetchWorkLogs" :loading="loading">{{ loadingText }}</el-button>
      </el-form-item>
    </el-form>
  <div class="table-container">
    <el-table :data="workLogs" border style="width: 100%;" size="small" stripe>
      <el-table-column label="员工姓名" prop="sname" width="120"></el-table-column>
      <el-table-column label="日期" prop="date" width="150"></el-table-column>
      <el-table-column label="任务类型" prop="taskType" width="180"></el-table-column>
      <el-table-column label="任务描述" prop="taskDescription"></el-table-column>
      <el-table-column label="任务耗时 (小时)" prop="workHours" width="180"></el-table-column>
      <el-table-column label="任务难度" prop="taskDifficulty" width="180"></el-table-column>
      <el-table-column label="完成任务数量" prop="tasksCompleted" width="180"></el-table-column>
      <el-table-column label="详细工作内容" prop="workDetails"></el-table-column>
    </el-table>
    </div>
  </div>
</template>

<script>
import {ref, onMounted} from "vue";
import {ElForm, ElFormItem, ElDatePicker, ElInput, ElButton, ElTable, ElTableColumn} from "element-plus";
import axios from "axios";
import NavMain from '@/components/NavMain.vue';

export default {
  name: "WorkReport",
  components: {
    NavMain,
    ElForm,
    ElFormItem,
    ElDatePicker,
    ElInput,
    ElButton,
    ElTable,
    ElTableColumn,
  },
  setup() {
    const workLogs = ref([
      {
        sname: '张三',
        date: '2024-12-19',
        taskType: '开发',
        taskDescription: '开发新功能',
        workHours: 8,
        taskDifficulty: '中等',
        tasksCompleted: 3,
        workDetails: '完成了用户登录模块，修复了几个bug',
      },
      {
        sname: '李四',
        date: '2024-12-19',
        taskType: '设计',
        taskDescription: 'UI设计优化',
        workHours: 6,
        taskDifficulty: '较难',
        tasksCompleted: 2,
        workDetails: '优化了移动端UI，设计了新的登录界面',
      },
      {
        sname: '王五',
        date: '2024-12-18',
        taskType: '测试',
        taskDescription: '功能测试',
        workHours: 7,
        taskDifficulty: '简单',
        tasksCompleted: 5,
        workDetails: '完成了多个模块的功能测试，提交了测试报告',
      }
    ]);

    const filter = ref({
      date: "",
      taskType: "",
    });

    const rules = {
      date: [{required: true, message: "请选择日期", trigger: "blur"}],
      taskType: [{required: true, message: "请输入任务类型", trigger: "blur"}],
    };

    const loading = ref(false);
    const loadingText = ref('搜索');

    const pickerOptions = {
      disabledDate(time) {
        return time.getTime() > Date.now();
      }
    };

    const fetchWorkLogs = () => {
      loading.value = true;
      loadingText.value = '搜索中...';
      const params = {
        date: filter.value.date,
        taskType: filter.value.taskType,
      };
      axios
          .get("/api/worklogs", {params})
          .then((response) => {
            workLogs.value = response.data;
            loading.value = false;
            loadingText.value = '搜索';
          })
          .catch((error) => {
            console.error("获取工作量数据失败:", error);
            loading.value = false;
            loadingText.value = '搜索';
          });
    };

    onMounted(() => {
      // 可以在这里初始化假数据
      // fetchWorkLogs(); // Uncomment this line to fetch real data when API is available
    });

    return {
      workLogs,
      filter,
      rules,
      loading,
      loadingText,
      pickerOptions,
      fetchWorkLogs,
    };
  },
};
</script>

<style scoped>
.box {
  width: 80%;
}

.el-select {
  width: 100%;
}

.table-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  margin-left: 20px;
}
</style>
