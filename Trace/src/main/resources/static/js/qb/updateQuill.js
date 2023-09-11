var options = {
	modules: {
		toolbar: '#toolbar'
	},
	clipboard: {
		matchVisual: false // 일반 텍스트로 변환하지 않도록 설정
	},
	placeholder: '글을 입력하세요',
	theme: 'snow',
	formats: {
		codeBlock: {
			keepStyles: true,
			sanitize: true,
			blacklist: {
				style: true
			}
		}
	}
};
quill = new Quill('#editor', options);
/*quill.on('text-change', function() {
	document.getElementById("quill_html").value = quill.root.innerHTML;
});
*/
$(document).ready(function() {

	var CodeBlock = Quill.import('formats/code-block');

	class StyledCodeBlock extends CodeBlock {
		static create(value) {
			let node = super.create();
			$(node).attr("style", "background-color: #23241f; color: #f8f8f2;"
				+ " margin: 5px 0px; padding: 5px 10px;"
				+ " border-radius: 3px; overflow: visible; white-space: pre-wrap;"
				+ " counter-reset: list-1 list-2 list-3 list-4 list-5 list-6 list-7 list-8 list-9;");
			return node;
		}
	}

	StyledCodeBlock.blotName = "code-block";
	StyledCodeBlock.tagName = "pre";
	Quill.register(StyledCodeBlock, true);
}); 