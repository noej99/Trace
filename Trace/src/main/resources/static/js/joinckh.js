
function checkEmpty(input) {
	return !input.value;
}

function shortThan(input, len) {
	return input.value.length < len;
}

function containsHS(input) {
	var hs = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM-_@.";
	for (var i = 0; i < input.value.length; i++) {
		if (hs.indexOf(input.value[i]) == -1) {
			return true;

		}
	}
	return false;

}

function notEquals(input1, input2) {
	return input1.value != input2.value;
}
function notContain(input, set) {
	for (var i = 0; i <= set.length; i++) {
		if (input.value.indexOf(set[i])!=-1){
			return false;

		}
	}
	return true;
}

function isNotNum(input){
	return input.value.indexOf(" ")!=-1||isNaN(input.value);
	
}

function isNotType(input,type){
	type="."+type;
	return input.value.toLowerCase().indexOf(type)==-1;
		
}