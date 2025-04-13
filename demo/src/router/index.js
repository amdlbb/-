import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Product from '@/components/Product'
import ProductView from "../components/ProductView.vue";
import Stars from "../components/Stars.vue";
import Login from "../components/Login.vue";
import menu from  "../components/menu.vue"
import Payment from "../components/Payment.vue";
import MyOrder from "../components/MyOrder.vue";
import HotSale from "../components/HotSale.vue";
import PersonalCenter from "../components/PersonalCenter.vue";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/HelloWorld',

      name: 'HelloWorld',
      component: HelloWorld,

    },
    {
      path: '/Product',
      component: Product,

    },
    {
      path: '/ProductView',
      component: ProductView,

    },
    {
      path: '/Stars',
      component: Stars,

    },
    {
      path: '/menu',
      name: 'menu',
      component: menu,

    },
    {
      path: '/',
      name: 'Login',
      component: Login,

    },
    {
      path: '/product/:id', // 动态路径参数
      name: 'ProductDetail',
      component: () => import('../components/ProductDetail.vue')
    },
    {
      path: '/Payment',
      name: 'Payment',
      component: Payment,
    },
    {
      path: '/MyOrder',
      name: 'MyOrder',
      component: MyOrder,
    },
    {
      path: '/HotSale',
      name: 'HotSale',
      component: HotSale,
    },
    {
      path: '/category/:category', // 动态分类参数
      name: 'CategorySearch',
      component: () => import('../components/CategroySearch.vue')
    },
    {
      path: '/PersonalCenter',
      name: 'PersonalCenter',
      component: PersonalCenter,
    },
    {
      path: '*',
      redirect: '/' // 未匹配路由跳转登录
    },

  ]
})
