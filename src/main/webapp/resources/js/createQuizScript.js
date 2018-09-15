/**
 * @author Alessandro De Simone
 */

var lastQuestionID = 1;
var tagsnames = "";
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
								<input type="text" class="form-control input-sm" name="" id="" placeholder="Insert answer" required> \
							</div> \
						</div> \
					</div> \
				</div>';

function init()
{
	$('#newQuestionBtn').click(addQuestion);
	$('#generate_newArg').click(gen_addArg);
	
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
	
	$.ajax(
	{
		type : "GET", url : "quiz?req=tags", datatype : "json",
		success : function(data)
		{
			data = $.parseJSON(data);
			var tags = [];
			$.each(data, function(key, val)
			{
				tags.push(val);
			})
			
			tagsnames = new Bloodhound({
				datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
				queryTokenizer: Bloodhound.tokenizers.whitespace,
				local: $.map(tags, function (tag) {
					return {
						name: tag
					};
				})
			});
			tagsnames.initialize();
			
			$("#question1 .questionTags input").tagsinput({
				typeaheadjs: [{
					minLength: 1,
					highlight: true,
				},{
					minlength: 1,
					name: 'tagsnames',
					displayKey: 'name',
					valueKey: 'name',
					source: tagsnames.ttAdapter()
				}],
				freeInput: true,
				confirmKeys: [13, 44, 32],
				trimValue: true
			});
			
			$("#generate_arg1").tagsinput({
				typeaheadjs: [{
					minLength: 1,
					highlight: true,
				},{
					minlength: 1,
					name: 'tagsnames',
					displayKey: 'name',
					valueKey: 'name',
					source: tagsnames.ttAdapter()
				}],
				freeInput: true,
				confirmKeys: [13, 44, 32],
				trimValue: true,
				maxTags: 1
			});
		}
	});
	
	$('#quizForm').submit(function(e)
	{
	    e.preventDefault();
	
	    var quiz = $(".quizQuestions");
	    quiz.find(".answer").each(function(idx, val)
		{
	    	var answer = $(this).find("input").last().val();
	    	$(this).find("input").first().val(answer);
		});
	    var quizPoints = 0;
	    quiz.find(".questionPoints").each(function(idx, val)
		{
	    	quizPoints += parseInt($(this).val());
		});
	    $("#quizPoints").val(quizPoints);

	    this.submit();
	});
	
	$('#generate_form').submit(function(e)
	{
	    e.preventDefault();
	    
	    var questionsNumber = [];
	    var tags = [];

	    
	    var generate = $("#generateModal");
	    generate.find(".nArgs").each(function(idx, val)
	    {
    	   questionsNumber.push($(this).val());
    	})
    	generate.find("input[name=generate_arg]").each(function(idx, val)
		{
    		tags.push($(this).val());
		})
		
		console.log("==============================");
	    console.log("QUANTITA' DOMANDE");
	    console.log(questionsNumber);
	    console.log("TAGS");
	    console.log(tags);
	    console.log("==============================");
	    
	    var randomQuestions =
		{
	    	"questionTagValues" : tags,
	    	"numberOfQuestions" : questionsNumber
		}
	    
	    $("#generateQuizBtn").prop("disabled", true);
	    $.ajax(
	    {
	        type: "POST",
	        contentType : 'application/json; charset=utf-8',
	        url: "generateQuestions",
	        data: JSON.stringify(randomQuestions),
	        success :function(data)
	        {
	        	gen_resetQuiz();
	        	$('#generateModal').modal('toggle');
	        	$.each(data , function( key, value )
	        	{
	                var split = key.split(/:_:/g);
	                if($("#question1_name").val() == "")
	                {
	                	gen_setFirstQuestion(split, value);
	                }
	                else
                	{
	                	addQuestion(split);
	                	value.forEach(function(answer)
            			{
            			    addAnswer(lastQuestionID, answer, split[3]);
            			});
                	}
	                $(".quizQuestion").last().find("input").attr("readonly", true);
	                $(".quizQuestion").last().find(':radio:not(:checked)').attr('disabled', true);
	            });
	        	$("#generateQuizBtn").prop("disabled", false);
	        }
	    }).fail(function(jqXHR, textStatus)
	    {
	        console.log(textStatus);
	        $("#generateQuizBtn").prop("disabled", false);
	    });
	});
}

function gen_addArg()
{
	// Clone Argument HTML
	var newArg = $('.questionArgument:first').clone();
	// Get last Argument ID
	var lastArg = $(".questionArgument").last();
	var lastArgID = lastArg.find("input").last().attr("id");
	lastArgID = parseInt(lastArgID.replace("generate_arg",""));
	lastArgID++;
	
	newArg.insertBefore($("#generateModal").find(".form-control-static").first().prev());
	lastArg = $(".questionArgument").last();
	
	// Set IDs and Values for new Argument
	lastArg.find("label").first().attr("for","generate_nArg"+lastArgID);
	lastArg.find("input").first().attr("id","generate_nArg"+lastArgID);
	lastArg.find("#generate_nArg"+lastArgID).val("1");

	lastArg.find("label").last().attr("for","generate_arg"+lastArgID);
	lastArg.find("input").last().attr("id","generate_arg"+lastArgID);
	lastArg.find("#generate_arg"+lastArgID).val("");
	
	lastArg.find(".bootstrap-tagsinput").remove();
	lastArg.find("#generate_arg"+lastArgID).css('display', '');
	
	// Add Typeahead for Tags in new Question
	$("#generate_arg"+lastArgID).tagsinput({
		typeaheadjs: [{
			minLength: 1,
			highlight: true,
		},{
			minlength: 1,
			name: 'tagsnames',
			displayKey: 'name',
			valueKey: 'name',
			source: tagsnames.ttAdapter()
		}],
		freeInput: true,
		confirmKeys: [13, 44, 32],
		trimValue: true,
		maxTags: 1
	});
	
	// Add Delete button to the new Argument
	$("#generate_arg"+lastArgID).closest(".row").prepend('<div class="col-md-1"><i class="material-icons md-18 delArgumentBtn">remove</i></div>');
	
	// Calculate new Total
	gen_CalculateTotalQuestions();
}

function gen_resetQuiz()
{
	// Reset old Quiz after new Quiz generation
	$(".quizQuestion").not(":first").remove();
	$("#question1_name").val("");
}

function gen_setFirstQuestion(question, answers)
{
	// Set First generated Question
	$("#question1_name").val(question[1]);
	$("#question1_points").val(question[2]);
	$("#question1").find(".answer").remove();
	$("#question1_tags").closest(".form-group").css("display", "none");
	
	if(question[4] === "MULTIPLE")
	{
		$("#question1_open").prop("checked", false);
		$("#question1_closed").prop("checked", true);
		
		// Add Question Answers
		answers.forEach(function(answer)
		{
		    addAnswer(1, answer, question[3]);
		});
	}
	else
	{
		$("#question1_open").prop("checked", true);
		$("#question1_closed").prop("checked", false);
	}
}

function addQuestion(question)
{
	var isFirstQuestionClosed = $("#question1_closed").prop("checked");

	// Clone Question HTML
	var newQuestion = $('.quizQuestion:first').clone();
	$(".quizQuestions").append(newQuestion);

	// Set IDs for new Question
	lastQuestionID++;
	var lastQuestion = $(".quizQuestion").last();
	lastQuestion.attr("id","question"+lastQuestionID);
	
	// Set new Question editable if first Question is generated
	lastQuestion.find("input").attr("readonly", false);
	lastQuestion.find(':radio:not(:checked)').attr('disabled', false);
	
	lastQuestion.find("#question1_open").next().attr("for","question"+lastQuestionID+"_open");
	lastQuestion.find("#question1_open").attr("name","question_types[question"+lastQuestionID+"]");
	lastQuestion.find("#question1_open").attr("id","question"+lastQuestionID+"_open");
	
	lastQuestion.find("#question1_closed").next().attr("for","question"+lastQuestionID+"_closed");
	lastQuestion.find("#question1_closed").attr("name","question_types[question"+lastQuestionID+"]");
	lastQuestion.find("#question1_closed").attr("id","question"+lastQuestionID+"_closed");
	
	// Set default Question Type (open)
	$("#question"+lastQuestionID+"_open").prop("checked", true);
	$("#question"+lastQuestionID+"_closed").prop("checked", false);
	
	if(question[4] === "MULTIPLE")
	{
		$("#question"+lastQuestionID+"_open").prop("checked", false);
		$("#question"+lastQuestionID+"_closed").prop("checked", true);
	}
	
	lastQuestion.find("#question1_name").closest("div.input-group").prev().attr("for","question"+lastQuestionID+"_name");
	lastQuestion.find("#question1_name").val(question[1]);
	lastQuestion.find("#question1_name").attr("id","question"+lastQuestionID+"_name");
	
	lastQuestion.find("#question1_points").closest("div.input-group").prev().attr("for","question"+lastQuestionID+"_points");
	lastQuestion.find("#question1_points").val(question[2] !== undefined ? question[2] : 5);
	lastQuestion.find("#question1_points").attr("id","question"+lastQuestionID+"_points");
	
	// Add Tags input if first Question is generated
	if($("#question1_name").attr("readonly"))
	{
		lastQuestion.find("#question1_tags").closest(".form-group").css("display", "block");
	}
	
	// Set Tags input for new Question
	lastQuestion.find(".bootstrap-tagsinput").remove();
	lastQuestion.find("#question1_tags").css('display', '');
	lastQuestion.find("#question1_tags").closest("div.form-group").find("label").attr("for","question"+lastQuestionID+"_tags");
	lastQuestion.find("#question1_tags").val("");
	lastQuestion.find("#question1_tags").attr("name","questions_tags[question"+lastQuestionID+"]");
	lastQuestion.find("#question1_tags").attr("id","question"+lastQuestionID+"_tags");

	// If Question is generated, hide Tags
	if(question[2] !== undefined)
	{
		$("#question"+lastQuestionID+"_tags").closest(".form-group").css("display", "none");
	}

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
	
	// Add Typeahead for Tags in new Question
	$("#question"+lastQuestionID+" .questionTags input").tagsinput({
		typeaheadjs: [{
			minLength: 1,
			highlight: true,
		},{
			minlength: 1,
			name: 'tagsnames',
			displayKey: 'name',
			valueKey: 'name',
			source: tagsnames.ttAdapter()
		}],
		freeInput: true,
		confirmKeys: [13, 44, 32],
		trimValue: true
	});
	
	// Add Delete button to the new Question
	$("#question"+lastQuestionID).prepend('<i class="material-icons md-36 delQuestionBtn">remove</i>');
}

function removeArgument()
{
	$(this).closest('.questionArgument').remove();
	gen_CalculateTotalQuestions();
}

function removeQuestion()
{
	$(this).closest('.quizQuestion').remove();
}

function addAnswer(questionID, answer, correctID)
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
	$(newAnswer).insertBefore($('#question'+questionID).find(".questionTags").closest(".form-group"));
	var lastAnswer = $("#question"+questionID).find(".answer").last();
	lastAnswer.attr("id","q"+questionID+"a"+lastAnswerID);
	lastAnswer.find("label").last().attr("for", "question"+questionID+"-answer"+lastAnswerID+"");
	lastAnswer.find("input").last().attr("name", "questions_answers[question"+questionID+"]");
	lastAnswer.find("input").last().attr("id", "question"+questionID+"-answer"+lastAnswerID+"");
	lastAnswer.find("label").last().html("Answer "+lastAnswerID);
	
	// If Question is generated, set the related answers
	$("#question"+questionID+"-answer"+lastAnswerID).val(answer !== undefined ? answer.text : "");
	
	lastAnswer.find("input").first().attr("name", "correctAnswers[question"+questionID+"]");
	lastAnswer.find("label").first().attr("for", "question"+questionID+"-answer"+lastAnswerID+"_correct");
	lastAnswer.find("input").first().attr("id", "question"+questionID+"-answer"+lastAnswerID+"_correct");

	// If Question is generated and current Answer is the correct one, check radiobutton
	if(answer !== undefined && answer.id == correctID)
	{
		$("#question"+questionID+"-answer"+lastAnswerID+"_correct").prop("checked", true);
		$("#question"+questionID+"-answer"+lastAnswerID+"_correct").attr("value", "correct");
	}
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
		console.log(questionID);
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

function gen_CalculateTotalQuestions()
{
	var total = 0;
    $(".questionArgument").find(".nArgs").each(function(idx, val)
	{
    	total += parseInt($(this).val());
	});
    $("#totalArgsNumber").html(total);
}

function oldSubmit()
{
//	$('#quizForm').submit(function(e)
//	{
//	    e.preventDefault();
//	    
//	    // Manage input data to send
//	    var quiz = $(".quizQuestions");
//	    
//	    var contestId = $("#nQuiz_contest").val();
//	    var quizName = $("#nQuiz_name").val();
//	    var questions = [];
//	    var points = [];
//	    var types = [];
//	    var correctAnswers = [];
//	    var questions_answers = {};
//	    var quizPoints = 0;
//	    quiz.find(".questionName").each(function(idx, val)
//	    {
//    	   questions.push($(this).val());
//    	});
//	    quiz.find(".questionPoints").each(function(idx, val)
//		{
//	    	points.push($(this).val());
//	    	quizPoints += parseInt($(this).val());
//		});
//	    quiz.find(".questionType[value='open']").each(function(idx, val)
//		{
//	    	if($(this).prop("checked"))
//	    	{
//	    		types.push("open");
//	    		correctAnswers.push("");
//	    	}
//	    	else
//	    	{
//	    		types.push("closed");
//	    		var correctAnswerID = $("#"+val.id).closest(".quizQuestion").find(".correctAnswer[value='correct']").attr("id");
//	    		correctAnswerID = correctAnswerID.replace("_correct","");
//	    		correctAnswers.push($("#"+correctAnswerID).val());
//	    		
//	    		var currentQuestion = $("#"+val.id).closest(".quizQuestion");
//	    		var questionName = currentQuestion.find(".questionName").val();
//	    		var currentQuestion_answers = [];
//	    		currentQuestion.find(".correctAnswer").each(function(idx, val)
//			    {
//		    	   currentQuestion_answers.push($(this).closest(".answer").find("input").last().val());
//		    	});
//	    		questions_answers[questionName] = currentQuestion_answers;
//	    		
//	    	}
//		});
//	    console.log("==============================");
//	    console.log("ID CONTEST");
//	    console.log(contestId);
//	    console.log("NOME QUIZ");
//	    console.log(quizName);
//	    console.log("PUNTI TOTALI");
//	    console.log(quizPoints);
//	    console.log("DOMANDE");
//	    console.log(questions);
//	    console.log("PUNTI");
//	    console.log(points);
//	    console.log("TIPOLOGIA");
//	    console.log(types);
//	    console.log("RISPOSTA CORRETTA");
//	    console.log(correctAnswers);
//	    console.log("RISPOSTE");
//	    for (var i in questions_answers) {
//	        console.log(questions_answers[i]);
//	    }
//	    console.log("==============================");
//	    
//	    // Create Quiz DTO
//	    var quiz =
//    	{
//	    	"contestName" : contestId,
//	    	"quizName" : quizName,
//	    	"quizPoints" : quizPoints,
//	    	"questions" : questions,
//	    	"types" : types,
//	    	"points" : points,
//	    	"correctAnswers" : correctAnswers,
//	    	"questions_answers": questions_answers
//    	}
//	    
//	    // Disable Submit button until Ajax call callback
//	    $("#createQuizBtn").prop("disabled", true);
//	    $.ajax(
//	    {
//	        type: "POST",
//	        contentType : 'application/json; charset=utf-8',
//	        url: "addQuiz",
//	        data: JSON.stringify(quiz),
//	        success :function(result)
//	        {
//	        	window.location.replace("/");
//	        	$("#createQuizBtn").prop("disabled", false);
//	        }
//	    }).fail(function(jqXHR, textStatus)
//	    {
//	        console.log(textStatus);
//	        $("#createQuizBtn").prop("disabled", false);
//	    });
//	});
}

$(document).ready(init);
$(document).on('click','i.delArgumentBtn',removeArgument);
$(document).on('click','i.delQuestionBtn',removeQuestion);
$(document).on('change','input.questionType',toggleForm);
$(document).on('change','input.correctAnswer',setCorrectValue);
$(document).on('change','input.nArgs',gen_CalculateTotalQuestions);