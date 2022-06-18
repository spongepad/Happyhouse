$(document).ready(function () {

    // areaCode : 법정동 코드 5자리
    // aptDict : 아파트 중복을 저장하는 사전형식 데이터
    // map : 지도
    var areaCode;
    var aptDict;

    // 현재 페이지 이름을 가져옴
    var path = window.location.pathname;
    var page = path.split("/").pop();

    // 시/도 선택할 때
    $('#do li > a').on('click', function () {
        // city : 도시이름
        let city = $(this).text();
        
        // 선택 했을 때
        $('#btnGroupDrop1').text(city);
        $('#hide_btn1').val(city);
        
        $('#btnGroupDrop2').text('시/구/군');
        $('#btnGroupDrop3').text('동');
        $('#gu').empty().append(`<li><a class="dropdown-item" href="#">시/구/군</a></li>`);
        // ajax로 행정구역 json얻어와서 
        // 시/도 별로 행정구역 생성해서 시/구/군에 추가
        $.ajax({
            url: "/data/행정구역.json",
            dataType: "json",
            type: "get",
            success: function (json) {
                // 시/구/군 추가함수
                makeGuList(json, city);
            },
            error: function (xhr, status, msg) {
                console.log('상태값 : ' + status + ' Http에러메시지 : ' + msg);
            }
        });
    });

    // 시/구/군 추가함수
    function makeGuList(json, city) {
        let guList;
        // json에서 일치하는 시/도의 시/구/군 받음
        for (var i = 0; i < json.length; i++){
            if (json[i].area == city) {
                guList = json[i].gu;
                break;
            }
        }
        let guMenu = $('#gu');
        guMenu.empty().append(`<li><a class="dropdown-item" href="#">시/구/군</a></li>`);
        
        // 받은 시/구/군 추가
        for (let gu of guList) {
            let item = `<li><a class="dropdown-item" href="#">${gu}</a></li>`
            guMenu.append(item);
        }

        // 추가한 시/구/군에 click이벤트 정의
        guMenu.find('li > a').on('click', function () {
            guItemEvent($(this));
        });
    }
    
    // 선택한 시/구/군의 동정보 받아서 추가하는 함수
    function guItemEvent(data) {
        $('#btnGroupDrop2').text(data.text());
        $('#hide_btn2').val(data.text());
        
        $('#btnGroupDrop3').text('동');
        $('#dong').empty().append(`<li><a class="dropdown-item" href="#">동</a></li>`);
        let city = $('#btnGroupDrop1').text();
        let gu = $('#btnGroupDrop2').text();
        $.ajax({
            url: "/data/법정동코드.json",
            dataType: "json",
            type: "get",
            success: function (json) {
                let dongList = [];
                for (var i = 0; i < json.length; i++){
                    if (json[i].city == city && json[i].gu == gu && json[i].dong != "") {
                        dongList.push(json[i].dong)
                        areaCode = json[i].code.toString().substring(0, 5);
                    }
                }

                // 중복원소 제거
                dongList = new Set(dongList);

                let dongMenu = $('#dong');
                dongMenu.empty().append(`<li><a class="dropdown-item" href="#">동</a></li>`);

                for (let dong of dongList) {
                    let item = `<li><a class="dropdown-item" href="#">${dong}</a></li>`
                    dongMenu.append(item);
                }
                dongMenu.find('li > a').on('click', function () {
                    $('#btnGroupDrop3').text($(this).text());
                    $('#hide_btn3').val($(this).text());
                });
            },
            error: function (xhr, status, msg) {
                console.log('상태값 : ' + status + ' Http에러메시지 : ' + msg);
            }
        });
    }

    // 아파트 정보 리스트에서 아파트 이름클릭 이벤트 추가 함수
    function addAptOnClick() {
        $('#aptinfo ul > li:first-child').on('click', function () {
            $('#aptinfo').empty();
            $("#backBtn").css("display", "inline-block");
            $("#info-apt-name").css("display", "inline-block");

            for (let apt of aptDict[$(this).text()]) {
                $('#aptinfo').prepend(apt.replace(`<li name="aptName">${$(this).text()}</li>`, ``));
            }
            $('#info-apt-name').text(`${$(this).text()}`);
        })
    }
});