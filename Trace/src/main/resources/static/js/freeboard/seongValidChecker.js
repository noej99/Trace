// 웹 유효성 검사 라이브러리
// 최대한 다양한 상황에서 쓸 수 있게
// 긍정/ 부정 컨셉을 통일

// input을 넣었을때
// 안썼으면 true
function isEmpty(input){ //칸이 비어있는지 검사. 없으면 true 반환
	return !input.value;
}

// input, 글자수를 넣었을 때
// 짧으면 true -> true면 조건에 걸리게 컨셉 통일

function isShort(input, len){
	return input.value.length < len; //입력한 값이 설정 길이보다 짧으면 true 반환
}

function isLong(input, len){
	return input.value.length > len; //입력한 값이 설정 길이보다 길면 true 반환
}

// input 넣었을때
// 한글/특수문자가 있으면 true, 없으면 false
// 다만 -_@.는 예외
function containsHSChar(input){
	var acChar = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM123456789-_@."; //허용되는 문자들

	for(var i = 0;i<input.value.length;i++){
			if(acChar.indexOf(input.value[i])==-1){ 
				//입력한 값들 하나하나가 허용되는 문자들에 포함이 되있는지 아닌지
				//만약 입력한 문자열 중 문자 하나가 포함되어있지 않다면(-1) return true;
				return true;
		}
	}
	return false;
}

//비밀번호 확인할때 다르면 true, 같으면 false;
function equalPw(input, checkInput){
	return input.value != checkInput.value;
}

//비번에 숫자, 특수문자, 대문자 하나 이상 등등 조건
// input, 문자열 세트 넣었을때
// 안들어있으면 true, 들어있으면 false
// 함수명(pwField, "체크할문자(숫자, 특수문자)세트")
function chkSet(input, set){ 
	for(var i =0;i<set.length;i++){
		if(input.value.indexOf(set[i])!=-1){ // 사용자가 있는지 확인할 문자열 세트 중 하나라도 input에 있다면
			return false; 
		}
	}
	return true;
	
}

//나이 input에 숫자가 아니면 true, 숫자면 false
// 단순 공백 값도 체크한다
function isNotNum(input){
	return input.value.indexOf(" ")!=-1 || isNaN(input.value);
}

// 사진 올리기. 그림 확장자가 아닌 것들 걸러내기
// 1) 확장자 : 원래 의미 없는거
// 2) 
function isNotType(input, type){
	type = "."+type;
	return input.value.toLowerCase().indexOf(type)==-1;
	
}

function findOut(input, char){ // 특정 문자를 못쓰게하기
	for(var i =0;i<input.value.length;i++){
			if(input.value.indexOf(char)!=-1){ // 검출되면
				return true;
			}
		}
		return true;
}





