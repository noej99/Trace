function dataroomLike(drNo, writer) {
	$.ajax({
		data: { no: drNo, drMw: writer },
		type: 'POST',
		url: 'dataroom.like',
		success: function(e) {
			alert(e.result);
			location.href = "/dataroom.view?no=" + drNo;
		},
	})
}

function dataroomDelete(drNo) {
	if (confirm("정말 삭제하시겠습니까?")) {
		$.ajax({
			type: "post",
			url: "/dataroom.delete",
			data: { no: drNo },
			success: function() {
				alert("게시글을 삭제했습니다");
				location.href = "/dataroom";
			}
		});
	}
}