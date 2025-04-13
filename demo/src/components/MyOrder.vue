<template>
  <div class="my-order-container">
    <!-- 订单列表 -->
    <el-table
      :data="orders"
      style="width: 100%"
      height="calc(100vh - 40px)"

    >
      <!-- 商品图片列 -->
      <el-table-column label="商品图片" width="120">
        <template slot-scope="scope">
          <img
            :src="scope.row.image"
            class="product-image"
            alt="商品图片"
          >
        </template>
      </el-table-column>

      <!-- 商品名称 -->
      <el-table-column prop="name" label="商品名称"></el-table-column>

      <!-- 商品分类 -->
      <el-table-column prop="category" label="分类" width="100"></el-table-column>

      <!-- 购买数量 -->
      <el-table-column prop="number" label="数量" width="100"></el-table-column>

      <!-- 取消订单操作 -->
      <el-table-column
        fixed="right"
        width="150"
      >
        <template slot="header">
          <span style="margin-left: 30px">操作</span>
        </template>
        <template slot-scope="scope">
          <el-button
            type="danger"
            @click="handleCancel(scope.row)"
            size="medium"
            style="width: 95px"
          >
            取消订单
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: "MyOrder",
  data() {
    return {
      orders: [] // 订单数据示例结构：
      // [
      //   {
      //     id: '1',
      //     name: '商品A',
      //     image: 'path/to/image.jpg',
      //     category: '电子产品',
      //     number: 2
      //   }
      // ]
    }
  },
  methods: {
    // 初始化加载订单
    fetchOrders() {
      // 从根实例获取当前用户ID（与ProductDetail.vue保持相同方式）
      const userId = this.$root.user.id

      // 添加防御性判断
      if (!userId) {
        console.error('未获取到用户ID')
        return
      }

      this.$http.get("/order/list", {
        params: {
          user_id: userId // 参数名需与后端接口一致
        }
      }).then(res => {
          this.orders = res.data.data;
        })
    },

    // 取消订单处理
    handleCancel(order) {
      if(confirm('确定要取消该订单吗？')) {
        this.$http.get("/order/cancel", {
          params: { // GET请求参数配置
            id: order.id
          }
        })
          .then(res => {
            if(res.data.code === 1) {
              this.orders = this.orders.filter(item => item.id !== order.id)
            }
          })
      }
    }
  },
  created() {
    this.fetchOrders()
  }
}
</script>

<style scoped>
.my-order-container {
  padding: 20px;
  height: 735px;
  overflow: hidden; /* 外层容器隐藏溢出 */
}

.product-image {
  width: 80px;
  height: 80px;
  object-fit: contain;
}

/* 内置滚动条样式 */
.el-table {
  overflow-y: auto;
  border: 1px solid #EBEEF5;
}
</style>
