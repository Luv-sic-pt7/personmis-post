import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Department from '../views/Department.vue'
import AddDepartment from '../views/AddDepartment.vue'
import Post from '../views/Post.vue'
import AddPost from '../views/AddPost.vue'
import AddStaff from '../views/AddStaff.vue'
import Staff from '../views/Staff.vue'
import PeroidOp from '../views/PeroidOp.vue'
import AddTransferStaff from '../views/AddTransferStaff.vue'
import TransferStaff from '../views/TransferStaff.vue'
import AddQuit from '../views/AddQuit.vue'
import Quit from '../views/Quit.vue'
import NewStaffReport from '../views/NewStaffReport.vue'
import QuitStaffReport from '../views/QuitStaffReport.vue'
import TransferStaffReport from '../views/TransferStaffReport.vue'
import DashBoard from '../views/DashBoard.vue'
import WorkReport from '../views/WorkReport.vue'
import AddWorkReport from '../views/AddWorkReport.vue'
import PostDetail from '../views/PostDetail.vue'
import Skill from "@/views/Skill.vue";
const routes = [
  //打开程序直接跳转到登录页面
  {
    path: '/',
    redirect:'/login',
    meta:{auth:true}//登录不需要验证权限
  },
  {
    path: '/login',
    component: Login,
    meta:{auth:true}
  },
  //部门管理
  {
    path: '/department',
    component: Department
  },
  {
    path: '/adddepartment',
    component: AddDepartment
  },
  //岗位管理
  {
    path: '/post',
    component: Post
  },
  {
    path: '/addpost',
    component: AddPost
  },
  {
    path: '/postDetail',
    component: PostDetail
  },
  //员工管理
  {
    path: '/addStaff',
    component: AddStaff
  },
  {
    path: '/staff',
    component: Staff
  },
  {
    path: '/skill',
    component: Skill
  },
  //试用期管理
  {
    path: '/peroidOp',
    component: PeroidOp
  },
  //岗位调动管理
  {
    path: '/addTransferStaff',
    component: AddTransferStaff
  },
  {
    path: '/transferStaff',
    component: TransferStaff
  },
  //离职管理
  {
    path: '/addQuit',
    component: AddQuit
  },
  {
    path: '/quit',
    component: Quit
  },
  //报表管理
  {
    path: '/newStaffReport',
    component: NewStaffReport
  },
  {
    path: '/quitStaffReport',
    component: QuitStaffReport
  },
  {
    path: '/transferStaffReport',
    component: TransferStaffReport
  },
  {
    path: '/dashBoard',
    component: DashBoard
  },
  {
    path: '/workReport',
    component: WorkReport
  },
  {
    path: '/addWorkReport',
    component: AddWorkReport
  }

]
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
