$(function () {
  $("#nav-item-login").on("click", function () {
	window.location.href = "/user/login";
  });

  $("#nav-item-logout").on("click", function () {
	  window.location.href = "/user/logout";
  });

  $("#nav-item-signup").on("click", function () {
    window.location.href = "/user/signup";
  });

  $("#nav-item-userinfo").on("click", function () {
    window.location.href = "/user/userinfo";
  });

  $("#notices").on("click", function () {
    window.location.href = "/notice/list?pg=1&word=";
  });

  $("#setInterestArea").on("click", function () {
    window.location.href = "/like";
  });

  $("#exploreInterestArea").on("click", function () {
    window.location.href = "/likelist";
  });

  $("#aptsearchBtn").on("click", function () {
    window.location.href = "/apt";
  });
});


