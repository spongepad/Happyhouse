<template>
  <b-container class="bv-example-row mt-3 text-center">
    <b-jumbotron bg-variant="light">
      <b-img
        class="mb-3"
        :src="require('@/assets/twitter_header_photo_1.png')"
        width="400%"
      ></b-img>
      <b-row>
        <div class="text-left ml-2">
          <b-icon class="mb-1" icon="newspaper" scale="1.5"></b-icon>
          <strong class="table-head ml-3">최신 뉴스</strong>
        </div>
        <b-table
          id="news-table"
          :items="newsList"
          :fields="newsFields"
          class="router"
        >
          <template #cell(title)="data">
            <a :href="data.item.url" target="_blank" class="router">
              {{ data.item.title }}
            </a>
          </template>
        </b-table>
      </b-row>
      <br />
      <b-row>
        <b-col>
          <div class="text-left ml-2">
            <b-icon class="mb-1" icon="clipboard" scale="1.5"></b-icon>
            <strong class="table-head ml-3">게시판</strong>
          </div>
          <b-table
            id="board-table"
            :items="boardList"
            :fields="fields"
            striped
            class="router"
          >
            <template #cell(title)="data">
              <router-link
                class="router"
                :to="{
                  name: 'boardDetail',
                  params: { articleno: data.item.no },
                }"
              >
                {{ data.item.title }} &nbsp;({{ data.item.ans_cnt }})
              </router-link>
            </template>
          </b-table>
        </b-col>
        <b-col>
          <div class="text-left ml-2">
            <b-icon class="mb-1" icon="question-square" scale="1.5"></b-icon>
            <strong class="table-head ml-3">QnA</strong>
          </div>
          <b-table
            id="qna-table"
            :items="qnaList"
            :fields="fields"
            striped
            class="router"
          >
            <template #cell(title)="data">
              <router-link
                class="router"
                :to="{
                  name: 'qnaDetail',
                  params: { articleno: data.item.no },
                }"
              >
                {{ data.item.title }} &nbsp;({{ data.item.ans_cnt }})
              </router-link>
            </template>
          </b-table>
        </b-col>
      </b-row>
    </b-jumbotron>
  </b-container>
</template>

<script>
/* eslint-disable */
import axios from "axios";
import cheerio from "cheerio";

import { listBoard } from "@/api/board.js";
import http from "@/api/http";

export default {
  name: "HomeView",
  props: {
    msg: String,
  },
  data() {
    return {
      newsList: [],
      boardList: [],
      qnaList: [],
      fields: [
        { key: "title", label: "", tdClass: "tdSubject" },
        { key: "userid", label: "", tdClass: "tdClass" },
      ],
      newsFields: [
        { key: "title", label: "", tdClass: "tdSubject" },
        { key: "writer", label: "", tdClass: "tdClass" },
      ],
    };
  },
  async created() {
    await this.getHtml().then((resopnse) => {
      const $ = cheerio.load(resopnse.data);
      const $bodyList = $("#headline_news_area ul").children("li");

      $bodyList.each((i, element) => {
        this.newsList.push({
          title: $(element).find("span:first-child").text(),
          url: $(element).find("span.title a").attr("href"),
          writer: $(element).find("span.writing").text(),
          date: $(element).find("span.date").text(),
        });
        this.newsList[i].url = "https://land.naver.com/" + this.newsList[i].url;
      });
    });

    listBoard(
      5,
      ({ data }) => {
        this.boardList = data;
      },
      (error) => {
        console.log(error);
      }
    );

    http.get(`/qna-api/qna?limit=5`).then(({ data }) => {
      this.qnaList = data;
    });
  },
  methods: {
    async getHtml() {
      try {
        return await axios.get("https://land.naver.com/news/");
      } catch (error) {
        console.error(error);
      }
    },
  },
  components: {},
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
.table-head {
  font-size: 22px;
}

#news-table {
  /* color: aqua; */
}
.router {
  color: rgba(8, 8, 40, 0.644);
}
</style>
