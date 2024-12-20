<template>
  <NavMain></NavMain>
  <div class="box1">
    <br />
    <el-form ref="selectForm" :model="selectForm" style="width: 50%;" label-width="50%">
      <el-form-item label="岗位名称" prop="pname">
        <el-input v-model="selectForm.pname" placeholder="请输入岗位名"></el-input>
      </el-form-item>
      <el-form-item label="岗位类型" prop="ptype">
        <el-select v-model="selectForm.ptype" placeholder="请选择岗位类型">
          <el-option v-for="(item, index) in ptypes" :key="index" :label="item" :value="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所属部门" prop="departmentId">
        <el-select v-model="selectForm.departmentId" placeholder="请选择部门">
          <!-- 空选项 -->
          <el-option :key="0" :label="'不限'" :value="null"></el-option>
          <el-option v-for="(item, index) in departments" :key="index" :label="item.dname" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="selectPostsByCon">查询</el-button>
      </el-form-item>
    </el-form>
  </div>

  <div class="box2">
    <el-table
        :data="tableData"
        :header-cell-style="headClass"
        :cell-style="{ textAlign: 'center' }"
        :default-sort="{ prop: 'id', order: 'descending' }"
    >
      <el-table-column prop="id" label="ID" sortable></el-table-column>
      <el-table-column prop="pname" label="岗位名称"></el-table-column>
      <el-table-column prop="ptype" label="岗位类型"></el-table-column>
      <el-table-column prop="dname" label="所属部门"></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="mini" type="success" @click="handleEdit(scope.$index, scope.row, 'update')">编辑</el-button>
          <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row, 'detail')">详情</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
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
        :total="total"
    ></el-pagination>
  </div>

  <el-dialog title="岗位修改" v-model="postDialogVisible" width="40%">
    <el-form ref="updateForm" :model="updateForm" style="width: 80%;" label-width="40%">
      <el-form-item label="岗位名称" prop="pname">
        <el-input v-model="updateForm.pname"></el-input>
      </el-form-item>
      <el-form-item label="岗位类型" prop="ptype">
        <el-select v-model="updateForm.ptype">
          <el-option v-for="(item, index) in ptypes" :key="index" :label="item" :value="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="编制人数" prop="organization">
        <el-input v-model="updateForm.organization"></el-input>
      </el-form-item>
      <el-form-item label="所属部门" prop="departmentId">
        <el-select v-model="updateForm.departmentId">
          <el-option v-for="(item, index) in departments" :key="index" :label="item.dname" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="岗位职责" prop="responsibilities">
        <el-input type="textarea" v-model="updateForm.responsibilities"></el-input>
      </el-form-item>
      <el-form-item label="所需技能" prop="skills">
        <el-input v-model="updateForm.skills"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="update(updateForm)" :loading="loadingbut">{{ loadingbuttext }}</el-button>
        <el-button type="danger" @click="cancel">重置</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

  <!--岗位详情-->
  <el-dialog title="岗位详情" v-model="postDetailDialogVisible" width="50%">
    <el-form ref="updateForm" :model="updateForm" style="width: 80%;" label-width="40%" disabled>
      <el-form-item label="岗位名称" prop="pname">
        <el-input v-model="updateForm.pname" disabled></el-input>
      </el-form-item>
      <el-form-item label="岗位类型" prop="ptype">
        <el-select v-model="updateForm.ptype" disabled>
          <el-option v-for="(item, index) in ptypes" :key="index" :label="item" :value="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="编制人数" prop="organization">
        <el-input v-model="updateForm.organization" disabled></el-input>
      </el-form-item>
      <el-form-item label="所属部门" prop="departmentId">
        <el-select v-model="updateForm.departmentId">
          <el-option v-for="(item, index) in departments" :key="index" :label="item.dname" :value="item.id" disabled></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="岗位职责" prop="responsibilities">
        <el-input type="textarea" v-model="updateForm.responsibilities" disabled></el-input>
      </el-form-item>
      <el-form-item label="所需技能" prop="skills">
        <el-input v-model="updateForm.skills" disabled></el-input>
      </el-form-item>
    </el-form>

    <!-- footer 插槽部分 -->
    <template #footer>
      <el-button type="primary" @click="showDepartmentDetail(updateForm.departmentId)">所属部门详情</el-button>
    </template>
  </el-dialog>

  <el-dialog title="部门详情" v-model="departmentDialogVisible" width="50%">
    <el-form ref="detailData" :model="departmentDetail" disabled style="width: 80%;" label-width="40%">
      <el-form-item label="部门名称">
        <el-input v-model="departmentDetail.dname" disabled></el-input>
      </el-form-item>
      <el-form-item label="部门类型">
        <el-input v-model="departmentDetail.dtype" disabled></el-input>
      </el-form-item>
      <el-form-item label="联系电话">
        <el-input v-model="departmentDetail.dtel" disabled></el-input>
      </el-form-item>
      <el-form-item label="传真">
        <el-input v-model="departmentDetail.dfax" disabled></el-input>
      </el-form-item>
      <el-form-item label="描述">
        <el-input type="textarea" v-model="departmentDetail.description" disabled></el-input>
      </el-form-item>
      <el-form-item label="成立日期">
        <el-input v-model="departmentDetail.establishmentdate" disabled></el-input>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import NavMain from '@/components/NavMain.vue';
export default {
  components: {
    NavMain,
  },
  created: function () {
    // 确保部门数据先加载完成，再加载岗位数据
    this.loadDepartment().then(() => {
      this.loadPosts();
    });
  },
  data() {
    return {
      ptypes: ['', '管理', '技术', '营销', '市场'],
      selectForm: {
        pname: '', // 岗位名称
        ptype: '', // 岗位类型
        departmentId: '', // 所属部门ID
        currentPage: 1, // 当前页码
        pageSize: 5, // 每页显示的记录数
        act: '', // 操作类型
      },
      departments: [], // 存储从后端加载的部门数据
      currentPage: 1,
      tableData: [], // 接收查询结果数据
      pageSize: 5,
      total: 0,
      updateForm: {}, // 编辑或详情表单数据
      loadingbut: false,
      loadingbuttext: '修改',
      postDialogVisible: false,
      postDetailDialogVisible: false,
      departmentDialogVisible: false,
      departmentDetail: {}, // 部门详情数据
    };
  },
  methods: {
    // 加载部门数据
    loadDepartment() {
      return this.$axios
          .get('/getDepartment')
          .then((successResponse) => {
            this.departments = successResponse.data;
          })
          .catch((failResponse) => {
            this.$alert(failResponse.response.status);
          });
    },

    // 加载岗位数据并添加部门名称映射
    loadPosts() {
      this.$axios
          .get(
              `/getPostByPage?currentPage=${this.currentPage}&&pageSize=${this.pageSize}`
          )
          .then((successResponse) => {
            const posts = successResponse.data.posts;

            // 映射部门名称到岗位数据
            posts.forEach((post) => {
              const department = this.departments.find(
                  (dept) => dept.id === post.departmentId
              );
              post.dname = department ? department.dname : '未知部门';
            });

            this.tableData = posts; // 更新表格数据
            this.total = successResponse.data.total;
          })
          .catch((failResponse) => {
            this.$alert(failResponse.response.status);
          });
    },

    selectPostsByCon() {
      console.log('查询条件:', this.selectForm);
      const queryData = { ...this.selectForm };
      if (!queryData.departmentId) {
        delete queryData.departmentId;
      }
      this.selectForm.act = 'byCon';
      this.$axios
          .post('/selectPostByCon', this.selectForm) // 直接提交表单
          .then((successResponse) => {
            const posts = successResponse.data.posts;

            // 映射部门名称到岗位数据
            posts.forEach((post) => {
              const department = this.departments.find(
                  (dept) => dept.id === post.departmentId
              );
              post.dname = department ? department.dname : '未知部门';
            });

            this.tableData = posts; // 更新表格数据
            this.total = successResponse.data.total;
          })
          .catch((failResponse) => {
            this.$alert(failResponse.response.status, { confirmButtonText: '确定' });
          });
    },

    // 表头样式设置
    headClass() {
      return 'text-align: center;background:rgb(242,242,242);color:rgb(140,138,140)';
    },

    // 页码变化
    handleCurrentChange(val) {
      this.currentPage = val;
      if (this.selectForm.act === 'byCon') {
        this.selectForm.currentPage = this.currentPage;
        this.selectForm.pageSize = this.pageSize;
        this.selectPostsByCon();
      } else {
        this.loadPosts();
      }
    },

    handleEdit(index, row, act) {
      console.log(index, row);
      this.$axios
          .get(`/getPostDetail?id=${row.id}`)
          .then((successResponse) => {
            this.updateForm = successResponse.data;
          })
          .catch((failResponse) => {
            this.$alert(failResponse.response.status);
          });
      if (act === 'update') this.postDialogVisible = true;
      else this.postDetailDialogVisible = true;
    },
    handleDelete(index, row) {
      console.log(index, row);
      this.$confirm('删除岗位, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios
            .post('/deletePost', null, { params: { id: row.id } }) // 修改为更安全的方式传递参数
            .then(successResponse => {
              if (successResponse.data === "ok") {
                this.$message({
                  type: 'success',
                  message: '删除成功!'
                });
                // 删除成功后重新加载
                this.loadPosts();
              } else {
                // 如果后端返回的是关于关联数据的错误信息
                this.$alert(successResponse.data, { confirmButtonText: '确定' });
              }
            })
            .catch(failResponse => {
              // 处理请求失败的情况
              if (failResponse.response && failResponse.response.data) {
                this.$alert(failResponse.response.data, { confirmButtonText: '确定' });
              } else {
                // 如果没有错误信息，或者无法从响应中提取，显示一个通用错误
                this.$alert('删除失败，请稍后再试！', { confirmButtonText: '确定' });
              }
            });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    update() {
      this.loadingbut = true;
      this.loadingbuttext = '修改中...';
      this.$axios
          .post('/updatePost', this.updateForm) // 直接提交表单
          .then((successResponse) => {
            if (successResponse.data === 'ok') {
              this.$alert('修改成功', { confirmButtonText: '确定' });
              this.postDialogVisible = false;
              this.loadPosts();
            } else {
              this.$alert('修改失败', { confirmButtonText: '确定' });
            }
            this.loadingbut = false;
            this.loadingbuttext = '修改';
          })
          .catch((failResponse) => {
            this.$alert(failResponse.response.status, { confirmButtonText: '确定' });
          });
    },

    cancel() {
      this.$refs['updateForm'].resetFields();
    },

    // 显示部门详情
    showDepartmentDetail(departmentId) {
      if (!departmentId) {
        this.$alert('该岗位未关联部门', '提示', { confirmButtonText: '确定' });
        return;
      }
      console.log('传递的 departmentId:', departmentId);
      this.$axios
          .get(`/getDepartmentByDepartmentId?departmentId=${departmentId}`)
          .then((response) => {
            console.log('返回的部门详情:', response.data); // 打印后端返回的数据
            this.departmentDetail = response.data; // 填充部门详情数据
            this.departmentDialogVisible = true; // 显示部门详情模态框
          })
          .catch((error) => {
            console.error('Error fetching department details:', error);
            this.$alert('获取部门详情失败，请稍后重试！', '错误', {
              confirmButtonText: '确定',
            });
          });
    },
  },
};
</script>

<style scoped>
.box1 {
  width: 80%;
  height: 260px;
  margin-left: 20%;
}
.box2 {
  margin-left: 5px;
  margin-right: 5px;
}
.el-select {
  width: 100%;
}
</style>