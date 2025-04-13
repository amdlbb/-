<script setup>

</script>

<template>
  <div id="wrapper">
    <div id="info">

    </div>

    <input type="checkbox" id="menu" name="menu" class="menu-checkbox">
    <div class="menu">
      <label class="menu-toggle" for="menu"><span>Toggle</span></label>
      <ul>
        <li>
          <a :href="'#/HelloWorld'">首页</a>
        </li>
        <li>
          <a href="#/product" class="nav-item">商品列表</a>
        </li>
        <li>
          <a href="#/productView" class="nav-item">浏览商品</a>
        </li>
        <li>
          <label for="menu-3">书籍分类</label>
          <input type="checkbox" id="menu-3" name="menu-3" class="menu-checkbox">
          <div class="menu">
            <label class="menu-toggle" for="menu-3"><span>Toggle</span></label>
            <ul>
              <li>
                <a href="#/category/儿童">儿童</a>
              </li>
              <li>
                <a href="#/category/小说">小说</a>
              </li>
<!--              <li>
                <label for="menu-3-2">小说</label>
                <input type="checkbox" id="menu-3-2" name="menu-3-2" class="menu-checkbox">
                <div class="menu">
                  <label class="menu-toggle" for="menu-3-2"><span>Toggle</span></label>
                  <ul>
                    <li>
                      <a href="#">Menu-3-2-1</a>
                    </li>
                    <li>
                      <a href="#">Menu-3-2-2</a>
                    </li>
                    <li>
                      <label for="menu-3-2-3">Menu-3-2-3</label>
                      <input type="checkbox" id="menu-3-2-3" name="menu-3-2-3" class="menu-checkbox">
                      <div class="menu">
                        <label class="menu-toggle" for="menu-3-2-3"><span>Toggle</span></label>
                        <ul>
                          <li>
                            <a href="#">Menu-3-2-3-1</a>
                          </li>

                        </ul>
                      </div>
                    </li>
                  </ul>
                </div>
              </li>-->
              <li>
                <a href="#/category/文史">文史</a>
              </li>
              <li>
                <a href="#/category/学术">学术</a>
              </li>
              <li>
                <a href="#/category/杂志期刊">杂志期刊</a>
              </li>
            </ul>
          </div>
        </li>
        <li>
          <a href="#/HotSale">热销</a>
        </li>

        <li>
          <a href="#/MyOrder">我的订单</a>
        </li>
      </ul>
    </div>
  </div><!-- #wrapper -->

</template>

<style scoped lang="scss">
* {
  margin: 0;
  padding: 0;
}

#wrapper {
  position: absolute;
  margin-top: 0px;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

label {
  cursor: pointer;
}

label:focus {
  outline: none;
}

.menu {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 1000;
  background: #fff;
  width: 240px;
  height: 100%;
  transform: translate3d(-240px, 0, 0);
  transition: transform 0.35s;
}

label.menu-toggle {
  position: absolute;
  right: -60px;
  width: 60px;
  height: 60px;
  line-height: 0px;
  display: block;
  padding: 0;
  text-indent: -9999px;
  background: #fff url(https://cdn4.iconfinder.com/data/icons/wirecons-free-vector-icons/32/menu-alt-512.png) 50% 50%/25px 25px no-repeat;
}

ul li > label {
  background: url(https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-arrow-right-b-128.png) 95% 50%/16px 16px no-repeat;
}

a,
label {
  display: block;
  text-align: center;
  padding: 0 20px;
  line-height: 75px;
  text-decoration: none;
  color: #000;
}

a:hover,
label:hover {
  color: #666;
}

/* hide inputs */
.menu-checkbox {
  display: none;
}

/* hide navigation icon for sublabels */
.menu .menu label.menu-toggle {
  background: none;
}

/* fade in checked menu */
.menu-checkbox:checked + .menu {
  transform: translate3d(0, 0, 0);
}

/* for show */
html,
body {
  height: 100%;
}

body {
  background: url(https://37.media.tumblr.com/f6c67ec2821a91051e4175f8a102e1e2/tumblr_n6rzpcsMk41st5lhmo1_1280.jpg) 50% 50%/cover;
}

p {
  margin-bottom: 15px;
}

#info {
  display: table;

  height: 100%;
  width: 100%;
}

#info-content {
  display: table-cell;
  vertical-align: middle;
  text-align: center;
  text-transform: uppercase;
  color: #fff;
  font-size: 12px;
}

h1 {
  color: #fff;
  border: 3px solid #fff;
  text-align: center;
  background: rgba(0, 0, 0, 0.1);
  font-size: 22px;
  font-weight: normal;
  padding: 20px;
  margin: 10px;
  display: inline-block;
}

strong {
  display: block;
  font-size: 26px;
}
</style>
