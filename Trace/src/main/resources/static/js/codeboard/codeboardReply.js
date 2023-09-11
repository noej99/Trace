function deleteReply(replyId, no) {
    if (confirm("정말 삭제하시겠습니까?")) {
        $.ajax({
            type: "DELETE",
            url: "/codeboard/deleteReply/" + replyId,
            data: { no: no },
            success: function(response) {
                alert(response);
                $("#reply-" + replyId).remove();
                location.href = "codeboard.get.detail?no=" + no;
            },
            error: function(xhr, status, error) {
                alert("댓글 삭제에 실패했습니다.");
                console.log(xhr.responseText);
            }
        });
    }
}

