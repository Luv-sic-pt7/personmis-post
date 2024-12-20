<template>
  <NavMain></NavMain>
  <div class="box1">
    <br>
    <el-form ref="selectForm" :model="selectForm" style="width:50%;" label-width="50%" >
      <el-form-item label="部门名称"  prop="dname">
        <el-input v-model="selectForm.dname"  placeholder="请输入部门名"></el-input>
      </el-form-item>
      <el-form-item label="部门类型" prop="dtype">
        <el-select v-model="selectForm.dtype" placeholder="请选择部门类型">
          <el-option v-for="(item,index) in dtypes" :key="index" :label="item" :value="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="selectDepartmentsByCon">查询</el-button>
      </el-form-item>
    </el-form>
  </div>

  <div class="box2">
    <el-table
        :data="tableData" :header-cell-style="headClass"
        :cell-style="{ textAlign: 'center' }"
        :default-sort="{prop: 'id', order: 'ascending'}">
      <el-table-column prop="id" label="ID" sortable></el-table-column>
      <el-table-column prop="dname" label="名称"></el-table-column>
      <el-table-column prop="dtype" label="类型"></el-table-column>
      <el-table-column prop="establishmentdate1" label="成立日期" sortable></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
              size="mini"  type="success"
              @click="handleEdit(scope.$index, scope.row, 'update')">编辑</el-button>
          <el-button
              size="mini" type="primary"
              @click="handleEdit(scope.$index, scope.row, 'detail')">详情</el-button>
          <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <div class="block">
    <el-pagination
        @current-change="handleCurrentChange"
        v-model:currentPage="currentPage"
        :page-size="pageSize"
        layout="total, prev, pager, next"
        :total="total">
    </el-pagination>
  </div>

  <!-- 部门详情对话框 -->
  <el-dialog title="部门详情" v-model="dialogVisible" width="50%">
    <el-form ref="detailData" :model="detailData" disabled style="width:80%;" label-width="40%">
      <el-form-item label="部门名称" prop="dname">
        <el-input v-model="detailData.dname"></el-input>
      </el-form-item>
      <el-form-item label="部门类型" prop="dtype">
        <el-select v-model="detailData.dtype">
          <el-option v-for="(item, index) in dtypes" :key="index" :label="item" :value="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="电话" prop="dtel">
        <el-input v-model="detailData.dtel"></el-input>
      </el-form-item>
      <el-form-item label="传真" prop="dfax">
        <el-input v-model="detailData.dfax"></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input v-model="detailData.description" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="上级部门" prop="supdepartment">
        <el-select v-model="detailData.supdepartment">
          <el-option v-for="(item, index) in supdepartments" :key="index" :label="item.dname" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="showpost">岗位设置情况</el-button>
    </template>
  </el-dialog>

  <el-dialog title="岗位设置情况" v-model="postDialogVisible" width="50%">
    <el-table :data="postSettings" :header-cell-style="headClass" :cell-style="tableCellStyle">
      <el-table-column prop="postName" label="岗位名称"></el-table-column>
      <el-table-column prop="employeeNames" label="员工姓名"></el-table-column>
      <el-table-column prop="employeeCount" label="岗位员工数量"></el-table-column>
    </el-table>
    <template #footer>
      <el-button type="primary" @click="postDialogVisible = false">关闭</el-button>
    </template>
  </el-dialog>

  <el-dialog title="部门修改" v-model="dialogVisibleDetail"  width="40%" >
    <el-form ref="detailData" :model="detailData"  style="width:80%;"  label-width="40%" >
      <el-form-item label="部门名称"  prop="dname">
        <el-input v-model="detailData.dname"></el-input>
      </el-form-item>
      <el-form-item label="部门类型" prop="dtype">
        <el-select v-model="detailData.dtype">
          <el-option v-for="(item,index) in dtypes" :key="index" :label="item" :value="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="电话"  prop="dtel">
        <el-input v-model="detailData.dtel" ></el-input>
      </el-form-item>
      <el-form-item label="传真"  prop="dfax">
        <el-input v-model="detailData.dfax"></el-input>
      </el-form-item>
      <el-form-item label="描述"  prop="description">
        <el-input v-model="detailData.description" type="textarea" ></el-input>
      </el-form-item>
      <el-form-item label="上级部门" prop="supdepartment">
        <el-select v-model="detailData.supdepartment">
          <el-option v-for="(item,index) in supdepartments" :key="index" :label="item.dname" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="update(detailData)">修改</el-button>
        <el-button type="danger" @click="cancel">重置</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

</template>

<script>
import NavMain from '@/components/NavMain.vue'

export default {
  components: {
    NavMain
  },
  created: function () {
    this.loadDepartments()
  },
  data() {
    return {
      dtypes: ['', '公司', '部门', '车间', '生产线', '班组'],
      selectForm: { currentPage: 1, pageSize: 5, act: ''},
      currentPage: 1,
      tableData: [{}], // 定义空数组接收数据
      pageSize: 5,
      total: 0,
      dialogVisible: false, //部门详情
      postDialogVisible: false, //岗位设置详情·
      dialogVisibleDetail: false,//部门修改
      postSettings: [], // 存储岗位设置情况
      detailData: {},
      supdepartments: [{id: '', dname: ''}], // 定义空数组接收后台数据
      // 定义 headClass 和 tableCellStyle
      headClass: {
        'background-color': '#f4f4f4',
        'font-weight': 'bold',
        'text-align': 'center', // 设置表头居中
      },
      tableCellStyle: {
        'text-align': 'center', // 设置单元格内容居中
      },
    }
  },
  methods: {
    loadDepartments() {
      this.$axios
          .get('/getDepartmentByPage?currentPage=' + this.currentPage + '&&pageSize=' + this.pageSize)
          .then(successResponse => {
            this.tableData = successResponse.data.departs
            this.total = successResponse.data.total
          })
          .catch(failResponse => {
            this.$alert(failResponse.response.status)
          })
    },
    selectDepartmentsByCon() {
      this.selectForm.act = "byCon"
      this.$axios
          .post('/selectDepartmentsByCon', this.selectForm) // 直接提交表单
          .then(successResponse => {
            this.tableData = successResponse.data.departs
            this.total = successResponse.data.total
          })
          .catch(failResponse => {
            this.$alert(failResponse.response.status, {confirmButtonText: '确定'})
          })
    },
    handleCurrentChange(val) {
      this.currentPage = val
      if (this.selectForm.act === 'byCon') {
        this.selectForm.currentPage = this.currentPage
        this.selectForm.pageSize = this.pageSize
        this.selectDepartmentsByCon()
      } else {
        this.loadDepartments()
      }
    },
    handleEdit(index, row, act) {
      console.log(index, row);
      this.$axios
          .get('/getDepartmentDetail?id=' + row.id)
          .then(successResponse => {
            this.detailData = successResponse.data.aDepart
            this.supdepartments = successResponse.data.departs;
          })
          .catch(failResponse => {
            this.$alert(failResponse.response.status)
          })
      if (act === 'update')
        this.dialogVisibleDetail = true
      else
        this.dialogVisible = true
    },
    showpost() {
      const departmentId = this.detailData.id;
      console.log('Department ID:', departmentId);
      if (!departmentId) {
        this.$message.error("部门ID为空，无法查询岗位设置！");
        return;
      }
      this.$axios
          .get(`/getPostSettingsByDepartment?departmentId=${departmentId}`)
          .then((response) => {
            console.log("Post settings response:", response);
            this.postSettings = response.data;
            this.postDialogVisible = true;
          })
          .catch((error) => {
            this.$alert("获取岗位设置情况失败：" + error.response.status);
          });
    },
    handleDelete(index, row) {
      console.log(index, row);
      this.$confirm('删除部门, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios
            .post('/deleteDepartment?id=' + row.id)
            .then(successResponse => {
              if (successResponse.data === "ok") {
                this.$message({
                  type: 'success',
                  message: '删除成功!'
                });
                // 删除成功后重新加载
                this.loadDepartments()
              } else {
                this.$alert('不能删除有关联数据！', {confirmButtonText: '确定'})
              }
            })
            .catch(failResponse => {
              this.$alert(failResponse.response.status, {confirmButtonText: '确定'})
            })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    cancel() {
      this.$refs['detailData'].resetFields()
    },
    update() {
      this.$axios
          .post('/updateDepartment', this.detailData) // 直接提交表单
          .then(successResponse => {
            if (successResponse.data === "ok") {
              this.$alert('修改成功', {confirmButtonText: '确定'})
              this.dialogVisibleDetail = false
              // 修改成功后重新加载
              this.loadDepartments()
            } else {
              this.$alert('修改失败', {confirmButtonText: '确定'})
              this.dialogVisibleDetail = false
            }
          })
          .catch(failResponse => {
            this.$alert(failResponse.response.status, {confirmButtonText: '确定'})
          })
    }
  }
}
</script>

<style scoped>
.box1 {
  width: 80%;
  height: 200px;
  margin-left: 20%;
}

.box2 {
  margin-left: 5px;
  margin-right: 5px;
}

.el-select {
  width: 100%
}
</style>