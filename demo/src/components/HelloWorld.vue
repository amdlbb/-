<template>
  <div class="hello container-main">
    <div class="content-card">
<!--      <h1 class="gradient-title">{{ msg }}</h1>

      <el-divider content-position="left"><i class="el-icon-link"></i> Essential Links</el-divider>

      <div class="action-group">
        <el-button
          type="primary"
          icon="el-icon-user"
          @click="reg"
          class="animated-button">
          测试注册
        </el-button>
      </div>

      <el-divider><i class="el-icon-star-off"></i> 评分组件演示</el-divider>



        <div class="demo-item">
          <h3 class="demo-title">选择评分</h3>
          <div class="star-group">
            <star v-model="star1" class="star-item"></star>
            <span class="rating-text">您选择了 {{star1}} 星</span>
          </div>
          <div class="star-group">
            <star v-model="star2" :total="10" class="star-item"></star>
            <span class="rating-text">您选择了 {{star2}} 星</span>
          </div>
        </div>-->
      <Table style="width: max-content"/>
      </div>
    </div>
</template>

<script>
import star from './Stars.vue';
import Table from "./Table.vue";
export default {
  name: 'HelloWorld',
  components: {
    star,
    Table
  },
  data () {
    return {
      msg: '你好 南华',
      star1 : 4,
      star2 : 7,
    }
  },
  methods: {
    reg() {
      this.$http.get("/user/reg").then(res=>{
        this.$notify({
          title: '操作结果',
          message: res.data.msg,
          type: 'success'
        });
        window.location.reload(true);
      })
    }
  }, mounted() {
    if (this.$route.query.forceRefresh) {
      window.location.reload();
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

.container-main {
  max-height: 800px; /* 新增最大高度限制 */
  overflow-y: hidden;  /* 启用垂直滚动条 */
  background: var(--bg-light);
  min-height: 10vh;
  padding: 10px 100px;
}

.content-card {
  width: 1090px;
  margin: 0 auto;
  background: white;
  border-radius: var(--border-radius);
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
  padding: 30px;
}

/*.gradient-title {
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  font-size: 3.5em;
  text-align: center;
  margin: 20px 0;
  text-shadow: 2px 2px 4px rgba(0,0,0,0.1);
}

.demo-section {
  display: grid;
  gap: 40px;
  padding: 20px 0;
}

.demo-item {
  background: var(--bg-light);
  border-radius: var(--border-radius);
  padding: 25px;
  transition: transform 0.3s ease;
}

.demo-item:hover {
  transform: translateY(-5px);
}

.demo-title {
  color: var(--text-dark);
  font-size: 1.4em;
  margin-bottom: 15px;
  border-left: 4px solid var(--primary-color);
  padding-left: 12px;
}

.star-group {
  display: flex;
  align-items: center;
  gap: 20px;
  margin: 15px 0;
}

.rating-text {
  color: var(--text-dark);
  font-weight: 500;
}

.animated-button {
  transition: all 0.3s ease;
}

.animated-button:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 12px rgba(64,158,255,0.3);
}

.star-item {
  transform: scale(1.2);
  margin: 10px 0;
}

.action-group {
  text-align: center;
  margin: 30px 0;
}

.el-divider {
  background-color: rgba(64,158,255,0.1);
}

.el-divider__text {
  color: var(--primary-color);
  font-size: 1.1em;
}*/
</style>
