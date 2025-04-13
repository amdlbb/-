<script>
export default {
  name: "Product",
  data() {
    return {
      tableData: [],
      pageobj: {page: 1, size: 10, total: 50},
      where: { name:"", price1:0, price2:0},
      form: {id:null ,name:null,price:null,image:null},
      dialogFormVisible: false,
    }
  }, methods:{
    query() {
      this.$http.get("/product/news", {
        params: {
          page: this.pageobj.page, size: this.pageobj.size
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
      if (this.where.name) {
        this.queryByName(); // 有名称条件时用名称查询
      } else {
        this.query(); // 无名称条件用基础查询
      }
    },
    queryByPrice() {
      this.$http.get("/product/news", {
        params: {
          page: this.pageobj.page,
          size: this.pageobj.size,
          price1: this.where.price1, // 传递价格下限
          price2: this.where.price2  // 传递价格上限
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
    queryByName() {
      this.$http.get("/product/news", {
        params: {
          page: 1, // 搜索时重置到第一页
          size: this.pageobj.size,
          name: this.where.name
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
    add(){
       this.dialogFormVisible = true;
       this.form= {id:null ,name:null,price:null,image:null};
    },
    mod(row){
      this.form= {id:row.id ,name:row.name,price:row.price,image:row.image};
      this.dialogFormVisible = true;
    },
    del(row){
      if(confirm('确认删除？')==false){
        return;
      }
      this.$http.get("/product/del?id=" + row.id).then(res=>{
        if(res.data.code !== 1) {
          this.$alert(res.data.msg);
        }else {
          this.$message(res.data.msg);
          this.tableData=this.tableData.filter(item=>item.id !== row.id);
        }
      })
    },
    save(){
      this.$http.get("/product/save",{
        params:this.form
      }).then(res => {
        if(res.data.code !== 1) {
          this.$alert(res.data.msg);
        }else {
          this.$message(res.data.msg);
          if (this.form.id === null) {
            //新增
            this.tableData.push({
              id:res.data.id,
              name:res.data.name,
              price:res.data.price,
              image:res.data.image
            })
          } else {
            //修改
            let p = this.tableData.find(item=>item.id === this.form.id);
            p.id = this.form.id;
            p.price = this.form.price;
            p.image = this.form.image;
            p.name = this.form.name;
          }
        this.dialogFormVisible = false;
        }
      })
    },
    uploaded(res, file, fileLis) {
      this.form.image = res.data;
    }
  },created() {
    this.query();
  }
}
</script>

<template>
  <div class="app">
    <div class="el-table">
      <el-form :inline="true" :model="where" class="demo-form-inline">
        <el-form-item label="价格下限">
          <el-input v-model="where.price1" type="number"></el-input>
        </el-form-item>
        <el-form-item label="至">
          <el-input v-model="where.price2" type="number"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="queryByPrice">查询</el-button>
          <el-button type="primary" @click="add">新增</el-button>
        </el-form-item>

        <el-form-item label="商品搜索" style="margin-left: 100px">
          <el-input v-model="where.name" placeholder="输入名称" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="queryByName">搜索</el-button>
        </el-form-item>

      </el-form>
      <el-table :data="tableData" style="width: 100%" :border="true">
        <el-table-column prop="id" label="编号"></el-table-column>
        <el-table-column prop="name" label="名称"></el-table-column>
        <el-table-column prop="price" label="价格"></el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button @click="del(scope.row)" type="text" size="small">删除</el-button>
            <el-button @click="mod(scope.row)" type="text" size="small">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog title="商品信息" :visible.sync="dialogFormVisible">
        <el-form :model="form">
          <el-form-item label="编号" label-width="100">
            <el-input v-model="form.id" autocomplete="off" disabled></el-input>
          </el-form-item>

          <el-form-item label=" 商品搜索 " label-width="200">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="价格" label-width="100">
            <el-input v-model="form.price" autocomplete="off" type="number"></el-input>
          </el-form-item>

          <el-form-item label="图片" label-width="100">
            <el-input v-model="form.image" autocomplete="off">
              <template slot="append">
                <el-upload
                  action="http://127.0.0.1:9527/upload" :on-success="uploaded" :show-file-list="false">
                <el-button size="mini">上传</el-button>
                </el-upload>
              </template>
            </el-input>
          </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <div style="text-align: right">
      <el-pagination :current-page="pageobj.page" page-size="pageobj.size" @current-change="queryPage"
                     background layout="prev, pager, next" :total=pageobj.total>
      </el-pagination>
    </div>
  </div>
</template>

<style scoped>
::v-deep .el-table__row {
  height: 50px;
  line-height: 50px;
}

#app {
  position: relative;
  z-index: 100;
}

div:first-child {
  position: relative;
  z-index: 0; /* 设置为最低基础层级 */
}

.el-table {
  position: relative;
  z-index: 0; /* 表格内容保持正常显示 */
}

.el-dialog__wrapper {
  z-index: 0;
}
</style>
