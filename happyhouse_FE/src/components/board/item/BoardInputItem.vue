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
            v-model="userInfo.userid"
            type="text"
            required
            placeholder="작성자 입력..."
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
        <b-button type="reset" variant="danger" class="m-1">초기화</b-button>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
/* eslint-disable */
import { mapState } from "vuex";
const userStore = "userStore";

import { viewBoard, createBoard, modifyBoard } from "@/api/board.js";

export default {
  name: "BoardInputItem",
  data() {
    return {
      article: {
        no: 0,
        userid: "",
        title: "",
        contents: "",
      },
    };
  },
  props: {
    type: { type: String },
  },
  computed: { ...mapState(userStore, ["userInfo"]) },
  created() {
    if (this.type === "modify") {
      viewBoard(
        this.$route.params.articleno,
        ({ data }) => {
          this.article = data.notice;
        },
        (error) => {
          console.log(error);
        }
      );
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();
      let err = true;
      let msg = "";
      !this.article.title &&
        !(this.type === "registerReply") &&
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
      this.article.no = 0;
      this.article.title = "";
      this.article.contents = "";
      this.$router.push({ name: "boardList" });
    },
    registArticle() {
      let board = {
        userid: this.userInfo.userid,
        title: this.article.title,
        contents: this.article.contents,
      };
      createBoard(
        board,
        ({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data == "1") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        },
        (error) => {
          console.log(error);
        }
      );
    },
    modifyArticle() {
      modifyBoard(
        this.article,
        ({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data == "1") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          // 현재 route를 /list로 변경.
          this.$router.push({ name: "boardList" });
        },
        (error) => {
          console.log(error);
        }
      );
    },
    moveList() {
      this.$router.push({ name: "boardList" });
    },
  },
};
</script>

<style></style>
