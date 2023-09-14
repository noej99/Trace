function updateNoticeLike(no) {
	$.ajax({
		type: "POST",
		url: "/notice.like",
		data: { no: no },
		success: function(response) {
			location.href = "notice.get.detail?no=" + no;
		},
		error: function(xhr, status, error) {
			// 에러 처리
		}
	});
}