function deleteReply(rno, rrNo) {
	if (confirm("정말 삭제하시겠습니까?")) {
		$.ajax({
			type: "post",
			url: "/dataroom.deleteReply",
			data: { no: rno, rNo: rrNo },
			success: function() {
				alert("댓글을 삭제했습니다");
				location.href = "/dataroom.view?no=" + rno;
			}
		});
	}
}

function deleteReReply(rno, rrrNo) {
	if (confirm("정말 삭제하시겠습니까?")) {
		$.ajax({
			type: "post",
			url: "/dataroom.deleteReReply",
			data: { no: rno, rrNo: rrrNo },
			success: function() {
				alert("댓글을 삭제했습니다");
				location.href = "/dataroom.view?no=" + rno;
			}
		});
	}
}

function reReply(td, drNo, drrNo, id, token) {
	var row = $(td).closest('tr').next('.new-row');
	if (row.length) {
		row.remove();
	} else {
		var newRow = $('<tr class="new-row">'
			+ '<td></td><td class="drReReplyWirte" colspan="10">'
			+ '<form action="dataroom.reReply" method="post">'
			+ '<input name="txt">&nbsp;'
			+ '<button>쓰기</button>'
			+ '<input name="drrrDr" value="' + drNo + '" hidden="hidden">'
			+ '<input name="drrrDrr" value="' + drrNo + '" hidden="hidden">'
			+ '<input name="drrrMw" value="' + id + '" hidden="hidden">'
			+ '<input name="drNo" value="' + drNo + '" hidden="hidden">'
			+ '<input name="token" value="' + token + '" hidden="hidden">'
			+ '</form></td></tr>');
		$(td).closest('tr').after(newRow);
	}
}