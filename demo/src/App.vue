<template>
  <div id="app">

    <header class="c1000 header-container">
      <div class="logo-container">
        <img src="./assets/logo3.png" class="logo">
        <!--        <nav class="nav-links">-->
        <!--          &lt;!&ndash; 首页路由，对应router配置的path: '/' &ndash;&gt;-->
        <!--&lt;!&ndash;          <a href="#/HelloWorld" class="nav-item">首页</a>&ndash;&gt;-->
        <!--&lt;!&ndash;          &lt;!&ndash; 商品列表路由，对应path: '/product' &ndash;&gt;&ndash;&gt;-->
        <!--&lt;!&ndash;          <a href="#/product" class="nav-item">商品列表</a>&ndash;&gt;-->
        <!--&lt;!&ndash;          &lt;!&ndash; 商品浏览路由，对应path: '/productView' &ndash;&gt;&ndash;&gt;-->
        <!--&lt;!&ndash;          <a href="#/productView" class="nav-item">浏览商品</a>&ndash;&gt;-->
        <!--&lt;!&ndash;          <a href="#/menu" class="nav-item">菜单</a>&ndash;&gt;-->
        <!--        </nav>-->
      </div>
      <div class="auth-section">
        <template v-if="user===null">
        </template>
        <template v-else>
          <div class="user-info">
            <el-avatar :size="40" :src="user.head || 'default-avatar.png'" class="user-avatar"></el-avatar>
            <el-dropdown>
          <span class="el-dropdown-link username">
            欢迎，{{user.username}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="$router.push({ name: 'PersonalCenter' })">个人中心</el-dropdown-item>
                <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </template>
      </div>
    </header>
    <Menu />
    <main class="c1000">
      <router-view/>
    </main>

  </div>
</template>

<script>
import Menu from '@/components/menu';

export default {
  name: 'App',
  components: {
    Menu
  },
  data() {
    return {
      isSignUp: false,
      dialogTableVisible: false,
      dialogFormVisible: false,
      // 当前登录用户信息（null表示未登录）
      form: {username: "",password: ""},
      // 当前登录用户信息（null表示未登录）
      formLabelWidth: '120px',
      user:null,
    };
  },
  methods: {

//登录功能（login函数）
// 当用户点击登录按钮时，会触发这个login函数 用GET请求发送用户名密码到服务器
// （实际开发中更常用POST请求，这里可能有安全隐患）
// 登录成功时（code=1）：
// 显示成功提示 关闭登录弹窗 把用户数据存储在组件的user变量中
// 把sessionId存入sessionStorage（类似浏览器临时保险箱） 登录失败则弹出错误提示

    login() {
      // 使用axios发送GET请求到/login接口
      this.$http.get("/user/login", {
        params: this.form// 将表单数据作为URL参数发送
      }).then(res => {
        if (res.data.code == 1) {
          this.$message(res.data.message);// ElementUI的消息提示
          this.dialogFormVisible = false; // 关闭登录对话框
          this.user = res.data.data;// 存储用户数据
          sessionStorage.setItem("sessionId", this.user.sessionId);
        } else {
          this.$alert(res.data.message);
        }
      })

    },
    logout() {
      this.$http.get("/user/Loginout").then(res => {
        this.user = null;
        this.$message(res.data.msg);// 显示退出提示
        sessionStorage.removeItem("sessionId"); // 清除session标识
        sessionStorage.removeItem("visitCount"); // 清除自动跳转标识
        this.$router.push("/").then(() => {
          window.location.reload(); // 关键解决点：打破Vue实例的保持状态
        });
      })
    },
    goToLogin() {
      this.$router.push('/');
    },
    toggleSignup() {
      this.isSignUp = !this.isSignUp;
      console.log('状态更新:', this.isSignUp);

      this.$nextTick(() => {
        const contEl = this.$el.querySelector('.cont');
        if (!contEl) return;



        // 同步类名状态
        contEl.classList.toggle('s--signup', this.isSignUp);


        // 调试样式
        contEl.style.outline = '2px solid red';


      });
    },
  },

// 自动登录检查（created函数）
// 这个函数会在页面加载时自动执行
// 从sessionStorage取出之前存的sessionId
// 带着这个sessionId问服务器"当前谁登录了"
// 如果sessionId有效就能自动登录
// 无效则提示错误
  beforeCreate() {
    this.$root.user = null; // 初始化根实例用户状态
  },
  created() {

    const visitCount = parseInt(sessionStorage.getItem('visitCount') || 0)

    if(visitCount > 0) {
      this.$router.push('/HelloWorld')
    }

    sessionStorage.setItem('visitCount', visitCount + 1)
    this.$router.push('/HelloWorld')
    console.log(this.numb)
    sessionStorage.getItem("sessionId")
    let sessionId = sessionStorage.getItem("sessionId");
    let url = "/user/getLoginedUser";
    // 如果存在sessionId，使用URL重写方式传递（常规应使用Cookie）
    if (sessionId) {
      // URL 重写
      url += ";jsessionid=" + sessionId; // Java Session ID拼接方式

      // 获取当前登录用户信息
    }
    this.$http.get(url).then(res => {
      if (res.data.code === 1) {
        this.$root.user = res.data.data
        this.$message(res.data.msg);
        this.user = res.data.data;// 更新用户数据
        sessionStorage.setItem("sessionId", this.user.sessionId);
      } else {
        this.$alert(res.data.msg);
        this.$router.push('/')
      }
    });
  }
}

</script>


<style scoped>


:root {
  --primary-color: #409EFF;
  --secondary-color: #67C23A;
  --text-color: #303133;
  --border-radius: 4px;
}

.header-container {
  z-index: 100;
  position: relative;
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: white;
  box-shadow: 0 0px 0px rgba(0,0,0,0.1);
  padding: -50px -50px;
}

.logo-container {
  display: flex;
  align-items: center;
  gap: 60px;
}

.logo {
  height: 40px !important;
  transition: transform 0.3s ease !important;
}

.logo:hover {
  transform: scale(1.05);
}

.nav-links {
  display: flex;
  gap: 30px;
}

.nav-item {
  color: var(--text-color);
  text-decoration: none;
  font-weight: 500;
  padding: 8px 12px;
  border-radius: var(--border-radius);
  transition: all 0.3s;
}

.nav-item:hover {
  background: rgba(64,158,255,0.1);
  color: var(--primary-color);
}

.auth-section {
  display: flex;
  align-items: center;
  gap: 15px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.user-avatar {
  border: 2px solid var(--primary-color);
  cursor: pointer;
}

.username {
  color: var(--text-color) !important;
  font-weight: 500 !important;
  cursor: pointer !important;
}

.el-dropdown-link:hover {
  color: var(--primary-color) !important;
}

main.c1000 {
  min-height: calc(100vh - 120px);
  padding: 0px 0;
  background: #f5f7fa;
}

footer {
  background: var(--primary-color) !important;
  color: white !important;
  padding: 20px 0 !important;
  text-align: center !important;
  font-size: 14px !important;
}


</style>

