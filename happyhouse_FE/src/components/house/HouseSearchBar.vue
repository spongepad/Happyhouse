<template>
  <b-row class="mt-2 mb-4 text-center">
    <!-- <b-col class="sm-3">
      <b-form-input
        v-model.trim="dongCode"
        placeholder="동코드 입력...(예 : 11110)"
        @keypress.enter="sendKeyword"
      ></b-form-input>
    </b-col>
    <b-col class="sm-3" align="left">
      <b-button variant="outline-primary" @click="sendKeyword">검색</b-button>
    </b-col> -->
    <b-row
      style="
        background-color: white;
        width: 60%;
        height: 70px;
        border-radius: 5px;
        border: 1px solid lightgrey;
        margin-left: 20%;
      "
    >
      <b-row style="margin-right: 0px">
        <b-col class="sm-3" style="margin: auto">
          <b-form-select
            v-model="sidoCode"
            :options="sidos"
            @change="gugunList"
          ></b-form-select>
        </b-col>
        <b-col class="sm-3" style="margin: auto">
          <b-form-select
            v-model="gugunCode"
            :options="guguns"
            @change="dongList"
          ></b-form-select>
        </b-col>
        <b-col class="sm-3" style="margin: auto">
          <b-form-select v-model="dongCode" :options="dongs"></b-form-select>
        </b-col>
        <b-col class="sm-3" style="margin: auto">
          <b-input
            v-model="searchName"
            type="text"
            placeholder="아파트 이름 입력"
          ></b-input>
        </b-col>

        <b-button @click="searchApt">검색</b-button>
      </b-row>

      <b-row style="margin: auto">
        주변 시설 보기&nbsp;&nbsp;
        <b-form-group v-slot="{ ariaDescribedby }" style="margin: auto">
          <b-form-checkbox-group
            id="checkbox-group-1"
            v-model="checkedCategorys"
            :options="options"
            :aria-describedby="ariaDescribedby"
            name="flavour-1"
            @change="changeChecked"
          ></b-form-checkbox-group>
        </b-form-group>
      </b-row>
    </b-row>
  </b-row>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

export default {
  name: "HouseSearchBar",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
      searchName: "",
      checkedCategorys: [],
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
  computed: {
    ...mapState(["sidos", "guguns", "dongs", "houses", "checkedCategorys"]),
    // sidos() {
    //   return this.$store.state.sidos;
    // },
  },
  created() {
    // this.$store.dispatch("getSido");
    // this.sidoList();
    this.CLEAR_SIDO_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions([
      "getSido",
      "getGugun",
      "getDong",
      "getHouseList",
      "getHouseListByName",
      "categoryCheck",
    ]),
    ...mapMutations(["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST", "CLEAR_DONG_LIST"]),
    // sidoList() {
    //   this.getSido();
    // },
    gugunList() {
      console.log(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    dongList() {
      console.log(this.gugunCode);
      this.CLEAR_DONG_LIST();
      this.dongCode = null;

      if (this.gugunCode) this.getDong(this.gugunCode);
    },
    searchApt() {
      if (this.searchName && this.dongCode)
        this.getHouseList({
          dongCode: this.dongCode,
          aptName: this.searchName,
        });
      else if (this.searchName)
        this.getHouseList({ dongCode: "", aptName: this.searchName });
      else if (this.dongCode)
        this.getHouseList({ dongCode: this.dongCode, aptName: "" });
      this.$emit("toggle", " ");
    },
    searchAptByname() {},
    changeChecked() {
      this.categoryCheck(this.checkedCategorys);
    },
  },
};
</script>

<style></style>
