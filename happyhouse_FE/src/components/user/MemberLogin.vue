<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <h3 class="underline-steelblue">
          <b-icon icon="person-check"></b-icon>
          로그인
        </h3>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="isLoginError">
              아이디 또는 비밀번호를 확인하세요.
            </b-alert>
            <b-form-group label="아이디:" label-for="userid">
              <b-form-input
                id="userid"
                v-model="user.userid"
                required
                placeholder="아이디 입력...."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userpwd">
              <b-form-input
                type="password"
                id="userpwd"
                v-model="user.userpwd"
                required
                placeholder="비밀번호 입력...."
                @keyup.enter="confirm"
              ></b-form-input>
              <span class="find-pwd" v-b-modal.findpwd-modal>
                비밀번호를 잊어버리셨나요?
              </span>
            </b-form-group>
            <b-button
              type="button"
              variant="primary"
              class="ml-1"
              @click="confirm"
            >
              로그인
            </b-button>
            <b-button
              type="button"
              variant="success"
              class="ml-1"
              @click="movePage"
            >
              회원가입
            </b-button>
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>

    <b-modal id="findpwd-modal" title="비밀번호 찾기" hide-footer>
      <b-form>
        <b-form-group label="아이디" label-for="user-id">
          <b-form-input v-model="find_pass.userid" />
        </b-form-group>
        <b-form-group label="사용자 이름" label-for="user-name">
          <b-form-input class="userinfo" v-model="find_pass.username" />
        </b-form-group>
        <b-form-group label="전화번호" label-for="user-tel">
          <b-form-input class="userinfo" v-model="find_pass.tel" />
        </b-form-group>
      </b-form>
      <b-button size="sm" class="mr-2" variant="success" @click="findPass()">
        찾기
      </b-button>
    </b-modal>
  </b-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
const userStore = "userStore";

export default {
  name: "MemberLogin",
  data() {
    return {
      user: {
        userid: null,
        userpwd: null,
      },
      find_pass: {
        userid: null,
        username: null,
        tel: null,
      },
    };
  },
  computed: {
    ...mapState(userStore, ["isLogin", "isLoginError", "pwd"]),
  },
  methods: {
    ...mapActions(userStore, ["userConfirm", "getUserInfo", "findPassword"]),
    async confirm() {
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getUserInfo(token);
        this.$router.push({ name: "home" });
      }
    },
    movePage() {
      this.$router.push({ name: "signUp" });
    },
    async findPass() {
      if (
        !this.find_pass.userid ||
        !this.find_pass.username ||
        !this.find_pass.tel
      ) {
        alert("양식을 모두 채워주세요.");
        return;
      }
      await this.findPassword(this.find_pass);
    },
  },
};
</script>

<style scoped>
.underline-steelblue {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(72, 190, 233, 0.3) 30%
  );
}

.find-pwd {
  font-size: 12px;
  cursor: pointer;
}

.find-pwd:hover {
  color: gray;
}
</style>
