<template>
  <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b">
    <el-submenu index="1">
      <template #title>部门管理</template>
      <el-menu-item index="1-1"><router-link to="/department" class="a">管理部门</router-link></el-menu-item>
      <el-menu-item index="1-2"><router-link to="/adddepartment" class="a">新增部门</router-link></el-menu-item>
    </el-submenu>
    <el-submenu index="2">
      <template #title>岗位管理</template>
      <el-menu-item index="2-1"><router-link to="/post" class="a">管理岗位</router-link></el-menu-item>
      <el-menu-item index="2-2"><router-link to="/addpost" class="a">新增岗位</router-link></el-menu-item>
      <el-menu-item index="2-3"><router-link to="/postDetail" class="a">岗位设置详情</router-link></el-menu-item>
    </el-submenu>
    <el-submenu index="3">
      <template #title>员工管理</template>
      <el-menu-item index="3-1"><router-link to="/staff" class="a">管理员工</router-link></el-menu-item>
      <el-menu-item index="3-2"><router-link to="/addStaff" class="a">新增员工</router-link></el-menu-item>
      <el-menu-item index="3-3"><router-link to="/skill" class="a">技能管理</router-link></el-menu-item>
    </el-submenu>
    <el-menu-item index="4"><router-link to="/peroidOp" class="a">试用期管理</router-link></el-menu-item>
    <el-submenu index="5">
      <template #title>岗位调动管理</template>
      <el-menu-item index="5-1"><router-link to="/addTransferStaff" class="a">录入岗位调动</router-link></el-menu-item>
      <el-menu-item index="5-2"><router-link to="/transferStaff" class="a">查询调动员工</router-link></el-menu-item>
    </el-submenu>
    <el-submenu index="6">
      <template #title>员工离职管理</template>
      <el-menu-item index="6-1"><router-link to="/addQuit" class="a">录入离职员工</router-link></el-menu-item>
      <el-menu-item index="6-2"><router-link to="/quit" class="a">查询已离职员工</router-link></el-menu-item>
    </el-submenu>
    <el-submenu index="7">
      <template #title>报表管理</template>
      <el-menu-item index="7-1"><router-link to="/newStaffReport" class="a">新聘员工报表</router-link></el-menu-item>
      <el-menu-item index="7-2"><router-link to="/quitStaffReport" class="a">离职员工报表</router-link></el-menu-item>
      <el-menu-item index="7-3"><router-link to="/transferStaffReport" class="a">岗位调动报表</router-link></el-menu-item>
    </el-submenu>
    <el-menu-item index="8"><router-link to="/dashBoard" class="a">岗位统计分析</router-link></el-menu-item>
    <el-menu-item index="9"><router-link to="/addWorkReport" class="a">录入工作日志</router-link></el-menu-item>
    <el-menu-item index="10"><router-link to="/workReport" class="a">查看工作日志</router-link></el-menu-item>
  </el-menu>
</template>
<script>
export default {
  name: 'NavMain',
  data() {
    return {
      //员工信息中心点亮
      activeIndex: '9'
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>
<style>
.a{
  text-decoration: none;
  color: aliceblue;
}
</style>
