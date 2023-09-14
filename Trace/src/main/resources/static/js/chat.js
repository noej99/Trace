$(function() {
	
	
	
	
	
    var socket = io.connect("http://195.168.9.95:1234");
    var n;
    $.ajax({
        url: "member.get.json2",
        async: false,
        success: function(memberData) {
            n = memberData.members[0].nick;
        },
        error: function(error) {
            console.error("Error fetching 'nick' from the server: " + error);
        }
    });
    
    
    $("#msg").keyup(function(e) {
        if (e.keyCode == 13) {
            var m = $(this).val();
            var msg = {
                nn: n,
                txt: m
            };
            socket.emit("clientMsg", msg);
            $(this).val("");
        }
    });


    socket.on("serverMsg", function(m) {
        var br = $("<br>");
        var br2 = $("<br>");
        var who = "[" + m.nn + "]";
        var td1 = $("<td></td>").append(who, br, br2, m.txt);
        var td2 = $("<td></td>").attr("style", "width:20%;");
        var td3 = $("<td></td>").attr("style", "width:40%;");
        var tr;

        if (n == m.nn) {
            td1 = $(td1).attr("class", "myMsg");
            tr = $("<tr></tr>").append(td3, td2, td1);
        } else {
            td1 = $(td1).attr("class", "yourMsg");
            tr = $("<tr></tr>").append(td1, td2, td3);
        }

        $("table.chatboard").append(tr, $("<tr><td><p></td></tr>"));
        
        var chatMessages = document.getElementById("chatMessages");
        chatMessages.scrollTop = chatMessages.scrollHeight;
    });
});
