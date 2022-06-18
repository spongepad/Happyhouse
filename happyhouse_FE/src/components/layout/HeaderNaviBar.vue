<template>
  <div>
    <b-navbar class="main-nav" toggleable="lg" fixed="top">
      <b-navbar-brand href="#">
        <router-link to="/">
          <img
            src="@/assets/logo.png"
            class="d-inline-block align-middle"
            width="200px"
            alt="logo"
          />
        </router-link>
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav d-flex>
        <b-navbar-nav class="m-auto">
          <b-nav-item href="#">
            <router-link :to="{ name: 'home' }" class="link">
              <b-icon icon="house-door" font-scale="1.5"></b-icon>
              Home
            </router-link>
          </b-nav-item>
          <b-nav-item href="#">
            <router-link :to="{ name: 'board' }" class="link">
              <b-icon icon="clipboard" font-scale="1.5"></b-icon>
              게시판
            </router-link>
          </b-nav-item>
          <b-nav-item href="#">
            <router-link :to="{ name: 'qna' }" class="link">
              <b-icon icon="question-square" font-scale="1.5"></b-icon>
              QnA
            </router-link>
          </b-nav-item>
          <b-nav-item href="#">
            <router-link :to="{ name: 'house' }" class="link">
              <b-icon icon="house-fill" font-scale="1.5"></b-icon>
              아파트정보
            </router-link>
          </b-nav-item>
        </b-navbar-nav>

        <b-navbar-nav v-if="isLogin" class="user-nav">
          <b-nav-item-dropdown right>
            <template #button-content>
              <b-icon icon="people-fill" class="link" font-scale="2"></b-icon>
            </template>
            <b-dropdown-item href="#">
              <div v-b-modal.userinfo-modal>
                <b-icon icon="person-circle"></b-icon>
                회원정보
              </div>
            </b-dropdown-item>
            <b-dropdown-item href="#">
              <div @click="logout()">
                <b-icon icon="door-open-fill"></b-icon>
                로그아웃
              </div>
            </b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>

        <b-navbar-nav v-else class="navbar-dark user-nav">
          <router-link :to="{ name: 'signIn' }">
            <b-icon icon="key" scale="1.5"></b-icon>
            로그인
          </router-link>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>

    <b-modal
      id="userinfo-modal"
      size="lg"
      title="회원정보"
      hide-footer
      v-if="isLogin"
    >
      <b-form>
        <b-form-group label="아이디" label-for="user-id">
          <b-form-input id="user-id" :value="userInfo.userid" readonly />
        </b-form-group>
        <b-form-group label="비밀번호" label-for="user-pwd">
          <b-form-input
            id="user-pwd"
            class="userinfo"
            :value="userInfo.password"
            type="password"
            readonly
          />
        </b-form-group>
        <b-form-group label="사용자 이름" label-for="user-name">
          <b-form-input
            id="user-name"
            class="userinfo"
            :value="userInfo.username"
            readonly
          />
        </b-form-group>
        <b-form-group label="전화번호" label-for="user-tel">
          <b-form-input
            id="user-tel"
            class="userinfo"
            :value="userInfo.tel"
            readonly
          />
        </b-form-group>
        <b-form-group label="이메일" label-for="name-email">
          <b-form-input
            id="user-email"
            class="userinfo"
            :value="userInfo.email"
            type="email"
            readonly
          />
        </b-form-group>
      </b-form>
      <b-button
        size="sm"
        class="mr-2"
        variant="success"
        @click="update()"
        v-if="is_modify"
      >
        수정하기
      </b-button>
      <b-button
        size="sm"
        class="mr-2"
        variant="success"
        @click="changeForm()"
        v-else
      >
        회원수정
      </b-button>
      <b-button
        size="sm"
        variant="danger"
        @click="changeForm()"
        v-if="is_modify"
      >
        취소
      </b-button>
      <b-button size="sm" variant="danger" @click="remove()" v-else>
        회원탈퇴
      </b-button>
    </b-modal>
  </div>
</template>

<script>
import { mapState, mapMutations, mapActions } from "vuex";
const userStore = "userStore";

export default {
  name: "HeaderNaviBar",
  data() {
    return {
      is_modify: false,
    };
  },
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo"]),
  },
  methods: {
    ...mapMutations(userStore, ["SET_LOGOUT"]),
    ...mapActions(userStore, ["userUpdate", "userDelete"]),
    logout() {
      this.SET_LOGOUT();
      this.$router.go();
    },
    changeForm() {
      this.is_modify = !this.is_modify;
      let inputs = document.getElementsByClassName("userinfo");
      let attr = ["password", "username", "tel", "email"];

      for (let i = 0; i < inputs.length; i++) {
        inputs[i].readOnly = !this.is_modify;
        inputs[i].value = this.userInfo[attr[i]];
      }
    },
    update() {
      let user = this.readUser();
      this.userUpdate(user);
      this.changeForm();
    },
    remove() {
      let pwdCk = prompt("비밀번호를 입력하세요");
      console.log(pwdCk);
      if (pwdCk === this.userInfo.password) {
        let user = this.readUser();
        this.userDelete(user);
        this.$router.push({ name: "home" });
      }
    },
    readUser() {
      let id = document.getElementById("user-id").value;
      let pwd = document.getElementById("user-pwd").value;
      let email = document.getElementById("user-email").value;
      let tel = document.getElementById("user-tel").value;
      let name = document.getElementById("user-name").value;
      let user = {
        email: email,
        password: pwd,
        tel: tel,
        userid: id,
        username: name,
      };
      return user;
    },
  },
};
</script>

<style scoped>
.main-nav {
  background-color: rgba(0, 0, 0, 0.711);
}
.user-nav {
  margin-left: 13rem;
}
.link {
  color: ivory;
}
.link:hover {
  color: orange;
}
</style>
