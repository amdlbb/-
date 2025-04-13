<script>
export default {//export default全局可用
  name: "ProductView",
  data() {
    return {
      //整个页面的数据，只有当前组件自己能用，this.访问
      tableData: [],
      pageobj: {page: 1, size: 10, total: 50},//这是个类
      where: {name: "", price1: 0, price2: 0}
    }
  }, methods: {
    //在create中启动自动执行，请求后端数据
    query() {
      this.$http.get("/product/news", {
        params: {
          page: this.pageobj.page, size: this.pageobj.size
          //请求参数，示例请求URL：/product/news?page=1&size=10
        }
      }).then(res => {
        this.pageobj = res.data;//data是axios的响应数据

        this.tableData = this.tableData.concat(res.data.data);
      });
    },
    load() {
      let totalPage = parseInt(this.pageobj.total / this.pageobj.size);
      if (this.pageobj.total % this.pageobj.size != 0) {
        totalPage++;
      }
      if (this.pageobj.page >= totalPage) {
        this.$alert("没有数据了");
        return;
      }
      this.pageobj++;
      this.query();
    }
  }, created() {
    this.query();
  }
}
</script>

<template>
  <div style="height:600px; position: relative; z-index: 1;
              overflow: auto; overflow-x: hidden;"
       v-infinite-scroll="load"
       infinite-scroll-distance="200"
       infinite-scroll-delay="300">
    <el-card v-for="p in tableData">
      <!-- 商品展示内容 -->
      <div style="display: grid;grid-template-columns: auto 1fr;">
        <img :src="p.image" alt="" style="height: 200px;aspect-ratio: 1/1;object-fit: cover">
        <div style="display: grid; grid-template-columns: auto;">
          <span>{{ p.name }}</span>
          <span>{{ p.price }}</span>
        </div>
      </div>
    </el-card>
  </div>
</template>

<style scoped>

</style>
