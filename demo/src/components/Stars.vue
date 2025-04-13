<script>
export default {
  name: 'Star',
  props: {
    value: { type: Number, default: 3 },
    total: { type: Number, default: 5 },
  },
  methods:{
    clk(i) {
      this.value = i;
      this.$emit('input', this.value);
    }
  }
}

</script>

<template>
  <span>
    <i v-for="i in total" @click="clk(i)" :class="{
      'el-icon-star-on': i <=value,
      'el-icon-star-off': i > value,
    }"></i>
  </span>

</template>

<style scoped>
i{
  cursor: pointer;
}
</style>
