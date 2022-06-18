<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <h3 class="underline-steelblue">
          <b-icon icon="person-plus"></b-icon>
          회원가입
        </h3>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="10">
        <b-card class="text-center mt-3" align="left">
          <b-form class="text-left">
            <b-form-group label="아이디:" label-for="user-id">
              <b-form-input
                id="user-id"
                v-model="user.userid"
                @keyup="idCheck"
              />
              <span v-if="isCheckId">
                <label id="success-id" class="ml-2" v-if="isAvailableId">
                  사용가능한 아이디 입니다.
                </label>
                <label id="fail-id" class="ml-2" v-else
                  >이미 사용중인 아이디 입니다.</label
                >
              </span>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="user-pwd">
              <b-form-input
                id="user-pwd"
                class="userinfo"
                type="password"
                v-model="user.password"
              />
            </b-form-group>
            <b-form-group label="비밀번호 확인:" label-for="user-pwd">
              <b-form-input
                id="user-pwd"
                class="userinfo"
                type="password"
                v-model="pwdConfirm"
              />
            </b-form-group>
            <b-form-group label="사용자 이름:" label-for="user-name">
              <b-form-input
                id="user-name"
                class="userinfo"
                v-model="user.username"
              />
            </b-form-group>
            <b-form-group label="전화번호:" label-for="user-tel">
              <b-form-input id="user-tel" class="userinfo" v-model="user.tel" />
            </b-form-group>
            <b-form-group label="이메일:" label-for="name-email">
              <b-form-input
                id="user-email"
                class="userinfo"
                type="email"
                v-model="user.email"
              />
            </b-form-group>
            <b-form-group>
              <b-button class="mr-2" variant="primary" @click="registUser">
                회원가입
              </b-button>
              <b-button variant="danger">취소</b-button>
            </b-form-group>
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
const userStore = "userStore";

export default {
  name: "MemberRegister",
  data() {
    return {
      user: {
        userid: "",
        password: "",
        username: "",
        email: "",
        tel: "",
      },
      pwdConfirm: "",
      isConfirm: false,
      isCheckId: false,
    };
  },
  computed: {
    ...mapState(userStore, ["isAvailableId"]),
  },
  methods: {
    ...mapActions(userStore, ["userRegist", "checkid"]),
    async idCheck() {
      await this.checkid(this.user.userid);
      this.isCheckId = true;
    },
    checkForm() {
      if (!this.isCheckId) {
        alert("아이디 중복검사를 해주세요.");
        return;
      }

      if (
        this.isAvailableId &&
        this.user.userid != "" &&
        this.user.password != "" &&
        this.pwdConfirm != "" &&
        this.user.username != "" &&
        this.user.email != "" &&
        this.user.tel != ""
      ) {
        this.passWordConfirm();
      } else {
        alert("모든 칸을 채워주세요.");
      }
    },
    passWordConfirm() {
      if (this.user.password != this.pwdConfirm) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
      }
      this.isConfirm = true;
    },
    registUser() {
      this.checkForm();
      if (this.isConfirm) {
        this.userRegist(this.user);
        this.$router.push({ name: "home" });
      }
    },
  },
};
</script>

<style scoped>
#success-id {
  color: green;
}
#fail-id {
  color: red;
}
.underline-steelblue {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(72, 190, 233, 0.3) 30%
  );
}
</style>
