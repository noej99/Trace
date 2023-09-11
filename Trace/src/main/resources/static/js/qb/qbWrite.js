function submitForm(q) {
	var form = document.querySelector('form');
	
	// Quill 에디터의 HTML 내용 가져오기
	var quillHtml = quill.root.innerHTML;

	if (quillHtml === "<p><br></p>") {
		alert("내용을 입력하세요");
		return false;
	}

	// 내용을 위한 숨겨진 입력 필드 생성 및 값 설정
	var input = document.createElement('input');
	input.setAttribute('type', 'hidden');
	input.setAttribute('name', 'txt');
	input.setAttribute('value', quillHtml); // Quill 내용을 값으로 설정
	
	form.appendChild(input);
	return true; // 폼 제출이 계속 진행됩니다.
}