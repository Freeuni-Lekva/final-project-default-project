function addQuestion(btn) {
	var cansw = 0;
	var wansw = 0;
	var ordered = "0";
	var quizid = btn.id
	if(btn.name === "MC") {
		while(true) {
			wansw = window.prompt("Wrong answer count");
			if(isNaN(wansw) === false && wansw > 0)
				break;
			alert("Please enter a valid number");
		}
	} else if(btn.name === "MCA") {
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
	} else if(btn.name === "MA") {
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
	} else if(btn.name === "M") {
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