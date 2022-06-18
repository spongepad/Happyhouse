<template>
  <b-container class="bv-example-row mt-3 text-center">
    <b-row>
      <b-col cols="12">
        <b-jumbotron
          bg-variant="light"
          text-variant="dark"
          border-variant="dark"
        >
          <template #lead>
            <h3 class="underline-hotpink">
              <b-icon icon="clipboard"></b-icon>
              게시판
            </h3>
            <router-view></router-view>
          </template>

          <hr class="my-4" />

          <p></p>
        </b-jumbotron>
      </b-col>
    </b-row>
  </b-container>
</template>
<script>
export default {
  name: "BoardView",
};
</script>
<style scoped>
.underline-hotpink {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(231, 27, 139, 0.3) 30%
  );
}
</style>
