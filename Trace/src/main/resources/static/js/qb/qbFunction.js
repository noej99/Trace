function qbSearch() {
	var search = document.getElementById('name').value;
	location.href = "qb.go?search=" + search;
}

function deleteReply(replyId, qbNo) {
	$.ajax({
		type: "DELETE", // HTTP 메서드를 DELETE로 설정
		url: "/qb/reply/" + replyId,
		data: { qbNo: qbNo },
		success: function(response) {
			// 삭제 성공 시
			alert(response); // 댓글 삭제 성공 메시지 출력
			$("#reply-" + replyId).remove();
			// 필요한 추가 처리 작업 수행
			location.href = "qb.detail?no=" + qbNo;
		},
		error: function(xhr, status, error) {
			// 삭제 실패 시
			alert("댓글 삭제에 실패했습니다.");
			console.log(xhr.responseText);
		}
	});
}

function deleteReReply(rereplyId, qbNo) {
	$.ajax({
		type: "DELETE", // HTTP 메서드를 DELETE로 설정
		url: "/qb/rereply/" + rereplyId,
		data: { qbNo: qbNo },
		success: function(response) {
			// 삭제 성공 시
			alert(response); // 댓글 삭제 성공 메시지 출력
			$("#rereply-" + rereplyId).remove();
			// 필요한 추가 처리 작업 수행
			location.href = "qb.detail?no=" + qbNo;
		},
		error: function(xhr, status, error) {
			// 삭제 실패 시
			alert("댓글 삭제에 실패했습니다.");
			console.log(xhr.responseText);
		}
	});
}

function updateLike(qbNo) {
	$.ajax({
		type: "POST",
		url: "/update.like",
		data: { qbNo: qbNo },
		success: function(response) {
			location.href = "qb.detail?no=" + qbNo;
		},
		error: function(xhr, status, error) {
			// 에러 처리
		}
	});
}

// 모든 copyButton 클래스를 가진 버튼 요소에 클릭 이벤트 리스너를 추가
const copyButtons = document.querySelectorAll(".copyButton");
copyButtons.forEach(button => {
    button.addEventListener("click", function() {
        // 클릭된 버튼의 부모 요소 안에서 qbrno input 요소의 값을 가져옴
        const qbrnoValue = this.parentElement.querySelector(".qbrno").value;

        // 바깥에 있는 qbrqrno input 요소에 값을 설정
        const qbrqrnoInput = document.getElementById("qbrqrno");
        qbrqrnoInput.value = qbrnoValue;
        
    });
});




