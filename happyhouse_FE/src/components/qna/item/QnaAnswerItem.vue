<template>
  <b-col>
    <b-card class="mb-2" border-variant="dark" no-body>
      <template #header>
        <b-row class="mb-2 text-left">
          <b-col cols="11">
            <span id="answer-user">{{ userid }}</span>
          </b-col>
          <b-icon
            icon="check2-circle"
            variant="success"
            scale="1.8"
            v-if="isContact"
          />
          <b-button
            size="sm"
            variant="success"
            @click="contactAnswer"
            v-if="!isContact && isWriter"
            >채택</b-button
          >
        </b-row>
        <div id="answer-info" class="text-left">
          {{ regdate }}&nbsp;&nbsp;
          <b-button
            size="sm"
            variant="outline-primary"
            @click="likeAnswer"
            v-if="!isLiked"
          >
            <b-icon icon="hand-thumbs-up"></b-icon>
          </b-button>
          <b-button
            size="sm"
            variant="outline-primary"
            @click="likeAnswer"
            v-else
          >
            <b-icon icon="hand-thumbs-up-fill"></b-icon>
          </b-button>
          &nbsp;{{ ansLike }}&nbsp;&nbsp;&nbsp;&nbsp;
          <b-button
            class="mr-2"
            variant="outline-info"
            size="sm"
            @click="modifyAnswer"
            v-if="isSameUser"
          >
            {{ ismodify == false ? "수정" : "적용" }}
          </b-button>
          <b-button
            variant="outline-danger"
            size="sm"
            @click="deleteAnswer"
            v-if="isSameUser"
          >
            {{ ismodify == false ? "삭제" : "취소" }}
          </b-button>
        </div>
      </template>

      <b-card-body class="text-left">
        <b-form-textarea
          id="answer-modify"
          :value="content"
          v-if="ismodify"
        ></b-form-textarea>
        <div v-html="message" v-else></div>
      </b-card-body>
    </b-card>
  </b-col>
</template>

<script>
/* eslint-disable */
import { mapState } from "vuex";
const userStore = "userStore";

import moment from "moment";
import http from "@/api/http";
export default {
  name: "QnaAnswerItem",
  data() {
    return {
      ansLike: 0,
      ismodify: false,
      isSameUser: false,
      isWriter: false,
      isContact: false,
      isLiked: false,
    };
  },
  props: {
    no: Number,
    notice_no: Number,
    like: Number,
    userid: String,
    content: String,
    contact: String,
    regdate: String,
    writer: String,
  },
  created() {
    console.log(this.writer);
    this.ansLike = this.like;
  },
  mounted() {
    if (this.userInfo != null) {
      http
        .get(
          `/qna-api/answer/like/check?answer_no=${this.no}&userid=${this.userInfo.userid}`
        )
        .then(({ data }) => {
          if (data > 0) {
            this.isLiked = true;
          }
        });
      this.isSameUser = this.userid === this.userInfo.userid;
      this.isWriter = this.writer === this.userInfo.userid;
    }
    if (this.contact != 0) {
      this.isContact = true;
    }
  },
  computed: {
    ...mapState(userStore, ["userInfo", "isLogin"]),
    message() {
      if (this.content) return this.content.split("\n").join("<br>");
      return "";
    },
  },
  filters: {
    dateFormat(regdate) {
      return moment(new Date(regdate)).format("YY.MM.DD");
    },
  },
  methods: {
    deleteAnswer() {
      if (this.ismodify) {
        this.ismodify = !this.ismodify;
      } else {
        if (confirm("삭제 하시겠습니까?")) {
          http.delete(`/qna-api/answer?no=${this.no}`).then(({ data }) => {
            let msg = "삭제 처리시 문제가 발생했습니다.";
            if (data > 0) {
              msg = "삭제가 완료되었습니다.";
            }
            alert(msg);
            this.$router.go();
          });
        }
      }
    },
    modifyAnswer() {
      if (this.ismodify) {
        let answer = {
          no: this.no,
          content: document.getElementById("answer-modify").value,
        };
        http.put(`/qna-api/answer`, answer).then(({ data }) => {
          let msg = "처리중 문제가 발생하였습니다.";
          if (data > 0) {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          this.$router.go();
        });
      } else {
        this.modify_content = this.content;
        this.ismodify = !this.ismodify;
      }
    },
    likeAnswer() {
      if (!this.isLogin) {
        alert("로그인 후 이용가능합니다.");
      }
      if (!this.isLiked) {
        http
          .get(
            `/qna-api/answer/like?no=${this.no}&userid=${this.userInfo.userid}`
          )
          .then(({ data }) => {
            if (data > 0) {
              this.isLiked = true;
              this.ansLike += 1;
            }
          });
      } else {
        http
          .get(
            `/qna-api/answer/dislike?no=${this.no}&userid=${this.userInfo.userid}`
          )
          .then(({ data }) => {
            if (data > 0) {
              this.isLiked = false;
              this.ansLike -= 1;
            }
          });
      }
    },
    contactAnswer() {
      http
        .get(`/qna-api/answer/contact/check/${this.notice_no}`)
        .then(({ data }) => {
          if (data == 0) {
            http.get(`/qna-api/answer/contact/${this.no}`).then(({ data }) => {
              let msg = "처리중 문제가 발생하였습니다.";
              if (data > 0) {
                msg = "선택한 답변을 채택하였습니다.";
              }
              alert(msg);
              this.$router.go();
            });
          } else {
            alert("답변 채택은 하나만 가능합니다.");
          }
        });
    },
  },
};
</script>

<style scoped>
#answer-info {
  font-size: 14px;
}

#answer-user {
  font-size: 18px;
  font-weight: bold;
}
</style>
