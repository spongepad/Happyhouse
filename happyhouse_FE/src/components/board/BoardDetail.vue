<template>
  <b-container class="bv-example-row mt-3">
    <b-col class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-primary" @click="listArticle">목록</b-button>
      </b-col>
      <b-col class="text-right" v-if="is_sameUser">
        <b-button
          variant="outline-info"
          size="sm"
          @click="moveModifyArticle"
          class="mr-2"
        >
          글수정
        </b-button>
        <b-button variant="outline-danger" size="sm" @click="deleteArticle">
          글삭제
        </b-button>
      </b-col>
    </b-col>
    <b-col class="mb-1">
      <b-col>
        <b-card class="mb-2" border-variant="dark" no-body>
          <template #header>
            <h4 class="mb-2 text-left">{{ article.title }}</h4>
            <div class="text-left">
              {{ article.userid }} &nbsp; {{ article.regdate }}
            </div>
          </template>

          <b-card-body class="text-left">
            <div v-html="message"></div>
          </b-card-body>
        </b-card>
      </b-col>

      <b-col>
        <b-input-group class="mb-2">
          <b-form-textarea
            id="reply-input"
            v-model="context"
            placeholder="댓글 입력"
            rows="2"
            max-rows="2"
            no-resize
          ></b-form-textarea>
          <b-input-group-append>
            <b-button variant="info" @click="writeReply">작성하기</b-button>
          </b-input-group-append>
        </b-input-group>
      </b-col>

      <b-col v-if="replys.length">
        <board-reply-item
          v-for="reply in replys"
          :key="reply.no"
          v-bind="reply"
          v-on:deleteReply="reload"
        />
      </b-col>
    </b-col>
  </b-container>
</template>

<script>
// import moment from "moment";
/* eslint-disable */
import { mapState } from "vuex";
const userStore = "userStore";

import { viewBoard, createReply, deleteBoard } from "@/api/board.js";
import BoardReplyItem from "@/components/board/item/BoardReplyItem";
export default {
  name: "BoardDetail",
  components: {
    BoardReplyItem,
  },
  data() {
    return {
      article: {},
      replys: [],
      componentKey: 0,
      context: "",
      is_sameUser: null,
    };
  },
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo"]),
    message() {
      if (this.article.contents)
        return this.article.contents.split("\n").join("<br>");
      return "";
    },
  },
  async created() {
    await viewBoard(
      this.$route.params.articleno,
      ({ data }) => {
        this.article = data.notice;
        this.replys = data.replys;
      },
      (error) => {
        console.log(error);
      }
    );
    if (this.userInfo) {
      this.is_sameUser = this.userInfo.userid === this.article.userid;
    }
  },
  mounted() {
    if (!this.isLogin) {
      let replyInput = document.getElementById("reply-input");
      replyInput.placeholder = "로그인 후 사용가능 합니다.";
    }
  },
  methods: {
    listArticle() {
      this.$router.push({ name: "boardList" });
    },
    moveModifyArticle() {
      this.$router.replace({
        name: "boardModify",
        params: { articleno: this.article.no },
      });
    },
    writeReply() {
      if (!this.isLogin) {
        alert("로그인 후 사용가능 합니다.");
        return;
      }

      let reply = {
        userid: this.userInfo.userid, // 임시 아이디
        content: this.context,
        notice_no: this.$route.params.articleno,
      };
      createReply(
        reply,
        ({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          //수정필요
          if (data > 0) {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.reload();
        },
        (error) => {
          console.log(error);
        }
      );
    },
    deleteArticle() {
      if (confirm("정말로 삭제?")) {
        deleteBoard(this.article.no, ({ data }) => {
          let msg = "삭제 처리시 문제가 발생했습니다.";
          if (data == "1") {
            msg = "삭제가 완료되었습니다.";
          }
          alert(msg);
          // 현재 route를 /list로 변경.
          this.$router.push({ name: "boardList" });
        });
      }
    },
    reload() {
      this.componentKey + 1;
      this.$router.go();
    },
  },
  // filters: {
  //   dateFormat(regtime) {
  //     return moment(new Date(regtime)).format("YY.MM.DD hh:mm:ss");
  //   },
  // },
};
</script>

<style></style>
