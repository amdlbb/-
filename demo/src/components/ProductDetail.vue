<template>
  <div v-if="isLoaded" class="hello container-main">
    <div class="content-card">

      <div class="product-image-wrapper">
        <img
          :src="product.image"
          :alt="product.name"
          class="product-image"
          :style="{
              '--product-img-width': '600px',
              '--product-img-margin': '0 0 0 40px'
            }"
        >
      </div>
      <!-- 商品主图区域 -->
      <div class="image-container">
        <div class="main-image" :style="{backgroundImage: `url(${product.image})`}"></div>

        <!-- 商品信息区域 -->
        <div class="product-info">
          <h1 class="product-title">{{ product.name }}</h1>

          <div class="meta-section">
            <div class="price-section">
              <span class="current-price">¥{{ product.price }}</span>
              <span class="sales">已售{{ product.sales }}件</span>
              <Fire :sales="product.sales" style="margin-top: 0px"/>
            </div>

          </div>


          <!-- 购买操作区域 -->
          <Payment
            v-if="showPayment"
            @confirmed="handlePaymentConfirmed"
            @canceled="showPayment = false"
          />
          <div class="action-group">
            <button
              class="purchase-button animated-button"
              @click="confirmPurchase"
              :disabled="showPayment"
            >
              购买
            </button>
          </div>
        </div>
      </div> <!-- 补全image-container的闭合标签 -->
    </div> <!-- 补全content-card的闭合标签 -->
  </div>
  <div v-else class="loading-container">
    <div class="loading-spinner"></div>
  </div>
</template>

<script>
/*
用户访问 /product/123 →
mounted() 触发 →
调用 loadProductDetail() →
获取ID=123 →
发送API请求 →
成功：更新商品数据 →
显示页面 失败：跳转404页
*/
import Fire from './Fire.vue';
import Payment from './Payment.vue';

export default {
  name: 'ProductDetail',
  components: {
    Fire,
    Payment
  },
  data() {
    return {
      product: {
        id: '',
        image: '',
        name: '',
        price: 0,
        sales: 0
      },
      isLoaded: false,
      showPayment: false, // 控制支付弹窗显示
      currentUser: null    // 存储当前用户信息
    }
  },
  watch: {
    '$route'(to, from) {
      if (to.params.id !== from.params.id) {
        this.isLoaded = false
        this.loadProductDetail()
      }
    }
  },
  methods: {
    async loadProductDetail() {
      try {
        console.log('params.id:', this.$route.params.id, '类型:', typeof this.$route.params.id)
        // ✅步骤1：从路由参数获取id（如/product/123的id是"123"）
        const id = Number(this.$route.params.id)
        console.log(typeof id, id)
        // ✅防御性判断：如果没有id则跳回首页
        // 在发送请求前增加校验 if (!id || isNaN(id)) {
        if (!id || isNaN(id)) {
          this.$router.push('/')
          return
        }

        // 改用与Product.vue一致的请求方式
        const res = await this.$http.get("/product/get", {
          params: { id } // 保持参数传递方式统一
        })

        // ✅步骤3：处理响应数据（统一数据结构）
        if(res.data.code === 1) {
          this.product = {
            id: res.data.data.id,
            name: res.data.data.name,
            price: res.data.data.price,
            sales: res.data.data.sales || 0,
            image: res.data.data.image || this.getDefaultImage()
          }
          // ✅更新加载状态
          this.isLoaded = true
          // ✅等待DOM更新后强制重绘（解决可能的样式问题）
          this.$nextTick(this.forceReflow)
        } else {
          this.handleError('商品不存在')
        }
      } catch (error) {
        this.handleError(error.message)
      }
    },

    // 复用Product.vue的错误处理模式
    handleError(msg) {
      console.error(msg)
      this.$alert(msg || '请求失败')
      this.$router.push('/404')
    },

    // 统一图片处理逻辑
    getDefaultImage() {
      try {
        return require('../assets/logo.png')
      } catch(error) {
        return 'https://ts4.tc.mm.bing.net/th/id/OIP-C.AZvaum9zwQTgpx_jyepBagHaHa?w=250&h=250&c=8&rs=1&qlt=90&o=6&dpr=1.3&pid=3.1&rm=2'
      }
    },

    // 保持原有方法...
    forceReflow() {
      void this.$el.offsetHeight
    },
    confirmPurchase() {
      console.log("aaaaa")
      if (confirm("您确认要购买吗？")) {
        this.handlePurchase();
      }
    },
    handlePurchase() {
      // 原有的购买逻辑
      this.currentUser = this.$root.user
      console.log(this.currentUser);

      /*if (!this.currentUser) {
        alert('请先登录')
        this.$router.push('/login')
        return
      }*/

      this.showPayment = true // 显示支付弹窗

    },
    async handlePaymentConfirmed(quantity) {

      console.log('收到的数量:', quantity)
      const validQty = Number(quantity)
      this.$http.get("/order/create", {
        params: {

          userId: this.currentUser.id,       // 从App组件获取的用户ID
          productId: this.product.id,        // 当前商品ID
          quantity: validQty
        }
      }).then(res => {
        if(res.data.code === 1) {
          // 更新本地销量显示（与商品详情页数据同步）

          this.product.sales += validQty

          this.$http.get("/product/updateSales", {
            params: {
              product_id: this.product.id, // 保持参数名与后端一致
              number: validQty           // 参数值保持原样
            }
          }).then(updateRes => {
            if(updateRes.data.code === 1) {
              console.log('销量同步成功')
            } else {
              this.$message({
                type: 'warning',
                message: '订单成功但销量未更新',
                duration: 5000
              })
            }
          }).catch(updateErr => {
            console.error('销量更新请求异常:', updateErr)
            this.$message({
              type: 'error',
              message: '销量数据同步失败，请联系管理员',
              duration: 5000
            })
          })

          // 使用ElementUI消息提示（保持与App.vue统一风格）
          this.$message({
            type: 'success',
            message: '下单成功！请在我的订单里查看',
            duration: 3000
          })
          // 关闭支付弹窗（确保UI状态重置）
          this.showPayment = false
        } else {
          // 处理业务逻辑错误（保持与商品加载错误处理一致）
          this.$message.error('下单失败')

          // 开发环境打印详细错误
          /*if(process.env.NODE_ENV === 'development') {
            console.error('API Error:', res.data)
          }*/
        }
      }).catch(error => {
        // 网络错误处理（与loadProductDetail方法保持相同模式）
        console.error('网络请求异常:', error)
        this.$message.error('网络连接异常，请检查网络后重试')

        // 关闭支付弹窗防止界面卡死
        this.showPayment = false

      })
    }
  },
  mounted() {

    this.$nextTick(() => {
      // 修复路由参数丢失时的处理
      if (!this.$route.params.id && this.product.id) {
        this.$router.replace(`/product/${this.product.id}`)
      } else {
        this.loadProductDetail()
      }
    })
  }
}
</script>

<style scoped>
/* 复用HelloWorld的容器样式 */
/*:root {
  --primary-color: #409EFF;
  --secondary-color: #67C23A;
  --text-dark: #303133;
  --bg-light: #f5f7fa;
  --border-radius: 8px;
}*/

.product-image-wrapper {
  order: 2; /* 通过flex order控制显示顺序 */
  flex-shrink: 0;
  margin-left: 420px;
  margin-top: 120px ;
}

.product-image {
  height: 370px;
  object-fit: contain;
  margin: var(--product-img-margin, 100px 0 0 0px); /* 通过CSS变量控制边距 */
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
  background-color: rgba(248, 249, 250, 0.3); /* 加载前占位背景 */
}

.image-container {
  display: flex;
  gap: 40px;
  align-items: center;
  margin-top: -470px;
}
.container-main {
  background: rgba(255, 255, 255, 0.6);
  min-height: 10vh;
  padding: 10px 100px;
  display: flex;
  align-items: center; /* 垂直居中 */
}

.content-card {
  margin-left: 100px;
  width: 1090px;
  height: 652px;
  background: rgba(238, 238, 238, 0.44);
  border-radius: var(--border-radius);
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
  padding: 30px;
}

.image-container {
  display: flex;
  gap: 40px;
  align-items: center; /* 垂直对齐 */
}
/* 主图样式 */
.main-image {
  height: 500px;
  background-size: contain;
  background-repeat: no-repeat;
  flex-shrink: 0; /* 禁止缩小 */
  background-position: center;
  margin-bottom: 40px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

/* 商品信息区域 */
.product-info {
  padding: 0 2rem;
}

.product-title {
  font-size: 2.2em;
  color: #303133;
  margin-bottom: 1.5rem;
}

.meta-section {
  border-bottom: 1px solid rgba(238, 238, 238, 0.38);
  padding-bottom: 1.5rem;
  margin-bottom: 2rem;
}

.price-section {
  display: flex;
  align-items: baseline;
  gap: 1.5rem;
}

.current-price {
  color: #e8a43f;
  font-size: 2.4em;
  font-weight: 700;
}

.sales {
  color: #909399;
  font-size: 1.1em;
}

/* 购买按钮样式 */
.purchase-button {
  background: #e8a43f;
  color: rgba(255, 255, 255, 0.8);
  border: none;
  width: 150px;
  height: 80px;
  z-index: 0;
  padding: 1.2rem 3rem;
  font-size: 1.2em;
  border-radius: 50px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.purchase-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(232,63,67,0.3);
}

/* 复用原有动画效果 */
.animated-button {
  transition: all 0.3s ease;
}
.purchase-button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  pointer-events: none;
  background: #ccc !important;
}
</style>
