<template>
  <div class="hello container-main">
    <div class="content-card">
      <div class="personal-center">
        <!-- 头像区域 -->
        <div class="avatar-section">
          <img :src="userInfo.head" class="avatar"/>

          <el-upload
            action="http://127.0.0.1:9527/upload"
            :on-success="handleAvatarSuccess"
            :show-file-list="false"
            :before-upload="beforeAvatarUpload">
            <el-button type="warning" plain style="width: 100px">更换头像</el-button>
          </el-upload>
        </div>

        <!-- 信息展示 -->
        <div class="info-display">
          <div class="info-item">
            <span class="info-label">用户名：</span>
            <span class="info-value">{{ userInfo.username }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">邮箱：</span>
            <span class="info-value">{{ userInfo.email }}</span>
          </div>
          <el-button type="danger" @click="dialogVisible = true" class="edit-btn">编辑资料</el-button>
        </div>

        <!-- 编辑对话框 -->
        <el-dialog title="编辑资料" :visible.sync="dialogVisible" width="500px">
          <el-form :model="formData">
            <el-form-item label="用户名" label-width="80px">
              <el-input v-model="formData.username" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="密码" label-width="80px">
              <el-input type="password" v-model="formData.password" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="邮箱" label-width="80px">
              <el-input v-model="formData.email"/>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="updateUser">保 存</el-button>
          </div>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {

      dialogVisible: false,

      userInfo: { // 存储原始用户数据
        id: null,
        username: '',
        password: '',
        head: '',
        email: ''
      },
      formData: { // 表单绑定数据
        username: '',
        password: '',
        email: '',
        head: '',
      }
    }
  },
  mounted() {
    this.getLogined();
  },
  methods: {
     getLogined() {
      try {
        console.log("尝试获取数据");
        if (!this.$root.user) {
          this.$alert('请先登录').then(() => {
            this.$router.push('/login');
          });
          return;
        }

        // 直接从根实例获取数据
        this.userInfo = {
          id: this.$root.user.id,
          username: this.$root.user.username,
          password: this.$root.user.password,
          head: this.$root.user.head,
          email: this.$root.user.email
        };

        // 初始化表单数据
        this.formData = {
          username: this.$root.user.username,
          password: this.$root.user.password, // 密码字段保持空值
          email: this.$root.user.email,
          head: this.$root.user.head
        };


      } catch (e) {
        console.error('获取用户信息失败', e);
      }
    },

    async updateUser() {
      // 构造请求参数
      const params = {
        username: this.formData.username || this.userInfo.username,
        password: this.formData.password || this.userInfo.password,
        email: this.formData.email || this.userInfo.email
      };

      try {
        const res = await this.$http.get('user/update', { params });
        if (res.data.code === 1) {
          Object.assign(this.$root.user, {
            username: this.formData.username,
            email: this.formData.email,
            head: this.userInfo.head
          });

          // 同步本地数据
          this.userInfo = { ...this.$root.user };
          this.dialogVisible = false;
          this.$message.success('修改成功');
          await this.getLogined(); // 刷新数据
        }



      } catch (e) {
        console.error('更新失败', e);
      }
    },
    beforeAvatarUpload(file) {
      const isImage = file.type.startsWith('image/');
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isImage) {
        this.$message.error('只能上传图片文件');
      }
      if (!isLt2M) {
        this.$message.error('头像图片大小不能超过2MB');
      }
      return isImage && isLt2M;
    },
    handleAvatarSuccess(res, file) {
       console.log(res.data)

      // 1. 更新本地数据
      this.userInfo.head = res.data;
      this.formData.head = res.data;

      // 2. 同步到根实例

      this.$root.user.head = res.data;


      // 3. 调用更新接口保存到数据库
      this.$http.get('user/update', {
        params: {
          head: res.data,
          // 保留其他字段原始值
          username: this.userInfo.username,
          email: this.userInfo.email,
          password: this.userInfo.password,
        }
      }).then(res => {
        console.log("更新头像")
        if (res.data.code === 1) {
          this.$message.success('头像更新成功');
          // 4. 强制刷新头像缓存
          this.userInfo.head = `${res.data}?t=${Date.now()}`;
        }
      });
    }
  }
}
</script>

<style scoped>


:root {
  --primary-color: #409EFF;
  --secondary-color: #67C23A;
  --text-dark: #303133;
  --bg-light: #f5f7fa;
  --border-radius: 8px;
}

.avatar-upload-btn:hover {
  transform: scale(1.1);
  transition: transform 0.3s ease;
  box-shadow: 0 2px 12px 0 rgba(255, 255, 255, 0.09);
}

/* 点击效果 */
.avatar-upload-btn:active {
  transform: scale(0.95);
}

/* 确保邮箱输入框可见 */
::v-deep .el-input__inner {
  color: #606266 !important;
}

/* 调试辅助样式 */
.info-value {
  border: 1px solid #eee; /* 临时添加边框确认元素存在 */
  padding: 4px;
}
.container-main {
  background: var(--bg-light);
  min-height: 10vh;
  padding: 10px 100px;
}

.content-card {
  width: 1090px;
  height: 730px;
  margin: 0 auto;
  background: white;
  border-radius: var(--border-radius);
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
  padding: 30px;
}

.personal-center {
  max-width: 600px;
  margin: 20px auto;
}
.avatar-section {
  text-align: center;
  margin-bottom: 30px;
}
.avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
}
/*.info-form {
  padding: 20px;
  border: 1px solid #eee;
}
.form-item {
  margin: 15px 0;
}*/
.form-item label {
  display: inline-block;
  width: 80px;
}
.info-display {
  padding: 20px;
  border: 2px solid #fde2e2;
  border-radius: 8px;
  background: #fef6f6;
}

.info-item {
  margin: 20px 0;
  padding: 12px;
  background: #fff5f5;
  border-radius: 6px;
}

.info-label {
  color: #f56c6c;
  font-weight: bold;
  margin-right: 15px;
}

.info-value {
  color: #606266;
}

.edit-btn {
  margin-top: 30px;
  width: 200px;
}

.el-dialog__header {
  background: #fef6f6;
  border-bottom: 2px solid #fde2e2;
}

.el-dialog__title {
  color: #f56c6c;
}

.el-button--primary {
  background-color: #f56c6c;
  border-color: #f56c6c;
}

.el-button--primary:hover {
  background-color: #f78989;
  border-color: #f78989;
}

.el-button--warning {
  color: #f56c6c;
  border-color: #fbc4c4;
}
</style>
