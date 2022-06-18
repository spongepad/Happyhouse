<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group
          id="userid-group"
          label="작성자:"
          label-for="userid"
          description="작성자를 입력하세요."
        >
          <b-form-input
            id="userid"
            :disabled="isUserid"
            :value="userInfo.userid"
            type="text"
            required
            readonly
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="subject-group"
          label="제목:"
          label-for="subject"
          description="제목을 입력하세요."
        >
          <b-form-input
            id="subject"
            v-model="article.title"
            type="text"
            required
            placeholder="제목 입력..."
          ></b-form-input>
        </b-form-group>

        <b-form-group id="content-group" label="내용:" label-for="content">
          <b-form-textarea
            id="content"
            v-model="article.contents"
            placeholder="내용 입력..."
            rows="10"
            max-rows="15"
          ></b-form-textarea>
        </b-form-group>

        <b-button
          type="submit"
          variant="primary"
          class="m-1"
          v-if="this.type === 'register'"
        >
          글작성
        </b-button>
        <b-button type="submit" variant="primary" class="m-1" v-else>
          글수정
        </b-button>
        <b-button type="reset" variant="danger" class="m-1">취소</b-button>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
import { mapState } from "vuex";
const userStore = "userStore";

import http from "@/api/http";

export default {
  name: "QnaInputItem",
  data() {
    return {
      article: {
        no: 0,
        userid: "",
        title: "",
        contents: "",
      },
      isUserid: false,
    };
  },
  props: {
    type: { type: String },
  },
  created() {
    console.log(this.type);
    if (this.type === "modify") {
      http
        .get(`/qna-api/qna/${this.$route.params.articleno}`)
        .then(({ data }) => {
          this.article = data.qna;
        });
      this.isUserid = true;
    }
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();
      let err = true;
      let msg = "";
      !this.article.title &&
        !(this.type === "registerAnswer") &&
        ((msg = "제목 입력해주세요"),
        (err = false),
        this.$refs.subject.focus());
      err &&
        !this.article.contents &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.content.focus());
      if (!err) alert(msg);
      else {
        if (this.type === "register") this.registArticle();
        else this.modifyArticle();
      }
    },
    onReset(event) {
      event.preventDefault();
      this.$router.go(-1);
    },
    registArticle() {
      http
        .post(`/qna-api/qna`, {
          userid: this.userInfo.userid,
          title: this.article.title,
          contents: this.article.contents,
        })
        .then(({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          //수정필요
          if (data > 0) {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        });
    },
    registAnswer() {
      http
        .post(`/qna-api/answer`, {
          userid: this.article.userid,
          content: this.article.contents,
          notice_no: this.$route.params.articleno,
        })
        .then(({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          //수정필요
          if (data > 0) {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.$router.push({
            name: "qnaDetail",
            params: { articleno: this.$route.params.articleno },
          });
        });
    },
    modifyArticle() {
      http
        .put(`/qna-api/qna`, {
          no: this.$route.params.articleno,
          userid: this.article.userid,
          title: this.article.title,
          contents: this.article.contents,
        })
        .then(({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          //수정필요
          if (data > 0) {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          // 현재 route를 /list로 변경.
          this.$router.push({
            name: "qnaDetail",
            params: { articleno: this.$route.params.articleno },
          });
        });
    },
    moveList() {
      this.$router.push({ name: "qnaList" });
    },
  },
};
</script>

<style></style>
