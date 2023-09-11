function memberupdateck(f) {
    var pwField = f.pw1;
    var pwckField = f.pwck;
    var nameField = f.name;
    var nickField = f.nick;
    var addr1Field = f.addr1;
    var addr2Field = f.addr2;
    var addr3Field = f.addr3;

    if (checkEmpty(pwField) || notEquals(pwField, pwckField) || notContain(pwckField, "1234567890")||shortThan(pwField,8)) {
        alert("비밀번호를 입력해주세요");
        pwField.value = "";
        pwckField.value = "";
        pwField.focus();
        return false;
    }

    if (checkEmpty(nameField)) {
        alert("이름을 입력해주세요");
        nameField.value = "";
        nameField.focus();
        return false;
    }

    if (checkEmpty(nickField)) {
        alert("닉네임을 입력해주세요");
        nickField.value = "";
        nickField.focus();
        return false;
    }

    if (checkEmpty(addr1Field) || checkEmpty(addr2Field) || checkEmpty(addr3Field)) {
        alert("주소를 입력해주세요");
        return false;
    }

    return true;
}


function memberbye() {
	var really = prompt("탈퇴하시겠습니까? 탈퇴를 입력해주세요");
	if (really == "탈퇴") {
		location.href = "member.delete";

	}
}