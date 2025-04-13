<template>
  <div >
    <div>
      <!-- 导航部分 -->
      <nav class="flex-nav">
        <div class="container">
          <div class="grid">
            <div class="column-xs-9 column-md-8">
              <p id="logo">书籍是人类进步的阶梯<span id="highlight">.</span></p>
            </div>
            <div class="column-xs-3 column-md-4">
              <!-- 添加导航切换事件 -->
              <a href="#" class="toggle-nav" @click.prevent="toggleNav">
                Menu <i class="ion-navicon-round"></i>
              </a>
              <!-- 动态生成导航项 -->
              <ul :class="{ active: isNavActive }">
                <li >
                  <router-link :to="{
                    name: 'ProductDetail',
                    params: {
                           id: (slides[currentIndex] && slides[currentIndex].id) || 0,
                           _refresh: Date.now()
                    }
                  }">了解详情</router-link>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </nav>

      <!-- 主内容区域 -->
      <main class="intro-section">
        <div class="container">
          <div class="grid">
            <div class="column-xs-12">
              <!-- 动态生成幻灯片 -->
              <ul class="slider">
                <li
                  v-for="(slide, index) in slides"
                  :key="index"
                  class="slider-item"
                  :class="{ active: currentIndex === index }"
                >
                  <div class="grid vertical">
                    <!-- 保持原有结构但使用动态数据 -->
                    <div class="column-xs-12 column-md-2 hide-mobile">
                      <div class="intro desktop-title"> <!-- 替换原第54行附近 -->

                        <a href="#" class="mobile-title">
                          <h1 class="title"  style="z-index: 9999"><span class="underline" style="z-index: 9999">{{ slide.title }}</span></h1>
                        </a>
                      </div>
                    </div>
                    <div class="column-xs-12 column-md-10">
                      <div class="image-holder">
                   <img :src="slide.image || 'https://via.placeholder.com/800x500'">
                      </div>
                      <div class="grid">
                        <div class="column-xs-12 column-md-9">
                          <div class="intro show-mobile">
                            <a href="#" class="mobile-title">
                              <h1 class="title"><span class="underline">{{ slide.title }}</span></h1>
                            </a>
                          </div>
                          <p class="description">{{ slide.content }}</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>

              <!-- 控制按钮绑定事件 -->
              <div class="grid">
                <div class="column-xs-12">
                  <div class="controls">
                    <button class="previous" @click="prevSlide">
                      <span class="visually-hidden">Previous</span>
                      <span class="icon arrow-left" aria-hidden="true"></span>
                    </button>
                    <button class="next" @click="nextSlide">
                      <span class="visually-hidden">Next</span>
                      <span class="icon arrow-right" aria-hidden="true"></span>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script>
import slide from "html-webpack-plugin/lib/chunksorter";
export default {
  computed: {
    slide() {
      return slide
    }
  },
  data() {
    return {
      currentIndex: 0,
      isNavActive: false,
      slides: [
        {
          id: 5,
          title: 'histories\nbooks',
          content: '二十四史，犹如一座承载着数千年华夏文明发展脉络的巍峨大厦...',
          image: 'https://pic3.zhimg.com/v2-30c3ede5e7330e77fabb9fee996ab011_720w.jpg?source=172ae18b'
        },
        {
          id: 6,
          title: '  three\n' +
            'Musket',
          content: '三个火枪手，一艘满载着大仲马奇幻冒险想象的坚固航船...',
          image: 'https://img.alicdn.com/bao/uploaded/i1/3715517208/O1CN01HFBemy237JgdDP0P7_!!0-item_pic.jpg'
        },
        {
          id: 7,
          title: 'Diaryof\n' +
            'catlaugh',
          content: '《笑猫日记》，一部充满童真的暖心故事集...',
          image: 'https://img.alicdn.com/bao/uploaded/O1CN01ajBpWs1chVGpCprNZ_!!6000000003632-0-yinhe.jpg'
        }
      ]
    }
  },
  methods: {
    nextSlide() {
      this.currentIndex = (this.currentIndex < this.slides.length - 1)
        ? this.currentIndex + 1
        : 0
      console.log(this.currentIndex)
    },
    prevSlide() {
      this.currentIndex = (this.currentIndex > 0)
        ? this.currentIndex - 1
        : this.slides.length - 1
      console.log(this.currentIndex)
    },
    toggleNav() {
      this.isNavActive = !this.isNavActive
    },
    handleKeyPress(e) {
      if (e.keyCode === 37) this.prevSlide()
      else if (e.keyCode === 39) this.nextSlide()
    }
  },
  mounted() {
    window.addEventListener('keydown', this.handleKeyPress)
  },
  beforeUnmount() {
    window.removeEventListener('keydown', this.handleKeyPress)
  }
}
</script>
<style lang="scss" >

@import url('https://fonts.googleapis.com/css?family=Barlow:400,500,700');

$white: #ffffff;
$black: #232323;
$gray: #fd9452;
$red: #e83f43;

$md: 768px;

* {
  box-sizing: border-box;
  &::before, &::after {
    box-sizing: border-box;
  }
}

body {
  font-family: 'Barlow', sans-serif;
  /*font-size: 1.125rem;
  font-weight: 400;*/
  line-height: 1.6;
  color: $gray;
  background: $white;
  text-rendering: optimizeLegibility;
  overflow-x: hidden;
  width: 1500px;
  height: 0px;
}

a {
  color: $gray;
  text-decoration: none;
  &:hover {
    color: $black;
  }
}

nav {
  position: relative;
  z-index: 2;
  padding: 1.25rem 0 1.25rem 0;
  ul {
    line-height: 2.2;
    display: flex;
    justify-content: flex-start;
    li {
      margin: 0.325rem 0;
    }
  }
}

#logo {
  color: $black;
  font-weight: 700;
  font-size: 1.125rem;
}

#highlight {
  color: $red;
}

.image-holder {
  position: relative;
  height: 0;
  overflow: hidden;


  img {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
}

.vertical {
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.intro {
  position: relative;
  z-index: 2;
  opacity: 0;
  animation: fadeIn 0.8s 0.4s ease forwards;
  .title {
    display: inline-block;
    font-size: 2rem;
    font-weight: 500;
    line-height: 1.1;
    text-decoration: underline;
    color: $red;
    width: min-content; /* 根据内容宽度自动换行 */
    white-space: pre-wrap; /* 保留换行符 */
    .underline {
      color: $black;
    }
  }
}

.description {
  position: relative;
  z-index: 1;
  margin: 1rem 0 1.5rem 0;
  font-size: 1rem;
  opacity: 0;
  animation: fadeIn 0.8s 0.6s ease forwards;
}

.slider-item {
  display: none;
  &.active {
    display: block;
    .hide-mobile {
      display: none;
    }
    .show-mobile {
      display: block;
      margin: 1rem 0;
    }
  }
}

button {
  position: relative;
  display: inline-block;
  cursor: pointer;
  outline: none;
  border: 0;
  vertical-align: middle;
  text-decoration: none;
  background: transparent;
  margin: 0;
  padding: 0;
  font-size: 100%;
  font: inherit;
  font-size: inherit;
  width: 55px;
  height: 55px;
}

.controls {
  position: relative;
  z-index: 1;
  display: flex;
  justify-content: flex-end;
  padding: 0.25rem 0 0.25rem 0;
  .previous {
    border: 1px solid $black;
  }
  .next {
    border-top: 1px solid $black;
    border-right: 1px solid $black;
    border-bottom: 1px solid $black;
  }
  .icon {
    position: relative;
    margin: auto;
    width: 20px;
    height: 1px;
    background-color: currentColor;
    &.arrow-left {
      &::before {
        content: '';
        position: absolute;
        left: 1px;
        top: -4px;
        width: 9px;
        height: 9px;
        border-top: solid 1px currentColor;
        border-right: solid 1px currentColor;
        transform: rotate(-135deg);
      }
    }
    &.arrow-right {
      &::before {
        content: '';
        position: absolute;
        right: 1px;
        top: -4px;
        width: 9px;
        height: 9px;
        border-top: solid 1px currentColor;
        border-right: solid 1px currentColor;
        transform: rotate(45deg);
      }
    }
  }
}

.previous, .next {
  display: flex;
  align-content: center;
  justify-content: center;
  flex-basis: 15%;
  font-size: 2rem;
  color: $black;
  &:hover .icon {
    transform: scale(1.5);
  }
  .icon {
    transition: transform 0.1s ease;
  }
}

.active .image-holder::before {
  display: grid;
  grid-template-rows: 1fr;
  position: absolute;
  content: '';
  z-index: 1;
  top: 0;
  left: 0;
  width: 100%;
  height: 0;
  padding-bottom: 56.25%;
  background: $white;
  transform-origin: 100% 50%;
  animation: revealRight 1s cubic-bezier(0.23,1,0.75,1) forwards;
  img {
    grid-row: 1 / -1;
    grid-column: 1 / -1;
  }
}

@keyframes revealRight {
  0% {
    transform: scaleX(1);
  }
  100% {
    transform: scaleX(0);
  }
}

@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

@keyframes fadeInLeft {
  0% {
    opacity: 0;
    transform: translateX(20%);
  }
  100% {
    opacity: 1;
    transform: translate(0);
  }
}

.toggle-nav {
  display: flex;
  justify-content: flex-end;
  font-size: 1rem;
  line-height: 1.9;
  i {
    font-size: 1.5rem;
    line-height: 1.3;
    margin: 0 0 0 0.5rem;
  }
}

.flex-nav ul {
  position: absolute;
  z-index: 1;
  list-style: none;
  margin: 0;
  padding: 0;
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  display: none;
  width: 100%;
  left: 0;
  padding: 1rem;
  background: $white;
  text-align: center;
  &.active {
    display: flex;
  }
}
@media (min-width: $md) {
  .desktop-title {
    position: absolute;
    top: 35%;
    left: 10%;
    height: 1000px;
    z-index: 3;
    .title {
      background: rgba($white, 0.9);
      padding: 1rem 2rem;
      box-shadow: 0 0 20px rgba(0,0,0,0.1);

    }
  }
  nav ul {
    justify-content: flex-end;
  }

  #logo {
    font-size: 1.25rem;
  }

  .controls {
    padding: 1.75rem 0 1.75rem 0;
  }

  .intro {
    animation: fadeInLeft 0.8s 0.4s ease forwards;
    .title {
      font-size: 4.25rem;
      padding: 2.5rem 2.5rem 3rem 2.5rem;
      background: $white;
    }
  }

  .image-holder {
    position: relative;
    height: 0;
    padding-bottom: 10%;
    overflow: hidden;

    img {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

    @media (min-width: $md) {
      height: auto;
      padding-bottom: 35%;

      img {
        height: 60vh;
      }
    }
  }

  /*.mobile-title {
    display: none;
  }*/

  .container {
     // 控制整体宽度
     width: 1000px; // 根据需求调整
     margin: 0 auto; // 居中
     padding: 0 2rem;
   }
  .description {
    font-size: 1.25rem;
    margin: 1rem 0 0 0;
  }

  .previous, .next {
    flex-basis: 8.33%;
  }

  .slider-item.active .hide-mobile {
    display: block;
  }

  .slider-item.active .show-mobile {
    display: none;
  }

  .toggle-nav {
    display: none;
  }

  .flex-nav ul {
    display: flex;
    flex-direction: row;
    position: relative;
    padding: 0;
    justify-content: flex-end;
  }

  nav {
    padding: 1.75rem 0 1.75rem 0;
    ul {
      line-height: 2.2;
      display: flex;
      justify-content: flex-start;
      li {
        font-size: 1rem;
        text-transform: uppercase;
        margin: 0 2rem 0 0;
        &:nth-child(3) {
          margin: 0;
        }
      }
    }
  }
}

.visually-hidden {
  clip: rect(0 0 0 0);
  clip-path: inset(50%);
  height: 1px;
  overflow: hidden;
  position: absolute;
  white-space: nowrap;
  width: 1px;
}
</style>
