// Fire.vue
<template>
  <img
    v-if="shouldShowHot"
    src="../assets/hot.jpg"
    alt="热销图标"
    class="hot-icon"
  >
</template>

<script>
export default {
  name: 'Fire',
  //父传子
  props: {
    sales: {
      type: Number,
      required: true
    }
  },
  computed: {
    shouldShowHot() {
      return this.sales > 500
    }
  }
}
</script>

<style scoped>
.hot-icon {
  width: 32px;
  height: 32px;
  vertical-align: middle;
  margin-left: 8px;
}
</style>
