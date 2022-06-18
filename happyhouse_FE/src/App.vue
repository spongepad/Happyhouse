<template>
  <div id="app" style="overflow-x: hidden">
    <b-row>
      <navi-bar />
    </b-row>
    <br /><br /><br /><br />
    <b-row class="test">
      <router-view />
    </b-row>
  </div>
</template>

<script>
import NaviBar from "@/components/layout/HeaderNaviBar.vue";

export default {
  name: "App",
  components: {
    NaviBar,
  },
};
</script>

<style>
a {
  text-decoration: none;
}
a:hover {
  text-decoration: none;
  font-weight: bold;
  color: chartreuse;
}

a.router-link-exact-active {
  color: #3aa4ca;
}
/* html {
  overflow: hidden;
} */
.body {
  overflow: auto;
}
.test::before {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  /* https://www.wallpapertip.com/wmimgs/72-723395_singapore-aesthetic.jpg */
  background-image: url("@/assets/back3.jpg");
  /* -webkit-filter: blur(5px);
  -moz-filter: blur(5px);
  -o-filter: blur(5px);
  -ms-filter: blur(5px);
  filter: blur(5px); */
  transform: scale(1.02);
  z-index: -1;
  content: "";
}
</style>
