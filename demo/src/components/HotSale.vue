<template>
  <div class="app">
    <div class="content-card">
    <div class="hot-sale-container">
      <el-row :gutter="20">
        <el-col
          v-for="item in tableData"
          :key="item.id"
          :xs="12"
          :sm="8"
          :md="6"
          :lg="4"
          class="product-card"
        >
          <div class="card-wrapper">
            <div class="image-container">
              <el-image
                :src="item.image"
                fit="cover"
                class="product-image"
                @click="goDetail(item.id)"
              >
                <div slot="error" class="image-slot">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </el-image>
            </div>
            <div class="product-info">
              <div class="title">{{ item.name }}</div>
              <div class="price-sales-row">
                <span class="price">¥{{ item.price }}</span>
                <span class="sales">销量: {{ item.sales }}</span>
              </div>
              <el-button
                type="warning"
                class="detail-btn"
                @click="goDetail(item.id)"
              >
                立即购买
              </el-button>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div style="text-align: center; margin-top: 420px; margin-left: 900px">
      <el-pagination
        :current-page="pageobj.page"
        :page-size="pageobj.size"
        @current-change="queryPage"
        background
        layout="prev, pager, next"
        :total="pageobj.total"
      >
      </el-pagination>
    </div>
  </div>
  </div>
</template>

<script>
export default {
  name: "HotSale",
  data() {
    return {
      tableData: [],
      pageobj: { page: 1, size: 12, total: 0 }, // 调整每页显示数量为12
    };
  },
  methods: {
    query() {
      this.$http.get("/product/news", {
        params: {
          hot: 1, // 添加热销参数
          page: this.pageobj.page,
          size: this.pageobj.size
        }
      }).then(res => {
        this.pageobj = {
          page: Number(res.data.page),
          size: Number(res.data.size),
          total: Number(res.data.total)
        };
        this.tableData = res.data.data;
      });
    },
    queryPage(page) {
      this.pageobj.page = page;
      this.query();
    },
    goDetail(id) {
      this.$router.push('/product/' + id);
    }
  },
  created() {
    this.query();
  }
};
</script>

<style scoped>
.content-card {
  top: 10px;
  margin: 0px auto;  /* 居中显示 */
  width: 1200px;      /* 比商品详情稍宽 */
  height: 720px;  /* 自动高度 */
  background: rgb(255, 255, 255);   /* 纯白背景 */
  border-radius: 12px;
  box-shadow: 0 2px 20px rgba(0,0,0,0.08); /* 更强的阴影 */
  padding: 30px;
  position: relative; /* 为分页定位准备 */
}

.price-sales-row {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
  margin: 0px 0;
}

.sales {
  color: #666;
  font-size: 12px;
  margin: 5px 0;
}

.hot-sale-container {
  padding: 0px;

}

.product-card {
  margin-bottom: 20px;
}

.card-wrapper {
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  transition: transform 0.3s;
  margin-left: 0px;
  margin-top: -10px;
}

.card-wrapper:hover {
  transform: translateY(-5px);
}

.image-container {
  position: relative;
  padding-top: 50%; /* 1:1比例 */
  background: #f5f5f5;
}

.product-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  cursor: pointer;
}

.product-info {
  padding: 10px;
  text-align: center;
}

.title {
  height: 20px;
  font-size: 14px;
  line-height: 1.4;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.price {
  color: #ff5000;
  font-size: 12px;
  font-weight: bold;
  margin: 2px 0;
}

.detail-btn {
  width: 90%;
  height: 200%;
  border-radius: 20px;
  margin-top: 0px; /* 增加顶部间距 */
  padding: 0px; /* 增加内边距 */
}
</style>
