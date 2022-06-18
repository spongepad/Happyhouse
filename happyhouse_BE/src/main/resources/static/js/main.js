$(document).ready(function () {
  // areaCode : 법정동 코드 5자리
  // map : 지도
  // curtLoca : 현재 위치 좌표
  var areaCode;
  var map;
  var curtLoca = "";

  var city;

  // 현재 페이지 이름을 가져옴
  var path = window.location.pathname;
  var page = path.split("/").pop();
  
  if (page == "") {
    initMap();
  }

  // 시/도 선택할 때
  $("#do li > a").on("click", function () {
    // city : 도시이름
    city = $(this).text();

    // 선택 했을 때
    $("#btnGroupDrop1").text(city);
    $('#hide_btn1').val(city);
    
    $("#btnGroupDrop2").text("시/구/군");
    $("#btnGroupDrop3").text("동");
    $("#gu").empty().append(`<li><a class="dropdown-item" href="#">시/구/군</a></li>`);
    // ajax로 행정구역 json얻어와서
    // 시/도 별로 행정구역 생성해서 시/구/군에 추가
    $.ajax({
      url: "./data/행정구역.json",
      dataType: "json",
      type: "get",
      success: function (json) {
        // 시/구/군 추가함수
        makeGuList(json, city);
        if (page == "") {
          addressToGeo(city, 12);
        }
      },
      error: function (xhr, status, msg) {
        console.log("상태값 : " + status + " Http에러메시지 : " + msg);
      },
    });
  });
  
  var onSuccessGeolocation = function(position) {
	curtLoca = new naver.maps.LatLng(position.coords.latitude, position.coords.longitude);
  }
  
  var onErrorGeolocation = function() {
	console.log("현재 위치를 가져오는데 실패하였습니다.");
  }
  
  if(navigator.geolocation) {
	  navigator.geolocation.getCurrentPosition(onSuccessGeolocation, onErrorGeolocation);
  } else{
	  console.log("Geolocation Not supported Required");
  }

  // 시/구/군 추가함수
  function makeGuList(json, city) {
    let guList;
    // json에서 일치하는 시/도의 시/구/군 받음
    for (var i = 0; i < json.length; i++) {
      if (json[i].area == city) {
        guList = json[i].gu;
        break;
      }
    }
    let guMenu = $("#gu");
    guMenu.empty().append(`<li><a class="dropdown-item" href="#">시/구/군</a></li>`);

    // 받은 시/구/군 추가
    for (let gu of guList) {
      let item = `<li><a class="dropdown-item" href="#">${gu}</a></li>`;
      guMenu.append(item);
    }

    // 추가한 시/구/군에 click이벤트 정의
    guMenu.find("li > a").on("click", function () {
      guItemEvent($(this));
      if (page == "") {
        addressToGeo(city + " " + $(this).text(), 15);
      }
    });
  }

  // 선택한 시/구/군의 동정보 받아서 추가하는 함수
  function guItemEvent(data) {
    $("#btnGroupDrop2").text(data.text());
    $('#hide_btn2').val(data.text());
    
    $("#btnGroupDrop3").text("동");
    $("#dong").empty().append(`<li><a class="dropdown-item" href="#">동</a></li>`);
    let gu = $("#btnGroupDrop2").text();
    $.ajax({
      url: "./data/법정동코드.json",
      dataType: "json",
      type: "get",
      success: function (json) {
        let dongList = [];
        for (var i = 0; i < json.length; i++) {
          if (json[i].city == city && json[i].gu == gu && json[i].dong != "") {
            dongList.push(json[i].dong);
            areaCode = json[i].code.toString().substring(0, 5);
          }
        }

        // 중복원소 제거
        dongList = new Set(dongList);

        let dongMenu = $("#dong");
        dongMenu.empty().append(`<li><a class="dropdown-item" href="#">동</a></li>`);

        for (let dong of dongList) {
          let item = `<li><a class="dropdown-item" href="#">${dong}</a></li>`;
          dongMenu.append(item);
        }
        dongMenu.find("li > a").on("click", function () {
          $("#btnGroupDrop3").text($(this).text());
          $('#hide_btn3').val($(this).text());
          if (page == "") {
            addressToGeo(city + " " + gu + " " + $(this).text(), 17);
          }
        });
      },
      error: function (xhr, status, msg) {
        console.log("상태값 : " + status + " Http에러메시지 : " + msg);
      },
    });
  }

  // 맵 불러오기
  function initMap() {
	  
	// 현재위치 이동 이미지
	let curtBtn = '<img src="/assets/img/curt-icon.png" alt="현재 위치로 이동">';
	
	  
    map = new naver.maps.Map(document.getElementById("map"), {
      center: new naver.maps.LatLng(37.3595704, 127.105399),
      zoom: 15,
      scaleControl: false,
      mapDataControl: true,
      mapTypeControl: true,
      zoomControl: true,
      zoomControlOptions: {
        style: naver.maps.ZoomControlStyle.SMALL,
        position: naver.maps.Position.RIGHT_BOTTOM,
      },
    });
    
    naver.maps.Event.once(map, 'init_stylemap', function() {
		let cstmCtrl = new naver.maps.CustomControl(curtBtn, {
			position: naver.maps.Position.RIGHT_BOTTOM
		});
		
		cstmCtrl.setMap(map);
		
		naver.maps.Event.addDOMListener(cstmCtrl.getElement(), 'click', function() {
			if(curtLoca){
				map.setCenter(curtLoca);
			} else {
				alert("위치 액세스가 거부 되었습니다.");
			}
		})
	});
  }

  function addressToGeo(data, zoomLevel) {
    naver.maps.Service.geocode({ query: data }, function (status, response) {
      if (status === naver.maps.Service.Status.ERROR) {
        return alert("Something wrong!");
      }

      // 성공 시의 response 처리
      var result = response.v2, // 검색 결과의 컨테이너
        items = result.addresses;

      let x = parseFloat(items[0].x);
      let y = parseFloat(items[0].y);

      map.setCenter(new naver.maps.LatLng(y, x));
      map.setZoom(zoomLevel);
    });
  }
});
