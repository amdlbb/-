<template>
  <div>

    <div class="login-bg"></div>
    <div class="cont" ref="cont">
<!--      <a href="#/HelloWorld" class="nav-item">首页</a>-->
      <div class="form sign-in">
        <h2>Welcome back,</h2>
        <label>
          <span>用户名</span>
          <input type="email" v-model="form.username" />
        </label>
        <label>
          <span>密码</span>
          <input type="password" v-model="form.password" />
        </label>
        <p class="forgot-pass">忘记密码?</p>
        <button type="button" class="submit" @click ="login">登 录</button>
        <button type="button" class="fb-btn">Connect with <span>facebook</span></button>
      </div>
      <div class="sub-cont">
        <div class="img">
          <div class="img__text m--up">
            <h2>New here?</h2>
            <p>Sign up and discover great amount of new opportunities!</p>
          </div>
          <div class="img__text m--in">
            <h2>One of us?</h2>
            <p>If you already has an account, just sign in. We've missed you!</p>
          </div>
          <div class="img__btn" @click="toggleSignup">
            <span class="m--up">Sign Up</span>
            <span class="m--in">Sign In</span>
          </div>
        </div>
        <div class="form sign-up">
          <h2>Time to feel like home,</h2>

          <label>
            <span>用户名</span>
            <input type="email" v-model="registerForm.username" />
          </label>
          <label>
            <span>Email</span>
            <input type="email" v-model="registerForm.email" />
          </label>
          <label>
            <span>密码</span>
            <input type="password" v-model="registerForm.password" />
          </label>
          <button type="button" class="submit" @click="signUp">注 册</button>
          <button type="button" class="fb-btn">Join with <span>facebook</span></button>
        </div>
      </div>
    </div>


  </div>
</template>

<script>
export default {// 修改2：添加组件导出
  data() {
    return {
      isSignUp: false,
      dialogTableVisible: false,
      dialogFormVisible: false,
      // 当前登录用户信息（null表示未登录）
      form: {username: "",password: ""},
      //注册信息
      registerForm: { username: "", password: "", email: "" },
      // 当前登录用户信息（null表示未登录）
      formLabelWidth: '120px',
      user:null
    };
  },
  methods: {
    toggleSignup() {
      // 修改3：使用Vue响应式操作
      this.$refs.cont.classList.toggle('s--signup');
    },
    signUp() {
      this.$http.get("/user/register", {
        params: this.registerForm
      }).then(res => {
        if (res.data.code == 1) {
          this.$message.success("注册成功，自动登录");
          this.user = res.data.data;
          sessionStorage.setItem("sessionId", res.data.data.sessionId);
          this.$router.push('/HelloWorld');
        } else {
          this.$alert("注册失败");
        }
      })
    },
    login() {
      // 使用axios发送GET请求到/login接口
      this.$http.get("/user/login", {
        params: this.form// 将表单数据作为URL参数发送
      }).then(res => {
        if (res.data.code == 1) {
          this.$root.user = res.data.data; // 新增根实例状态更新
          this.$message(res.data.message);// ElementUI的消息提示
          this.dialogFormVisible = false; // 关闭登录对话框
          this.user = res.data.data;// 存储用户数据
          sessionStorage.setItem("sessionId", this.user.sessionId);
          this.$router.push('/HelloWorld').then(() => {
            window.location.reload();
          }); // 新增跳转逻辑
        } else {
          this.$alert(res.data.message);
        }
      })

    }
  },
  created() {
    sessionStorage.getItem("sessionId");
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
        this.$message(res.data.msg);
        this.user = res.data.data;// 更新用户数据
        sessionStorage.setItem("sessionId", this.user.sessionId);
      } else {
        /*this.$alert(res.data.msg);*/
      }
    });
  }
}
</script>

<style style id="login" lang="scss">

.login-bg {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  z-index: 2000; /* 确保在登录界面之下 */

  /* 背景图片路径示例（自行替换） */
  /* background-image: url('/assets/login-bg.jpg'); */
  background-image: url("../assets/BackGround1.jpg");

  background-size: cover;
  background-position: center;
  filter: blur(5px); /* 虚化效果 */
  &::after {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0,0,0,0.3); /* 半透明遮罩增强可读性 */
  }
}

*, *:before, *:after {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

body {
  font-family: 'Open Sans', Helvetica, Arial, sans-serif;
  background: #ededed;
}

input, button {
  border: none;
  outline: none;
  background: none;
  font-family: 'Open Sans', Helvetica, Arial, sans-serif;
}

$contW: 900px;
$imgW: 260px;
$formW: $contW - $imgW;
$switchAT: 1.2s;

$inputW: 260px;
$btnH: 36px;

$diffRatio: ($contW - $imgW) / $contW;

@mixin signUpActive {
  .cont.s--signup & {
    @content;
  }
}

.tip {
  font-size: 20px;
  margin: 40px auto 50px;
  text-align: center;
}

.cont {
  overflow: hidden;
  position: fixed;
  width: $contW;
  height: 550px;
  margin: 0 auto 100px;
  background: #fff;
  left: 20%;
  top: 12.6%;
  z-index: 2000;
}

.form {
  position: relative;
  width: $formW;
  height: 100%;
  transition: transform $switchAT ease-in-out;
  padding: 50px 30px 0;
}

.sub-cont {
  overflow: hidden;
  position: absolute;
  left: $formW;
  top: 0;
  width: $contW;
  height: 100%;
  padding-left: $imgW;
  background: #fff;
  transition: transform $switchAT ease-in-out;

  @include signUpActive {
    transform: translate3d($formW * -1,0,0);
  }
}

button {
  display: block;
  margin: 0 auto;
  width: $inputW;
  height: $btnH;
  border-radius: 30px;
  color: #fff;
  font-size: 15px;
  cursor: pointer;
}

.img {
  overflow: hidden;
  z-index: 2;
  position: absolute;
  left: 0;
  top: 0;
  width: $imgW;
  height: 100%;
  padding-top: 360px;

  &:before {
    content: '';
    position: absolute;
    right: 0;
    top: 0;
    width: $contW;
    height: 100%;
    background-image: url('https://s3-us-west-2.amazonaws.com/s.cdpn.io/142996/sections-3.jpg');
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
    transition: transform $switchAT ease-in-out;
  }

  &:after {
    content: '';
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background: rgba(0,0,0,0.6);
  }

  @include signUpActive {
    &:before {
      transform: translate3d($formW,0,0);
    }
  }

  &__text {
    z-index: 2;
    position: absolute;
    left: 0;
    top: 50px;
    width: 100%;
    padding: 0 20px;
    text-align: center;
    color: #fff;
    transition: transform $switchAT ease-in-out;

    h2 {
      margin-bottom: 10px;
      font-weight: normal;
    }

    p {
      font-size: 14px;
      line-height: 1.5;
    }

    &.m--up {

      @include signUpActive {
        transform: translateX($imgW*2);
      }
    }

    &.m--in {
      transform: translateX($imgW * -2);

      @include signUpActive {
        transform: translateX(0);
      }
    }
  }

  &__btn {
    overflow: hidden;
    z-index: 2;
    position: relative;
    width: 100px;
    height: $btnH;
    margin: 0 auto;
    background: transparent;
    color: #fff;
    text-transform: uppercase;
    font-size: 15px;
    cursor: pointer;

    &:after {
      content: '';
      z-index: 2;
      position: absolute;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      border: 2px solid #fff;
      border-radius: 30px;
    }

    span {
      position: absolute;
      left: 0;
      top: 0;
      display: flex;
      justify-content: center;
      align-items: center;
      width: 100%;
      height: 100%;
      transition: transform $switchAT;

      &.m--in {
        transform: translateY($btnH*-2);

        @include signUpActive {
          transform: translateY(0);
        }
      }

      &.m--up {
        @include signUpActive {
          transform: translateY($btnH*2);
        }
      }
    }
  }
}

h2 {
  width: 100%;
  font-size: 26px;
  text-align: center;
}

label {
  display: block;
  width: $inputW;
  margin: 25px auto 0;
  text-align: center;

  span {
    font-size: 12px;
    color: #cfcfcf;
    text-transform: uppercase;
  }
}

input {
  display: block;
  width: 100%;
  margin-top: 5px;
  padding-bottom: 5px;
  font-size: 16px;
  border-bottom: 1px solid rgba(0,0,0,0.4);
  text-align: center;
}

.forgot-pass {
  margin-top: 15px;
  text-align: center;
  font-size: 12px;
  color: #cfcfcf;
}

.submit {
  margin-top: 40px;
  margin-bottom: 20px;
  background: #d4af7a;
  text-transform: uppercase;
}

.fb-btn {
  border: 2px solid #d3dae9;
  color: darken(#d3dae9, 20%);

  span {
    font-weight: bold;
    color: darken(#768cb6, 20%);
  }
}

.sign-in {
  transition-timing-function: ease-out;

  @include signUpActive {
    transition-timing-function: ease-in-out;
    transition-duration: $switchAT;
    transform: translate3d($formW,0,0);
  }
}

.sign-up {
  transform: translate3d($contW * -1,0,0);

  @include signUpActive {
    transform: translate3d(0,0,0);
  }
}

.icon-link {
  position: absolute;
  left: 5px;
  bottom: 5px;
  width: 32px;

  img {
    width: 100%;
    vertical-align: top;
  }

  &--twitter {
    left: auto;
    right: 5px;
  }
}

.link-footer {
  position: absolute;
  left: 50%;
  bottom: 0;
  transform: translateX(-50%);
}
</style>

