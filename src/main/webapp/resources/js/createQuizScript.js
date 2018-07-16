/**
 * @author Alessandro De Simone
 */

var lastQuestionID = 1;

var answer = '<div class="answer" id="q1a1"> \
				<div class="form-group"> \
					<label for=""></label> \
					<div class="input-group"> \
						<span class="input-group-addon"> \
							<i class="glyphicon glyphicon-chevron-right"></i> \
						</span> \
						<input type="text" class="form-control input-sm" name="answer" id="" placeholder="Inserisci la risposta" required> \
					</div> \
				</div> \
			</div>';
var newAnswer = '<div class="answer" id="q1a1"> \
					<div class="row">\
						<div class="form-check col-md-1"> \
							<input class="form-check-input correctAnswer" type="radio" name="" id="" value="" required> \
							<label class="form-check-label" for=""></label> \
						</div> \
						<div class="form-group col-md-11"> \
							<label for=""></label> \
							<div class="input-group"> \
								<span class="input-group-addon"> \
									<i class="glyphicon glyphicon-chevron-right"></i> \
								</span> \
								<input type="text" class="form-control input-sm" name="answer" id="" placeholder="Inserisci la risposta" required> \
							</div> \
						</div> \
					</div> \
				</div>';

function init()
{
//	$('input[name="question1_type"]').on('change', toggleForm);

	$('#newQuestionBtn').click(addQuestion);
	
	$.ajax(
	{
		type : "GET", url : "createProblem?req=contests", datatype : "json",
		success : function(data)
		{
			$('#nQuiz_contest').html("");
			data = $.parseJSON(data);
			var contests = [];
			$.each(data, function(key, val)
			{
				contests.push('<option id="' + val + '">' + val + '</option>');
			})
			$('#nQuiz_contest').append(contests);
		}
	});
	
	$('#quizForm').submit(function(e)
	{
	    e.preventDefault();
	    
	    // Manage input data to send
	    var quiz = $(".quizQuestions");
	    
	    var contestId = $("#nQuiz_contest").val();
	    var quizName = $("#nQuiz_name").val();
	    var questions = [];
	    var points = [];
	    var types = [];
	    var correctAnswers = [];
	    var questions_answers = {};
	    var quizPoints = 0;
	    quiz.find(".questionName").each(function(idx, val)
	    {
    	   questions.push($(this).val());
    	});
	    quiz.find(".questionPoints").each(function(idx, val)
		{
	    	points.push($(this).val());
	    	quizPoints += parseInt($(this).val());
		});
	    quiz.find(".questionType[value='open']").each(function(idx, val)
		{
	    	if($(this).prop("checked"))
	    	{
	    		types.push("open");
	    		correctAnswers.push("");
	    	}
	    	else
	    	{
	    		types.push("closed");
	    		var correctAnswerID = $("#"+val.id).closest(".quizQuestion").find(".correctAnswer[value='correct']").attr("id");
	    		correctAnswerID = correctAnswerID.replace("_correct","");
	    		correctAnswers.push($("#"+correctAnswerID).val());
	    		
	    		var currentQuestion = $("#"+val.id).closest(".quizQuestion");
	    		var questionName = currentQuestion.find(".questionName").val();
	    		var currentQuestion_answers = [];
	    		currentQuestion.find(".correctAnswer").each(function(idx, val)
			    {
		    	   currentQuestion_answers.push($(this).closest(".answer").find("input").last().val());
		    	});
	    		questions_answers[questionName] = currentQuestion_answers;
	    		
	    	}
		});
	    console.log("==============================");
	    console.log("ID CONTEST");
	    console.log(contestId);
	    console.log("NOME QUIZ");
	    console.log(quizName);
	    console.log("PUNTI TOTALI");
	    console.log(quizPoints);
	    console.log("DOMANDE");
	    console.log(questions);
	    console.log("PUNTI");
	    console.log(points);
	    console.log("TIPOLOGIA");
	    console.log(types);
	    console.log("RISPOSTA CORRETTA");
	    console.log(correctAnswers);
	    console.log("RISPOSTE");
	    for (var i in questions_answers) {
	        console.log(questions_answers[i]);
	    }
	    console.log("==============================");
	    
	    // Create Quiz DTO
	    var quiz =
    	{
	    	"contestName" : contestId,
	    	"quizName" : quizName,
	    	"quizPoints" : quizPoints,
	    	"questions" : questions,
	    	"types" : types,
	    	"points" : points,
	    	"correctAnswers" : correctAnswers,
	    	"questions_answers": questions_answers
    	}
	    
	    // Disable Submit button until Ajax call callback
	    $("#createQuizBtn").prop("disabled", true);
	    $.ajax(
	    {
	        type: "POST",
	        contentType : 'application/json; charset=utf-8',
	        url: "addQuiz",
	        data: JSON.stringify(quiz),
	        success :function(result)
	        {
	        	window.location.replace("/");
	        	$("#createQuizBtn").prop("disabled", false);
	        }
	    }).fail(function(jqXHR, textStatus)
	    {
	        console.log(textStatus);
	        $("#createQuizBtn").prop("disabled", false);
	    });
	});
}

function addQuestion()
{
	var isFirstQuestionClosed = $("#question1_closed").prop("checked");

	// Clone Question HTML
	var newQuestion = $('.quizQuestion:first').clone();
	$(".quizQuestions").append(newQuestion);

	// Set IDs for new Question
	lastQuestionID++;
	var lastQuestion = $(".quizQuestion").last();
	lastQuestion.attr("id","question"+lastQuestionID);
	
	lastQuestion.find("#question1_open").next().attr("for","question"+lastQuestionID+"_open");
	lastQuestion.find("#question1_open").attr("name","question"+lastQuestionID+"_type");
	lastQuestion.find("#question1_open").attr("id","question"+lastQuestionID+"_open");
	
	lastQuestion.find("#question1_closed").next().attr("for","question"+lastQuestionID+"_closed");
	lastQuestion.find("#question1_closed").attr("name","question"+lastQuestionID+"_type");
	lastQuestion.find("#question1_closed").attr("id","question"+lastQuestionID+"_closed");
	
	// Set default Question Type (open)
	$("#question"+lastQuestionID+"_open").prop("checked", true);
	$("#question"+lastQuestionID+"_closed").prop("checked", false);
	
	lastQuestion.find("#question1_name").closest("div.input-group").prev().attr("for","question"+lastQuestionID+"_name");
	lastQuestion.find("#question1_name").val("");
	lastQuestion.find("#question1_name").attr("id","question"+lastQuestionID+"_name");
	
	lastQuestion.find("#question1_points").closest("div.input-group").prev().attr("for","question"+lastQuestionID+"_points");
	lastQuestion.find("#question1_points").val("5");
	lastQuestion.find("#question1_points").attr("id","question"+lastQuestionID+"_points");
	
	// Reset first Question correct answer to previous state
	var FirstQuestionCorrectAnswer = $(".quizQuestion").first().find(".correctAnswer[value='correct']").attr("id");
	$("#"+FirstQuestionCorrectAnswer).prop("checked", true);
	
	// Remove Answers on new Question
	$("#question"+lastQuestionID).find(".answer").remove();
	
	// Reset first Question Type
	if(isFirstQuestionClosed)
	{
		$("#question1_closed").prop("checked", true);
	}
	else
	{
		$("#question1_open").prop("checked", true);
	}
	// Add Delete button to the new Question
	$("#question"+lastQuestionID).prepend('<i class="material-icons md-36 delQuestionBtn">remove</i>');
}

function removeQuestion()
{
	$(this).closest('.quizQuestion').remove();
}

function addAnswer(questionID)
{
	var lastAnswerID = $("#question"+questionID).find(".answer").last().attr("id");
	
	// If there are no answers, initialize lastAnswerID
	if(typeof lastAnswerID == "undefined")
	{
		lastAnswerID = 0;
	}
	else
	{
		lastAnswerID = lastAnswerID.replace(/q\d+|a/gi, "");
	}
	lastAnswerID++;
	$('#question'+questionID).append(newAnswer);
	var lastAnswer = $("#question"+questionID).find(".answer").last();
	lastAnswer.attr("id","q"+questionID+"a"+lastAnswerID);
	lastAnswer.find("label").last().attr("for", "question"+questionID+"-answer"+lastAnswerID+"");
	lastAnswer.find("input").last().attr("id", "question"+questionID+"-answer"+lastAnswerID+"");
	lastAnswer.find("label").last().html("Risposta "+lastAnswerID);
	
	lastAnswer.find("input").first().attr("name", "question"+questionID+"_correct");
	lastAnswer.find("label").first().attr("for", "question"+questionID+"-answer"+lastAnswerID+"_correct");
	lastAnswer.find("input").first().attr("id", "question"+questionID+"-answer"+lastAnswerID+"_correct");
}

function toggleForm()
{
	if (this.value == 'open')
	{
		var q = $(this).closest('.quizQuestion');
		$("#"+q.attr("id")+" > .answer").remove();
	}
	else if (this.value == 'closed')
	{
		var questionID = $(this).closest('.quizQuestion').attr('id');
		questionID = questionID.replace(/^question/i, '');
		
		addAnswer(questionID);
		addAnswer(questionID);
		addAnswer(questionID);
	}
}

function setCorrectValue()
{
	$(this).closest(".quizQuestion").find(".correctAnswer").attr("value","");
	$(this).attr("value","correct");
}

$(document).ready(init);
$(document).on('click','i.delQuestionBtn',removeQuestion);
$(document).on('change','input.questionType',toggleForm);
$(document).on('change','input.correctAnswer',setCorrectValue);