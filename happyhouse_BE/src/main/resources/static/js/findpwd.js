$(function () {
  $("#submitButton").on("click", function () {
    let userid = $("#userid").val();
    let name = $("#username").val();
    let tel = $("#tel").val();

    if (!userid || !name || !tel) {
      alert("빈 칸을 모두 입력하세요.");
      return;
    }
    $("#contactForm").attr("action", "/user/findpwd").submit();
  });
});
