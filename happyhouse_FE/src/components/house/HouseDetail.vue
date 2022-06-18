<template>
  <b-container v-if="house" class="bv-example-row">
    <b-sidebar
      :visible="open"
      id="sidebar-1"
      title="아파트 상세정보"
      class="my-sidebar"
      shadow
      right
      @change="sidebarchange"
    >
      <b-row style="margin-left: 10px; margin-bottom: 10px">
        <b-button
          v-on:click="addlikeapt"
          class="float-right"
          v-if="!this.isliked && this.isLogin"
          >☆</b-button
        >
        <b-button
          v-on:click="deletelikeapt"
          class="float-right"
          v-if="this.isliked && this.isLogin"
          >★</b-button
        >
      </b-row>
      <b-img
        thumbnail
        src="https://picsum.photos/250/250/?image=58"
        alt="Image 1"
      ></b-img>
      <b-row>
        <b-col>
          <h5>{{ house.aptname }}</h5>
        </b-col>
      </b-row>

      <!-- <b-alert show variant="warning">
        주변 시설 보기
        <b-form-group v-slot="{ ariaDescribedby }">
          <b-form-checkbox-group
            id="checkbox-group-1"
            v-model="checkedCategorys"
            :options="options"
            :aria-describedby="ariaDescribedby"
            name="flavour-1"
            @change="changeChecked"
          ></b-form-checkbox-group>
        </b-form-group>
      </b-alert> -->
      <b-row class="mb-2 mt-1">
        <b-col>
          <!-- <b-img :src="require('@/assets/apt.png')" fluid-grow></b-img> -->
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <b-alert show variant="secondary">
            건축년도 : {{ house.buildyear }}
          </b-alert>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <b-alert show variant="primary">
            아파트 이름 : {{ house.aptname }}
          </b-alert>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <b-alert show variant="info">법정동 : {{ house.dong }}</b-alert>
        </b-col>
      </b-row>
      <b-row>
        <!-- <b-col>
          <b-alert show variant="warning">층수 : {{ house.floor }}층</b-alert>
        </b-col> -->
      </b-row>
      <b-row>
        <b-col>
          <b-alert show variant="danger"> 거래 기록 </b-alert>

          <b-list-group>
            <b-list-group-item
              class="flex-column align-items-start"
              v-for="(house, index) in selected_houses"
              :key="index"
              :house="house"
              style="margin-top: 10px"
            >
              <div class="d-flex w-100 justify-content-between">
                <h5 class="mb-1">
                  {{ house.floor }}층 {{ house.area }}m<sup>2</sup>
                </h5>
                <small class="text-muted"
                  >{{ house.dealyear }}/{{ house.dealmonth }}/{{
                    house.dealday
                  }}</small
                >
              </div>

              <p class="mb-1">
                {{ parseInt(house.dealamount.replace(",", "")) | price }}원
              </p>
            </b-list-group-item>
          </b-list-group>
        </b-col>
      </b-row>
    </b-sidebar>
  </b-container>
</template>
<script>
import { mapState, mapActions, mapMutations } from "vuex";
const userStore = "userStore";
export default {
  name: "HouseDetail",
  data() {
    return {
      open: false,
      open2: true,
      checkedCategorys: [],
      isliked: false,
      selected: [], // Must be an array reference!
      options: [
        { text: "은행", value: "은행" },
        { text: "마트", value: "마트" },
        { text: "약국", value: "약국" },
        { text: "주유소", value: "주유소" },
        { text: "카페", value: "카페" },
        { text: "편의점", value: "편의점" },
      ],
    };
  },
  created() {
    this.CLEAR_CHECKED_CATEGORYS();
    //var sidebar = document.querySelectorAll(".sidebar-1");
  },
  watch: {
    house: function () {
      //alert(this.house.likeno);
      this.isliked = false;
      for (var i in this.houses_like) {
        if (this.house.aptcode == this.houses_like[i].aptcode) {
          this.isliked = true;
          break;
        }
      }
    },
    houses_like: function () {
      this.isliked = false;
      for (var i in this.houses_like) {
        if (this.house.aptname == this.houses_like[i].aptname) {
          this.isliked = true;
          this.detailHouseSet(this.houses_like[i]);
          break;
        }
      }
    },
  },
  computed: {
    ...mapState([
      "house",
      "selected_houses",
      "checkedCategorys",
      "isLogin",
      "userInfo",
      "houses_like",
    ]),
    ...mapState(userStore, ["isLogin", "userInfo"]),

    // house() {
    //   return this.$store.state.house;
    // },s
  },
  methods: {
    ...mapActions([
      "categoryCheck",
      "setLikeHouseList",
      "getLikeHouseList",
      "deleteLikeHouseList",
      "detailHouse_like",
      "detailHouse",

      "detailHouseSet",
    ]),
    opensidebar() {
      if (!this.open) {
        this.open = true;
        this.open2 = false;
      }
    },
    addlikeapt() {
      //alert(this.userInfo.userid);
      if (this.isLogin) {
        //this.isliked = true;
        this.setLikeHouseList({
          userid: this.userInfo.userid,
          aptcode: this.house.aptcode,
        });
        //this.getLikeHouseList(this.userInfo.userid);
      }
    },
    deletelikeapt() {
      if (this.isLogin) {
        //this.isliked = false;
        this.deleteLikeHouseList({
          likeno: this.house.likeno,
          userid: this.userInfo.userid,
        });
        //this.getLikeHouseList(this.userInfo.userid);
      }
    },
    changeChecked() {
      this.categoryCheck(this.checkedCategorys);
    },
    sidebarchange() {
      //alert(this.open2);
      if (!this.open2) this.open2 = true;
      else {
        this.open = false;
        this.open2 = false;
      }
    },
    ...mapMutations(["CLEAR_CHECKED_CATEGORYS"]),
  },
  filters: {
    price(value) {
      var v = value;
      if (!value) return value;
      if (value > 10000) {
        value = parseInt(value / 10000) + "억 ";
      }
      if (v % 10000 > 0) {
        value = value + (v % 10000) + "만";
      }
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
};
</script>
<style></style>
