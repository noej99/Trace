function connectMemberNickCheckEvent() {
    var nicknameChecked = false;

    function checkDuplicateNickname(nick) {
        var nicknameExists = false;
        
        $.ajax({
            url: "member.get.json",
            data: { nick: nick },
            async: false, 
            success: function(memberData) {
                console.log("AJAX success:", memberData);

                for (var i = 0; i < memberData.members.length; i++) {
                    if (memberData.members[i].nick === nick) {
                        alert("중복된 닉네임입니다.");
                        nicknameExists = true;
                        break;
                    }
                }
            },
            error: function(xhr, status, error) {
                console.log("AJAX Error:", status, error);
            }
        });

        return nicknameExists;
    }

    $("#duplicateCheckButton").click(function() {
        var nick = $("#memberjoinNick").val().trim();
        if (nick === "") {
            $("#nicknameAlert").text("닉네임을 입력해주세요");
            return;
        }

        $.ajax({
            url: "member.get.json",
            data: { nick: nick },
            success: function(memberData) {
                console.log("AJAX success:", memberData);

                var nicknameExists = false;
                for (var i = 0; i < memberData.members.length; i++) {
                    if (memberData.members[i].nick === nick) {
                        alert("중복된 닉네임입니다.");
                        nicknameExists = true;
                        break;
                    }
                }

                var alertMessage = nicknameExists ? "중복된 닉네임 입니다." : "사용할 수있는 닉네임입니다.";
                console.log(alertMessage);
                $("#nicknameAlert").text(alertMessage);

                nicknameChecked = !nicknameExists;
                $("#signupButton").prop("disabled", nicknameExists);
            },
            error: function(xhr, status, error) {
                console.log("AJAX Error:", status, error);
            }
        });
    });

    $("#memberjoinNick").on("input", function() {
        if (nicknameChecked) {
            var nick = $(this).val().trim();
            var nicknameExists = checkDuplicateNickname(nick);

            $("#signupButton").prop("disabled", nicknameExists);
        }
    });
}















function connectSummonJoinControlAddrSearchevent() {

	$("#addr1,#addr2").click(function() {
		new daum.Postcode({
			oncomplete: function(data) {

				$("#addr1").val(data.zonecode);
				$("#addr2").val(data.address);
			}
		}).open();

	});

}






function getverifyMailEvent() {
	sessionStorage.removeItem("au");
	$("#mailCheckbtn").click(function() {
		var name = document.querySelector('#username').value;
		var freeFix = document.querySelector('#freefix').value;
		var email = name + freeFix;
		var user = {
			username: email
		};

		$.ajax({
			url: "api.mailcheck",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify(user),
			success: function(authNum) {
				if (authNum !== null) {
					alert("이메일이 전송됐습니다.");
					console.log(authNum);
					sessionStorage.setItem("id",email);
					sessionStorage.setItem("au", authNum);
					$('#mail-check-input').removeAttr('disabled');
				} else {
					alert("이메일 인증코드 전송 실패");
				}
			},

			error: function() {
				alert("이메일 전송 실패");
			}
		});
	});


}
function checkAuthNumAndInputNum() {
	$("#checkVerify-btn").click(function() {
		var a = sessionStorage.getItem('au');
		var inputCode = document.querySelector("#mail-check-input").value;
		var id=sessionStorage.getItem("id");
		var name = document.querySelector('#username').value;
		var freeFix = document.querySelector('#freefix').value;
		var inputId= name+freeFix;
		if (a !== inputCode || id!=inputId) {
			alert("인증코드가 일치하지 않습니다.");
			return false;
		} else {
			var user = document.querySelector("#username").value;
			var fix = document.querySelector("#freefix").value;
			alert("인증되었습니다.");
			$("#email").val(user + fix);
			$("#joinArea2").css("opacity", 0);
			setTimeout(function() {
				$("#joinArea2").css("left", "-100%");
				$("#joinArea2").css("top", "-100%");
			}, 300);
		}
	});
	sessionStorage.removeItem("au");
	return $("#email").val(user + fix);
}







function connectSummonJoinEmailInputEvent() {
	$("#x").click(function() {
		$("#joinArea2").css("opacity", 0);
		setTimeout(function() {
			$("#joinArea2").css("left", "-100%");
			$("#joinArea2").css("top", "-100%");
		}, 300);
	});

}










$(function() {
	connectMemberNickCheckEvent();
	connectSummonJoinEmailInputEvent();
	getverifyMailEvent();
	connectSummonJoinControlAddrSearchevent();
	checkAuthNumAndInputNum();

});



