<template>
  <div class="container map-container">
    <div id="map"></div>
  </div>
</template>

<script>
/* eslint-disable */
import { mapState } from "vuex";
import { mapActions } from "vuex";
export default {
  name: "MapApi",
  data() {
    return {
      map: null,
      geocoder: null,
      infowindows: [],
      ps: null,
      markers: [],
      markers_like: [],
      markers_category: [],
      myLoc: {
        lat: 0,
        lng: 0,
      },
      category: {
        은행: ["BK9", 0],
        마트: ["MT1", 1],
        약국: ["PM9", 2],
        주유소: ["OL7", 3],
        카페: ["CE7", 4],
        편의점: ["CS2", 5],
      },
      category_code: {
        BK9: 0,
        MT1: 1,
        PM9: 2,
        OL7: 3,
        CE7: 4,
        CS2: 5,
      },
    };
  },
  props: {
    apts: [],
    envs: [],
  },
  mounted() {
    if (!("geolocation" in navigator)) {
      return;
    }

    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");

      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=2696a8065b833ddb1b97c61c86d774eb&libraries=services";
      document.head.appendChild(script);
    }
  },
  methods: {
    ...mapActions(["detailHouse", "detailHouse_like"]),
    initMap() {
      const container = document.getElementById("map");
      const option = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };

      this.map = new kakao.maps.Map(container, option);
      this.ps = new kakao.maps.services.Places(this.map);
      this.geocoder = new kakao.maps.services.Geocoder();
      // this.infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

      // 현재 위치로 이동하는 gps컨트롤
      let gpsControl = document.createElement("img");
      gpsControl.src = require("@/assets/curt-icon.png");
      gpsControl.style.cursor = "pointer";
      gpsControl.onclick = () => {
        this.moveCurPos();
      };
      const zoomControl = new kakao.maps.ZoomControl();
      this.map.addControl(gpsControl, kakao.maps.ControlPosition.RIGHT);
      this.map.addControl(zoomControl, kakao.maps.ControlPosition.BOTTOMRIGHT);
      this.addlikemarker();
      if (this.houses_like.length > 0)
        this.map.setCenter(
          new kakao.maps.LatLng(
            this.houses_like[0].lat,
            this.houses_like[0].lng
          )
        );
    },
    moveCurPos() {
      navigator.geolocation.getCurrentPosition((pos) => {
        let lat = pos.coords.latitude,
          lng = pos.coords.longitude;

        let curPos = new kakao.maps.LatLng(lat, lng);

        this.displayMarker(curPos, "hello", "cur");
        this.map.setCenter(curPos);
      });
    },
    addMakerWithAddress(adresses) {
      this.clearMaker();

      adresses.forEach((adress) => {
        this.geocoder.addressSearch(adress, (result, status) => {
          if (status === kakao.maps.services.Status.OK) {
            let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            this.displayMarker(coords, "", "");
            //this.map.setCenter(coords);
          }
        });
      });
    },
    addMakerWithPosition(positions) {
      this.clearMaker();

      console.log(positions);
      positions.forEach((pos) => {
        let makerPos = new kakao.maps.LatLng(pos.lat, pos.lng);
        this.displayMarker(makerPos, "", "");
        //this.map.setCenter(makerPos);
      });
    },
    clearMaker() {
      console.log("clear maker");
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }
      if (this.infowindows.length > 0) {
        this.infowindows.forEach((infowindow) => infowindow.setMap(null));
      }
    },
    clearCategoryMaker() {
      console.log("clear maker");
      if (this.markers_category.length > 0) {
        this.markers_category.forEach((marker) => marker.setMap(null));
      }
    },
    displayMarker(position, message, type) {
      let makerImage = null;
      if (type === "cur") {
        let imgSrc = require("@/assets/curlocation-icon.png");
        let imgSize = new kakao.maps.Size(28, 28);
        let imgOption = { offset: new kakao.maps.Point(14, 28) };
        makerImage = new kakao.maps.MarkerImage(imgSrc, imgSize, imgOption);
      }

      let maker = new kakao.maps.Marker({
        map: this.map,
        position: position,
        image: makerImage,
      });

      if (message != "") {
        this.infowindows;
        var newinfowindow = new kakao.maps.InfoWindow();
        this.infowindows.push(newinfowindow);
        newinfowindow.setContent(message);
        newinfowindow.open(this.map, maker);
        newinfowindow.close();
        kakao.maps.event.addListener(maker, "click", () => {
          this.infowindows.forEach((infowindow) => infowindow.close());
          newinfowindow.open(this.map, maker);
          this.opensidebar(
            newinfowindow
              .getContent()
              .substring(200, newinfowindow.getContent().length - 7)
          );
          var infoTitle = document.querySelectorAll(".info-title");
          infoTitle.forEach(function (e) {
            var w = e.offsetWidth + 10;
            var ml = w / 2;
            e.parentElement.style.top = "10px";
            e.parentElement.style.left = "50%";
            e.parentElement.style.marginLeft = -ml + "px";
            e.parentElement.previousSibling.style.display = "none";
            e.parentElement.parentElement.style.border = "0px";
            e.parentElement.parentElement.style.background = "unset";
          });
        });
      }
      this.markers.push(maker);
    },
    displayLikeMarker(position, message, type) {
      let makerImage = null;
      if (type === "cur") {
        let imgSrc = require("@/assets/markerStar.png");
        let imgSize = new kakao.maps.Size(28, 32);
        let imgOption = { offset: new kakao.maps.Point(14, 30) };
        makerImage = new kakao.maps.MarkerImage(imgSrc, imgSize, imgOption);
      }
      let maker = new kakao.maps.Marker({
        map: this.map,
        position: position,
        image: makerImage,
      });
      maker.setZIndex(2);
      this.markers_like.push(maker);
      if (message != "") {
        this.infowindows;
        var newinfowindow = new kakao.maps.InfoWindow();
        this.infowindows.push(newinfowindow);
        newinfowindow.setContent(message);
        newinfowindow.open(this.map, maker);
        newinfowindow.close();
        kakao.maps.event.addListener(maker, "click", () => {
          this.infowindows.forEach((infowindow) => infowindow.close());
          newinfowindow.open(this.map, maker);
          this.opensidebar_like(
            newinfowindow
              .getContent()
              .substring(200, newinfowindow.getContent().length - 7)
          );
          var infoTitle = document.querySelectorAll(".info-title");
          infoTitle.forEach(function (e) {
            var w = e.offsetWidth + 10;
            var ml = w / 2;
            e.parentElement.style.top = "10px";
            e.parentElement.style.left = "50%";
            e.parentElement.style.marginLeft = -ml + "px";
            e.parentElement.previousSibling.style.display = "none";
            e.parentElement.parentElement.style.border = "0px";
            e.parentElement.parentElement.style.background = "unset";
          });
        });
      }
    },
    addMarker(position, order) {
      var imageSrc =
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(27, 28), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(46, order * 36), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(11, 28), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imgOptions
        ),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.markers_category.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },

    addHouseMarkers(houses) {
      // this.addMakerWithPosition([
      //   { lat: this.houses_distinct[0].lat, lng: this.houses_distinct[0].lng },
      // ]);
      for (var i in houses) {
        // div style="padding:5px;font-size:12px;"
        let content = `<span class="info-title" style="display: block;
    background: #50627F;
    color: #fff;
    text-align: center;
    height: 24px;
    line-height:22px;
    border-radius:4px;
    padding:0px 10px;">${houses[i].aptname}</span>`;
        this.displayMarker(
          new kakao.maps.LatLng(houses[i].lat, houses[i].lng),
          content,
          "cur"
        );
      }
      var infoTitle = document.querySelectorAll(".info-title");
      infoTitle.forEach(function (e) {
        var w = e.offsetWidth + 10;
        var ml = w / 2;
        e.parentElement.style.top = "10px";
        e.parentElement.style.left = "50%";
        e.parentElement.style.marginLeft = -ml + "px";
        e.parentElement.style.width = w + "px";
        e.parentElement.previousSibling.style.display = "none";
        e.parentElement.parentElement.style.border = "0px";
        e.parentElement.parentElement.style.background = "unset";
      });
    },
    placesSearchCB(data, status, pagination) {
      if (status === kakao.maps.services.Status.OK) {
        pagination;
        for (var i = 0; i < data.length; i++) {
          this.addMarker(
            new kakao.maps.LatLng(data[i].y, data[i].x),
            this.category_code[data[i].category_group_code]
          );
        }
      }
    },
    CategorysSearch(categorys) {
      //카테고리 검색
      this.clearCategoryMaker();
      for (var i in categorys) {
        this.ps.categorySearch(
          this.category[categorys[i]][0],
          this.placesSearchCB,
          {
            useMapBounds: true,
          }
        );
      }
    },
    opensidebar(aptname) {
      for (var i in this.houses_distinct) {
        if (this.houses_distinct[i].aptname == aptname) {
          this.detailHouse(this.houses_distinct[i]);
          break;
        }
      }
      this.$emit("toggle", " ");
    },
    opensidebar_like(aptname) {
      for (var i in this.houses_like) {
        if (this.houses_like[i].aptname == aptname) {
          this.detailHouse_like(this.houses_like[i]);
          break;
        }
      }
      this.$emit("toggle", " ");
    },
    addlikemarker() {
      if (this.markers_like.length > 0) {
        this.markers_like.forEach((marker) => marker.setMap(null));
      }
      for (var i in this.houses_like_distinct) {
        //alert(JSON.stringify(this.houses_like[i]));
        // div style="padding:5px;font-size:12px;"
        let content = `<span class="info-title" style="display: block;
    background: #50627F;
    color: #fff;
    text-align: center;
    height: 24px;
    line-height:22px;
    border-radius:4px;
    padding:0px 10px;">${this.houses_like_distinct[i].aptname}</span>`;
        this.displayLikeMarker(
          new kakao.maps.LatLng(
            this.houses_like_distinct[i].lat,
            this.houses_like_distinct[i].lng
          ),
          content,
          "cur"
        );
      }
      // var infoTitle = document.querySelectorAll(".info-title");
      // infoTitle.forEach(function (e) {
      //   var w = e.offsetWidth + 10;
      //   var ml = w / 2;
      //   e.parentElement.style.top = "10px";
      //   e.parentElement.style.left = "50%";
      //   e.parentElement.style.marginLeft = -ml + "px";
      //   e.parentElement.style.width = w + "px";
      //   e.parentElement.previousSibling.style.display = "none";
      //   e.parentElement.parentElement.style.border = "0px";
      //   e.parentElement.parentElement.style.background = "unset";
      // });
    },
    openSelectedInfowindow(aptname) {
      let name = this.infowindows[i]
        .getContent()
        .substring(200, this.infowindows[i].getContent().length - 7);
      this.infowindows.forEach((infowindow) => infowindow.close());
      for (var i in this.infowindows) {
        if (name == aptname) {
          this.infowindows[i].open(this.map, maker);
          break;
        }
      }
    },
  },
  watch: {
    houses_distinct: function (houses) {
      this.clearMaker();
      this.map.setCenter(new kakao.maps.LatLng(houses[0].lat, houses[0].lng));
      this.addHouseMarkers(houses);
    },
    house: function (house) {
      this.map.setCenter(new kakao.maps.LatLng(house.lat, house.lng));
      this.CategorysSearch(this.checkedCategorys);
      //this.openSelectedInfowindow(house.aptname);
    },
    checkedCategorys(categorys) {
      this.CategorysSearch(categorys);
    },
    houses_like() {
      this.addlikemarker();
      //alert(JSON.stringify(this.house));
      setTimeout(function () {}, 10);
      this.map.setCenter(new kakao.maps.LatLng(this.house.lat, this.house.lng));
    },
  },
  computed: {
    ...mapState([
      "houses",
      "houses_distinct",
      "selected_houses",
      "house",
      "checkedCategorys",
      "houses_like",
      "houses_like_distinct",
    ]),
  },
};
</script>

<style scoped>
.map-container {
  max-width: 1500px;
}
#map {
  margin: auto;
  height: 900px;
  width: 100%;
}
</style>
