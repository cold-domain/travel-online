import Vue from "vue";
import VueRouter from "vue-router";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import { getToken } from "@/utils/storage.js";
import echarts from 'echarts';
Vue.prototype.$echarts = echarts;
Vue.use(ElementUI);
Vue.use(VueRouter);

const routes = [
  {
    path: "*",
    redirect: "/login"
  },
  {
    path: "/login",
    component: () => import(`@/views/login/Login.vue`)
  },
  {
    path: "/register",
    component: () => import(`@/views/register/Register.vue`)
  },
  {
    path: "/publishStrategy",
    name: '发布攻略',
    component: () => import(`@/views/user/PublishStrategy.vue`),
    meta: { requireAuth: true },
  },
  {
    path: "/seeStrategyDetail",
    name: '攻略详情',
    icon: 'el-icon-camera-solid',
    component: () => import(`@/views/admin/StrategyDetail.vue`),
    meta: { requireAuth: true },
  },
  {
    path: "/editStrategy",
    name: '攻略修改',
    component: () => import(`@/views/user/EditStrategy.vue`),
    meta: { requireAuth: true },
  },
  {
    path: "/admin",
    component: () => import(`@/views/admin/Home.vue`),
    meta: {
      requireAuth: true,
    },
    children: [
      {
        path: "/adminLayout",
        name: '首页',
        icon: 'el-icon-pie-chart',
        component: () => import(`@/views/admin/Main.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/userManage",
        name: '用户管理',
        icon: 'el-icon-user',
        component: () => import(`@/views/admin/UserManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/vendorManage",
        name: '供应商',
        icon: 'el-icon-s-shop',
        component: () => import(`@/views/admin/VendorManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/scenicManage",
        name: '景点管理',
        icon: 'el-icon-camera-solid',
        component: () => import(`@/views/admin/ScenicManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/scenicTicketManage",
        name: '门票管理',
        icon: 'el-icon-s-finance',
        component: () => import(`@/views/admin/ScenicTicketManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/scenicTicketOrderManage",
        name: '门票订单',
        icon: 'el-icon-receiving',
        component: () => import(`@/views/admin/ScenicTicketOrderManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/scenicStrategyManage",
        name: '攻略管理',
        icon: 'el-icon-document-copy',
        component: () => import(`@/views/admin/ScenicStrategyManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/hotelManage",
        name: '酒店管理',
        icon: 'el-icon-school',
        component: () => import(`@/views/admin/HotelManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/hotelRoomManage",
        name: '酒店房间管理',
        icon: 'el-icon-box',
        component: () => import(`@/views/admin/HotelRoomManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/hotelOrderInfoManage",
        name: '酒店订单',
        icon: 'el-icon-document-checked',
        component: () => import(`@/views/admin/HotelOrderInfoManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/scenicCategoryManage",
        name: '分类管理',
        icon: 'el-icon-share',
        component: () => import(`@/views/admin/ScenicCategoryManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/evaluationsManage",
        name: '评论管理',
        icon: 'el-icon-chat-dot-round',
        component: () => import(`@/views/admin/EvaluationsManage.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/noticeManage",
        name: '公告管理',
        icon: 'el-icon-house',
        component: () => import(`@/views/admin/NoticeManage.vue`),
        meta: { requireAuth: true },
      },
    ]
  },
  {
    path: "/user",
    component: () => import(`@/views/user/Main.vue`),
    meta: {
      requireAuth: true,
    },
    children: [
      {
        path: "/scenic",
        name: '景点',
        component: () => import(`@/views/user/Scenic.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/scenicDetail",
        name: '景点详情',
        component: () => import(`@/views/user/ScenicDetail.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/order",
        name: '我的订单',
        component: () => import(`@/views/user/Order.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/strategy",
        name: '攻略',
        component: () => import(`@/views/user/Strategy.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/strategyDetail",
        name: '攻略详情',
        component: () => import(`@/views/user/StrategyDetail.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/hotel",
        name: '酒店',
        component: () => import(`@/views/user/Hotel.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/hotelDetail",
        name: '酒店详情',
        component: () => import(`@/views/user/HotelDetail.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/hotelOrder",
        name: '酒店订单',
        component: () => import(`@/views/user/HotelOrder.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/ticketOrder",
        name: '门票订单',
        component: () => import(`@/views/user/TicketOrder.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/notice",
        name: '公告通知',
        component: () => import(`@/views/user/Notice.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/noticeDetail",
        name: '公告详情',
        component: () => import(`@/views/user/NoticeDetail.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/service",
        name: '服务中心',
        component: () => import(`@/views/user/Service.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/scenicVendor",
        name: '供应商景点',
        component: () => import(`@/views/user/ScenicVendor.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/hotelVendor",
        name: '供应商酒店',
        component: () => import(`@/views/user/HotelVendor.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/ticket",
        name: '景点门票',
        component: () => import(`@/views/user/Ticket.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/hotelRoom",
        name: '酒店房间',
        component: () => import(`@/views/user/HotelRoom.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/self",
        name: '个人中心',
        component: () => import(`@/views/user/Self.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/resetPwd",
        name: '重置密码',
        component: () => import(`@/views/user/ResetPwd.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/contentCenter",
        name: '内容中心',
        component: () => import(`@/views/user/ContentCenter.vue`),
        meta: { requireAuth: true },
      },
      {
        path: "/save",
        name: '我的收藏',
        component: () => import(`@/views/user/Save.vue`),
        meta: { requireAuth: true },
      },
    ]
  }
];
const router = new VueRouter({
  routes,
  mode: 'history'
});
router.beforeEach((to, from, next) => {
const role = sessionStorage.getItem('role');
  const token = getToken();
  const isLoginPage = to.path === '/login';
  // 防止无限重定向
  if (isLoginPage) {
    return next();
  }
  // 未登录情况
  if (!role) {
    return next('/login');
  }
  // 需要认证的路由
  if (to.meta.requireAuth && !token) {
    return next('/login');
  }
  // 管理员路由校验
  if (to.matched.some(record => record.path === '/admin') && role !== '1') {
    return next('/login');
  }
  // 默认放行
  next();
});
export default router;
