<template>
  <b-list-group-item
    class="d-flex justify-content-between align-items-center"
    @click="selectHouse"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    :class="{ 'mouse-over-bgcolor': isColor }"
  >
    <!-- <b-col cols="2" class="text-center align-self-center">
      <b-img
        thumbnail
        src="https://picsum.photos/250/250/?image=58"
        alt="Image 1"
      ></b-img>
    </b-col> -->
    <b-col cols="10" class="align-self-center">
      <h5>{{ house.aptname }}</h5>
      {{ house.dong }}{{ house.jibun }}
    </b-col>
    <b-badge variant="primary" pill>{{
      this.houses_count[house.aptname]
    }}</b-badge>
  </b-list-group-item>
</template>

<script>
import { mapActions } from "vuex";
import { mapState } from "vuex";
export default {
  name: "HouseListItem",
  data() {
    return {
      isColor: false,
    };
  },
  props: {
    house: Object,
  },
  methods: {
    ...mapActions(["detailHouse"]),
    selectHouse() {
      console.log("listRow : ", this.house);
      // this.$store.dispatch("getHouse", this.house);
      this.detailHouse(this.house);
      this.$emit("toggle", " ");
    },
    colorChange(flag) {
      this.isColor = flag;
    },
  },
  computed: {
    ...mapState(["selected_houses", "houses_distinct", "houses_count"]),

    // houses() {
    //   return this.$store.state.houses;
    // },
  },
};
</script>

<style scoped>
.apt {
  width: 50px;
}
.mouse-over-bgcolor {
  background-color: lightblue;
}
</style>
