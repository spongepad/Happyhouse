<template>
  <b-container class="bv-example-row mt-3">
    <div>
      <b-sidebar
        :visible="open"
        id="sidebar-1"
        class="my-sidebar"
        title="검색 결과"
        shadow
        @change="sidebarchange"
      >
        <!-- <b-button v-on:click="closesidebar" class="float-right">X</b-button> -->
        <b-list-group class="px-3 py-2">
          <house-list-item
            v-for="(house, index) in houses_distinct"
            :key="index"
            :house="house"
            @toggle="toggleSidebar"
          />
        </b-list-group>
      </b-sidebar>
    </div>
  </b-container>
  <!-- <b-container v-else class="bv-example-row mt-3">
    <b-button v-on:click="open = !open">{{ open }}</b-button>
    <b-row>
      <b-col><b-alert show>주택 목록이 없습니다.</b-alert></b-col>
    </b-row>
  </b-container> -->
</template>

<script>
import HouseListItem from "@/components/house/HouseListItem.vue";
import { mapState } from "vuex";

export default {
  name: "HouseList",
  components: {
    HouseListItem,
  },
  data() {
    return {
      open: false,
      open2: false,
    };
  },
  methods: {
    closesidebar() {
      this.open = false;
    },
    opensidebar() {
      this.open = true;
    },
    toggleSidebar() {
      this.$emit("toggle", " ");
    },
    sidebarchange() {
      //alert(this.open2);
      if (!this.open2) this.open2 = true;
      else {
        this.open = false;
        this.open2 = false;
      }
    },
  },

  computed: {
    ...mapState(["houses", "houses_distinct", "isLogin", "userInfo"]),

    // houses() {
    //   return this.$store.state.houses;
    // },
  },
};
</script>

<style>
.my-sidebar .b-sidebar {
  top: 67px !important;
  width: 20%;
  height: 100% !important;
}
</style>
