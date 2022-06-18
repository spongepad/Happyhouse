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
  name: "MemberView",
};
</script>

<style></style>
