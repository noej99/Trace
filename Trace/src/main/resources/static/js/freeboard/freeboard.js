function freeboard(v){
	var word = v.search;
	
	
	if(isEmpty(word)){
		alert("찾으려는 검색어를 입력해주세요!");
		return false;
	}
	
	return true;
}

function writeFreeboard(c){
	var contents = c.contents;
	if(isEmpty(contents)){
		alert("내용을 입력해주세요!");
		return false;
	}
	
	return true;
}

function writereplyandrereply(txt){
	var contents = txt.text;
	if(isEmpty(contents)){
		alert("댓글 내용을 입력해주세요!");
		return false;
	}
	
	return true;
}

function writerereply(re_text){
	var re_text = re_text.re_text;
	if(isEmpty(re_text)){
		alert("대댓글 내용을 입력해주세요!");
		return false;
	}
	
	return true;
}