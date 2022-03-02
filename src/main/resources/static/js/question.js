/**
 * 
 */
"use strict"
/*<![CDATA[*/
let questionsTitle = document.getElementById('questionsTitle');
let questionTitle = document.getElementById('questionTitle');
let questionTextarea = document.getElementById('questionTextarea');
//questionTitle.innerHTML = '<p id="questionTitle" th:text="${dbTitle}"></p>';
console.log(/*[[${dbTitle}]]*/);

//questionTitle.innerText = "abc";
function buttonClick() {
	//questionTitle.innerHTML = "[[${obj.title}]]";
	//questionTitle.innerText = 'abc';
	//questionTextarea.innerHTML = "[[${obj.question}]]";
	//questionTextarea.innerText = "abc";
	console.log(2);
}

questionsTitle.onclick = buttonClick();

/*]]>*/