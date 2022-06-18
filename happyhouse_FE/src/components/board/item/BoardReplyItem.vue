<template>
  <b-row>
    <b-col class="mt-2">
      <b-input-group :append="regdate">
        <template #prepend>
          <b-input-group-text>
            <strong>{{ userid }}</strong>
          </b-input-group-text>
        </template>
        <b-form-input
          id="reply-modify"
          v-model="modify_content"
          v-if="ismodify"
        ></b-form-input>
        <b-form-input :value="content" readonly v-else></b-form-input>
        <b-input-group-append v-if="isSameUser">
          <b-button
            class="mr-1"
            variant="outline-info"
            size="sm"
            @click="modifyReply"
          >
            {{ ismodify == false ? "수정" : "적용" }}
          </b-button>
          <b-button variant="outline-danger" size="sm" @click="deleteReply">
            {{ ismodify == false ? "삭제" : "취소" }}
          </b-button>
        </b-input-group-append>
      </b-input-group>
    </b-col>
  </b-row>
</template>

<script>
/* eslint-disable */
import { mapState } from "vuex";
const userStore = "userStore";
import { modifyReply, deleteReply } from "@/api/board.js";

import moment from "moment";
export default {
  name: "BoardReplyItem",
  data() {
    return {
      ismodify: false,
      isSameUser: false,
      modify_content: "",
    };
  },
  props: {
    no: Number,
    notice_no: Number,
    userid: String,
    content: String,
    regdate: String,
  },
  mounted() {
    if (this.userInfo != null) {
      this.isSameUser = this.userid === this.userInfo.userid;
    }
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
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
    modifyReply() {
      if (this.ismodify) {
        let reply = {
          no: this.no,
          content: this.modify_content,
        };
        modifyReply(
          reply,
          ({ data }) => {
            let msg = "수정 중 문제가 발생했습니다.";
            if (data > 0) {
              msg = "수정 완료되었습니다.";
            }
            alert(msg);
            this.$router.go();
          },
          (error) => {
            console.log(error);
          }
        );
      } else {
        this.modify_content = this.content;
        this.ismodify = !this.ismodify;
      }
    },
    deleteReply() {
      if (this.ismodify) {
        this.ismodify = !this.ismodify;
      } else {
        if (confirm("정말로 삭제?")) {
          deleteReply(
            this.no,
            ({ data }) => {
              let msg = "삭제 처리시 문제가 발생했습니다.";
              if (data > 0) {
                msg = "삭제가 완료되었습니다.";
              }
              alert(msg);
              this.$router.go();
            },
            (error) => {
              console.log(error);
            }
          );
        }
      }
    },
  },
};
</script>

<style></style>
