<template>
  <b-container class="bv-example-row mt-0 text-center container">
    <b-row>
      <map-api
        style="position: absolute; z-index: 0"
        @toggle="toggleSidebar_right"
      ></map-api>
      <house-search-bar
        style="padding-left: 5%; position: absolute; z-index: 1"
        @toggle="toggleSidebar_left"
      ></house-search-bar>
    </b-row>
    <b-row>
      <b-col cols="6" align="left">
        <house-list ref="houselist" @toggle="toggleSidebar_right" />
      </b-col>
      <b-col cols="6">
        <house-detail ref="housedetail" />
      </b-col>
    </b-row>
  </b-container>
</template>
<script>
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import HouseList from "@/components/house/HouseList.vue";
import HouseDetail from "@/components/house/HouseDetail.vue";
import MapApi from "@/components/map/MapApi.vue";
import { mapState, mapActions } from "vuex";
const userStore = "userStore";
export default {
  data() {
    return {};
  },
  name: "HouseView",
  components: {
    HouseSearchBar,
    HouseList,
    HouseDetail,
    MapApi,
  },
  created() {
    //즐겨찾기 불러오기
    if (this.isLogin) {
      this.getLikeHouseList(this.userInfo.userid);
    }
  },
  methods: {
    ...mapActions(["getLikeHouseList"]),
    toggleSidebar_left() {
      this.$refs.houselist.opensidebar();
    },
    toggleSidebar_right() {
      this.$refs.housedetail.opensidebar();
    },
  },
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo"]),
  },
};
</script>
<style scoped>
.container {
  max-width: 1500px;
}
.underline-orange {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(231, 149, 27, 0.3) 30%
  );
}
</style>
