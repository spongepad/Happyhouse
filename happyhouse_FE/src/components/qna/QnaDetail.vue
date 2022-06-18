<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-1">
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
    </b-row>
    <b-col class="mb-1" display="block">
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
            id="answer-input"
            v-model="context"
            placeholder="답변 입력"
            rows="4"
            max-rows="4"
            no-resize
          ></b-form-textarea>
          <b-input-group-append>
            <b-button variant="info" @click="writeAnswer">답변하기</b-button>
          </b-input-group-append>
        </b-input-group>
      </b-col>

      <b-col v-if="answers.length">
        <qna-answer-item
          v-for="answer in answers"
          :key="answer.no"
          v-bind="answer"
          :writer="article.userid"
        />
      </b-col>
    </b-col>
  </b-container>
</template>

<script>
import { mapState } from "vuex";
const userStore = "userStore";

import http from "@/api/http";
import QnaAnswerItem from "@/components/qna/item/QnaAnswerItem";
export default {
  name: "QnaDetail",
  components: {
    QnaAnswerItem,
  },
  data() {
    return {
      article: {},
      answers: [],
      context: "",
      is_sameUser: false,
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
    await http
      .get(`/qna-api/qna/${this.$route.params.articleno}`)
      .then(({ data }) => {
        this.article = data.qna;
        this.answers = data.answers;
      });
    if (this.userInfo) {
      this.is_sameUser = this.userInfo.userid === this.article.userid;
    }
  },
  mounted() {
    if (!this.isLogin) {
      let answerInput = document.getElementById("answer-input");
      answerInput.placeholder = "로그인 후 사용가능 합니다.";
    }
  },
  methods: {
    listArticle() {
      this.$router.push({ name: "qnaList" });
    },
    moveModifyArticle() {
      this.$router.push({
        name: "qnaModify",
        params: { articleno: this.article.no },
      });
    },
    writeAnswer() {
      if (!this.isLogin) {
        alert("로그인 후 사용가능 합니다.");
        return;
      }
      http
        .post(`/qna-api/answer`, {
          userid: this.userInfo.userid,
          content: this.context,
          notice_no: this.$route.params.articleno,
        })
        .then(({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          //수정필요
          if (data > 0) {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.$router.go();
        });
    },
    deleteArticle() {
      if (confirm("삭제 하시겠습니까?")) {
        http
          .delete(`/qna-api/qna?no=${this.$route.params.articleno}`)
          .then(({ data }) => {
            let msg = "삭제 처리시 문제가 발생했습니다.";
            if (data > 0) {
              msg = "삭제가 완료되었습니다.";
            }
            alert(msg);
            // 현재 route를 /list로 변경.
            this.$router.push({ name: "qnaList" });
          });
      }
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
