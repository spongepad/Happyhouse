<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-1 pl-2">
      <b-col cols="1" class="text-left pr-0 pl-1">
        <b-form-select v-model="selected" :options="options" />
      </b-col>
      <b-col cols="3" class="text-left pr-0 pl-1">
        <b-form-input v-model="searchWord"></b-form-input>
      </b-col>
      <b-col cols="2" class="text-left pr-0 pl-1">
        <b-button @click="searchArtcle">검색</b-button>
      </b-col>
      <b-col class="text-right" v-if="isLogin">
        <b-button variant="outline-primary" @click="moveWrite">글쓰기</b-button>
      </b-col>
    </b-row>
    <b-row>
      <b-col v-if="articles.length">
        <b-table
          id="my-table"
          :items="articles"
          :per-page="perPage"
          :fields="fields"
          :current-page="currentPage"
          hover
          striped
          responsive
        >
          <template #cell(title)="data">
            <router-link
              class="contents"
              :to="{
                name: 'boardDetail',
                params: { articleno: data.item.no },
              }"
            >
              {{ data.item.title | crop }} &nbsp;({{ data.item.ans_cnt }})
            </router-link>
          </template>

          <template #cell(regdate)="data">
            {{ data.item.regdate | dateFormat }}
          </template>
        </b-table>
        <b-pagination
          v-model="currentPage"
          :total-rows="rows"
          :per-page="perPage"
          aria-controls="my-table"
          align="center"
        ></b-pagination>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
/* eslint-disable */
import { mapState } from "vuex";
const userStore = "userStore";

import { listBoard, searchBoard } from "@/api/board.js";
//import BoardListItem from "@/components/board/item/BoardListItem";

export default {
  name: "BoardList",
  components: {
    //BoardListItem,
  },
  data() {
    return {
      perPage: 5,
      currentPage: 1,
      articles: [],

      searchWord: "",
      selected: null,
      options: [
        { value: null, text: "선택" },
        { value: "id", text: "아이디" },
        { value: "title", text: "제목" },
        { value: "both", text: "제목+내용" },
      ],

      fields: [
        { key: "no", label: "글번호", tdClass: "tdClass" },
        { key: "title", label: "제목", tdClass: "tdSubject" },
        { key: "userid", label: "작성자", tdClass: "tdClass" },
        { key: "regdate", label: "작성일", tdClass: "tdClass" },
      ],
    };
  },
  computed: {
    ...mapState(userStore, ["isLogin"]),
    rows() {
      return this.articles.length;
    },
  },

  created() {
    listBoard(
      0,
      ({ data }) => {
        this.articles = data;
      },
      (error) => {
        console.error(error);
      }
    );
  },
  methods: {
    moveWrite() {
      this.$router.push({ name: "boardRegister" });
    },
    viewArticle(article) {
      this.$router.push({
        name: "boardDetail",
        params: { articleno: article.articleno },
      });
    },
    searchArtcle() {
      if (this.selected == null) {
        alert("검색옵션을 선택해 주세요.");
        return;
      }
      if (this.searchWord == "") {
        alert("검색어를 입력해 주세요.");
        return;
      }
      searchBoard(
        {
          type: this.selected,
          word: this.searchWord,
        },
        ({ data }) => {
          this.articles = data;
        },
        (error) => {
          console.error(error);
        }
      );
    },
  },
};
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}

.tdSubject {
  width: 300px;
  text-align: left;
}
.contents {
  color: rgba(8, 8, 40, 0.644);
}
</style>
