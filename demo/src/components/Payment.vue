<template>
  <div class="payment-modal">
    <div class="payment-content">
      <img src="../assets/payment.jpg" alt="支付头部图片" class="payment-header-img">
      <h3>购买数量</h3>

      <div class="quantity-selector">
        <button
          class="quantity-btn"
          @click="decrement"
          :disabled="quantity <= 1"
        >-</button>
        <input
          type="number"
          class="quantity-input"
          v-model.number="quantity"
          min="1"
          readonly
        >
        <button
          class="quantity-btn"
          @click="increment"
        >+</button>
      </div>

      <div class="button-group">
        <button class="confirm-btn" @click="confirm">支付</button>
        <button class="cancel-btn" @click="cancel">取消</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      quantity: 1
    }
  },
  methods: {
    decrement() {
      if (this.quantity > 1) {
        this.quantity--
        console.log("数量减少了")
      }
    },
    increment() {
      this.quantity++
      console.log("数量增加了")
    },
    confirm() {
      this.$emit('confirmed', this.quantity)
    },
    cancel() {
      this.$emit('canceled')
    }
  }
}
</script>

<style scoped>
.payment-modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0,0,0,0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
}

.payment-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 300px;
}

.payment-header-img {
  width: 100%;
  height: 100px;
  object-fit: cover;
  border-radius: 4px;
  margin-bottom: 15px;
}

.quantity-selector {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 20px 0;
}

.quantity-btn {
  width: 40px;
  height: 40px;
  border: 1px solid #e0e0e0;
  background: #f8f8f8;
  font-size: 20px;
  cursor: pointer;
  transition: all 0.3s;
}

.quantity-btn:disabled {
  background: #f8f8f8;
  color: #c0c0c0;
  cursor: not-allowed;
}

.quantity-btn:hover:not(:disabled) {
  background: #409eff;
  color: white;
  border-color: #409eff;
}

.quantity-input {
  width: 60px;
  height: 38px;
  border-top: 1px solid #e0e0e0;
  border-bottom: 1px solid #e0e0e0;
  text-align: center;
  font-size: 16px;
  -moz-appearance: textfield;
}

.quantity-input::-webkit-outer-spin-button,
.quantity-input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

.button-group {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.confirm-btn, .cancel-btn {
  flex: 1;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s;
}

.confirm-btn {
  background: #409eff;
  color: white;
  margin-right: 10px;
}

.confirm-btn:hover {
  background: #66b1ff;
}

.cancel-btn {
  background: #f0f0f0;
  color: #606266;
}

.cancel-btn:hover {
  background: #e0e0e0;
}
</style>
