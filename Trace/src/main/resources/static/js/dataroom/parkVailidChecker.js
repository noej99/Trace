// 웹 유효성검사 라이브러리
//		최대한 다양한 상황에서 쓸 수 있게
//		긍정적(잘하면 true)/부정적(잘못하면 true) 컨셉통일

// input을 넣었을 때
// 안썼으면 true, 썼으면 false <- 부정적

function isEmpty(input) {
	return !input.value;
}

// input, 글자수 넣었을 때
// 짧으면 true, 길면 false
function isShort(input, len) {
	return input.value.length < len;
}

// 특수문자
// 있으면 true, 없으면 false
function hasHS(input) {
	var hs = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-_.@";
	for (var i = 0; i < input.value.length; i++) {
		if (hs.indexOf(input.value[i]) == -1) {
			console.log("특수문자 포함");
			return true;
		}
	}
}

// input 2개 넣었을때
// 입력한게 다르면 true 같으면 false
function isEqual(input1, input2) {
	return input1.value != input2.value;
}

// 문자열세트 넣었을때
// 그거 없으면 true, 있으면 false
function notHas(input, set) {
	for (var i = 0; i < input.value.length; i++) {
		if (set.indexOf(input.value[i]) != -1) {
			return false;
		}
	}
	return true;
}
// input넣고
// 숫자 아니면 true, 숫자면 false
function isNotNum(input) {
	return input.value.indexOf(" ") != -1 || isNaN(input.value);
}

// input, 확장자 넣고
// 해당확장자 아니면 true
function isNotType(input, type) {
	type = "." + type;
	return input.value.toLowerCase().indexOf(type) == -1;
}

function isPhotoType(input) {
	var type = [".png", ".gif", ".jpg", "webp"];
	for(var i = 0; i < 3; i++){
		if(input.value.toLowerCase().indexOf(type[i]) != -1) {
			return false;
		}
	}
	return true;
}

function isPhoto(name) {
	var type = [".png", ".gif", ".jpg", "webp"];
	for(var i = 0; i < 3; i++){
		if(name.toLowerCase().indexOf(type[i]) != -1) {
			return false;
		}
	}
	return true;
}