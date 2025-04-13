// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App'
import router from './router'
import axios from "axios";
import '@fortawesome/fontawesome-free/css/all.css'

Vue.config.productionTip = false

const instance = axios.create({
   baseURL: 'http://127.0.0.1:9527'
});
axios.defaults.withCredentials = true;


Vue.prototype.$http = instance;

Vue.use(ElementUI);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
