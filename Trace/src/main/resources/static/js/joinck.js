function memberjoinck(f){
	var idField=f.email;
	var pwField=f.pw1;
	var pwckField=f.pwck;
	var nameField=f.name;
	var nickField=f.nick;
	var birthField=f.birth1;
	var addr1Field=f.addr1;
	var addr2Field=f.addr2;
	var addr3Field=f.addr3;
	var id=sessionStorage.getItem("id");
	
	if(checkEmpty(idField)||notContain(idField,"@")||id!=idField.value){
		alert("이메일을 입력해주세요");
		idField.focus();
		return false;
	}
	if(checkEmpty(pwField)||notEquals(pwField,pwckField)||notContain(pwckField,"1234567890")||shortThan(pwField,8)){
		alert("비밀번호를 입력해주세요");
		pwField.value = "";
		pwckField.value = "";
		pwField.focus();
		return false;
	}
	if(checkEmpty(nameField)){
		alert("이름을 입력해주세요");
		nameField.value="";
		nameField.focus();
		return false;
	}
	if(checkEmpty(nickField)){
		alert("닉네임을 입력해주세요");
		nickField.value="";
		nickField.focus();
		return false;
	}
	
	if(checkEmpty(birthField)){
		alert("생일날짜를 기입해주세요");
		return false;
	}
	if(checkEmpty(addr1Field) || checkEmpty(addr2Field) || checkEmpty(addr3Field)){
		alert("주소를 입력해주세요");
		return false;
	}
	
}
function joinEmail(){
	
	$("#joinArea2").css("left","0px");
	$("#joinArea2").css("top","0px");
	$("#joinArea2").css("opacity","1");
}
function reportDv(){
		
	$("#reportDiv").css("left","0px");
	$("#reportDiv").css("top","0px");
	$("#joinArea2").css("opacity","1");
}
	


