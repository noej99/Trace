function dataroomWriteCheck(f) {
	var fTitle = f.title;
	var fTxt = f.content;

	if (fTxt.value == '<p><span style="font-family: &quot;맑은 고딕&quot;; font-size: 12px;">﻿</span><br></p>') {
		alert("제목과 내용이 빈 칸인지 확인해주세요");
		return false;
	}

	if (isEmpty(fTitle)) {
		alert("제목과 내용이 빈 칸인지 확인해주세요");
		return false;
	}

	if (!isShort(fTxt, 3500)) {
		alert("내용이 너무 길어 작성할 수 없습니다");
		return false;
	}

	return true;
}