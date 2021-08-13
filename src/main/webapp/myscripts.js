function requestFunc() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			document.getElementById("content").innerHTML = xhttp.responseText;
		}
	};
	xhttp.open("GET", "FriendRequests", true)
	xhttp.send()
}

function searchFunc() {
	var name = document.getElementById("mySearch").value;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			document.getElementById("content").innerHTML = xhttp.responseText;
		}
	};
	xhttp.open("GET", "Search?searching=" + name, true)
	xhttp.send(name)
}

function friendFunc() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			document.getElementById("content").innerHTML = xhttp.responseText;
		}
	};
	xhttp.open("GET", "FriendList", true)
	xhttp.send()
}

function noteFunc() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			document.getElementById("content").innerHTML = xhttp.responseText;
		}
	};
	xhttp.open("GET", "Notes", true)
	xhttp.send()
}

function challengesFunc() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			document.getElementById("content").innerHTML = xhttp.responseText;
		}
	};
	xhttp.open("GET", "MyChallenges", true)
	xhttp.send()
}

function sendNote() {
	var person = window.prompt("Sending to..");
	var note = window.prompt("Note");
	if (person != null && note != null) {
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4 && xhttp.status == 200) {
				document.getElementById("content").innerHTML = xhttp.responseText;
			}
		};
		xhttp.open("POST", "CreateNote?user="+person + "&note=" + note, true)
		xhttp.send()
	}
}

function addQuestion(btn) {
	var cansw = 0;
	var wansw = 0;
	var ordered = "0";
	var quizid = btn.id
	if(btn.name === "2") {
		while(true) {
			wansw = window.prompt("Wrong answer count");
			if(isNaN(wansw) === false && wansw > 0)
				break;
			alert("Please enter a valid number");
		}
	} else if(btn.name === "3") {
		while(true) {
			cansw = window.prompt("Correct answer count");
			if(isNaN(cansw) === false && cansw > 0)
				break;
			alert("Please enter a valid number");
		}
		while (true) {
			wansw = window.prompt("Wrong answer count");
			if(isNaN(wansw) === false && wansw > 0)
				break;
			alert("Please enter a valid number");
		}
	} else if(btn.name === "4") {
		while(true) {
			cansw = window.prompt("Correct answer count");
			if(isNaN(cansw) === false && cansw > 0)
				break;
			alert("Please enter a valid number");
		}
		while(true) {
			ordered = window.prompt("If answers are ORDERED - write 1 otherwise - 0");
			if(ordered === "1" || ordered === "0")
				break;
			alert("Please enter a valid number");
		}
	} else if(btn.name === "6") {
		while(true) {
			cansw = window.prompt("Couples count");
			if(isNaN(cansw) === false){
				if(isInt(cansw) === true)
					break;
			} else {
				alert("Please enter a valid number");
			}
		}
	}

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var qst = document.getElementById("content")
			qst.innerHTML = xhttp.responseText;
		}
	};
	xhttp.open("GET", "QuestionForm?quizid=" + quizid + "&type=" + btn.name + "&cansc=" + cansw + "&wansc=" + wansw + "&ord=" + ordered, true)
	xhttp.send()
}

function isInt(n) {
	return n % 1 === 0;
}

function displayQuestions(arg) {
	var quizid = arg;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var qst = document.getElementById("questions")
			qst.innerHTML = xhttp.responseText;
		}
	};
	xhttp.open("GET", "DisplayQuestions?quizid=" + quizid, true)
	xhttp.send()
}

function editQuestion(obj) {
	var questid = obj.id
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var qst = document.getElementById("questions")
			qst.innerHTML = xhttp.responseText;
		}
	};

	xhttp.open("GET", "DisplayQuestions?questid=" +questid, true)
	// unda gadaikvanos im gverdze sadac question ketdeba, + show whole quiz button
	xhttp.send()
}

function categoryQuizes(obj) {
	var cat = obj.name
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var qst = document.getElementById("content")
			qst.innerHTML = xhttp.responseText;
		}
	};

	xhttp.open("GET", "GetByCategory?cat=" +cat, true)
	xhttp.send()
}

function friendList(obj) {
	var user = obj.name
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var qst = document.getElementById("content")
			qst.innerHTML = xhttp.responseText;
		}
	};

	xhttp.open("GET", "UsersFriends?profile=" + user, true)
	xhttp.send()
}

function showQuiz(obj) {
	var quizid = obj.name
	console.log(obj);
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var qst = document.getElementById("content")
			qst.innerHTML = xhttp.responseText;
		}
	};

	xhttp.open("GET", "StartQuiz?quizid=" + quizid, true)
	xhttp.send()
}

function multiPageQuiz(obj) {
	var quizid = obj.name;
	console.log("obj: " + obj);

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var qst = document.getElementById("content")
			qst.innerHTML = xhttp.responseText;
		}
	};

	xhttp.open("GET", "StartMultiPageQuiz?quizid=" + quizid, true);
	xhttp.send()
}

function challengeUser(obj) {
	while(true) {
		var person = window.prompt("Challenge..");
		if (person != null && person != "")
			break;
	}

	var quizid = obj.name;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			window.location = "http://localhost:8080/final_project_war_exploded/myHistory.jsp?challenged="+xhttp.responseText;
		}
	};

	xhttp.open("POST", "Challenge?user="+person + "&quizid=" + quizid, true)
	xhttp.send()
}

function multiPageAdvance() {
	var quizid = $("#quizid").val();
	var question = $("#question").val();
	var text = $("#callForm").serialize();
	console.log("StartMultiPageQuiz?" + text);
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var qst = document.getElementById("content")
			qst.innerHTML = xhttp.responseText;
		}
	};

	xhttp.open("POST", "StartMultiPageQuiz?" + text, true);
	xhttp.send();
}

function multiPageFinish() {
	var quizid = $("#quizid").val();
	var question = $("#question").val();
	var text = $("#callForm").serialize();
	console.log("StartMultiPageQuiz?" + text);
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var qst = document.getElementById("content")
			$("html").html(xhttp.responseText);
		}
	};

	xhttp.open("POST", "StartMultiPageQuiz?" + text, true);
	xhttp.send();
}