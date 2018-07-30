/**
 * 
 */
var c = [];
var nav_newAnswer = '<div class="answer" id=""> \
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
	var fileExtension;
	$("#testcase").on('change',manageProblemForm);
	$("#generateInput").on('change', generateInput);
	$("#isExam").on('change',askPassword);
	
	$("#newSub_year").datepicker( {
	    format: "yyyy",
	    viewMode: "years", 
	    minViewMode: "years"
	});
	$("#newContest_deadline").datepicker( {
		format: "dd-mm-yyyy"
	});
	
	$.ajax(
	{
		type : "GET", url : "createProblem?req=tags", datatype : "json",
		success : function(data)
		{
			data = $.parseJSON(data);
			var tags = [];
			$.each(data, function(key, val)
			{
				tags.push(val);
			})
			
			var tagsnames = new Bloodhound({
				datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
				queryTokenizer: Bloodhound.tokenizers.whitespace,
				local: $.map(tags, function (tag) {
					return {
						name: tag
					};
				})
			});
			tagsnames.initialize();
			
			$('#tagsDiv input').tagsinput({
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
		}
	});
	
	$.ajax(
	{
		type : "GET", url : "createProblem?req=contests", datatype : "json",
		success : function(data)
		{
			$('#newQuestion_contestName').html("");
			data = $.parseJSON(data);
			var contests = [];
			$.each(data, function(key, val)
			{
				contests.push('<option id="' + val + '">' + val + '</option>');
			})
			$('#newQuestion_contestName').append(contests);
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
			
			$("#addQuestionModal .questionTags input").tagsinput({
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
		}
	});
	
	$('#addQuestionModal form').submit(submitNewQuestion);
	$("#myModal2 form").submit(newContestChecks);
}

function askPassword()
{
	if($(this).is(':checked'))
	{
		$('#newContest_password').closest('.form-group').removeClass('hidden');
		$('#newContest_confPassword').closest('.form-group').removeClass('hidden');
		$('#newContest_password').prop("required", true);
		$('#newContest_confPassword').prop("required", true);
	}
	else
	{
		$('#newContest_password').closest('.form-group').addClass('hidden');
		$('#newContest_confPassword').closest('.form-group').addClass('hidden');
		$('#newContest_password').prop("required", false);
		$('#newContest_confPassword').prop("required", false);
	}
}

function generateInput()
{
	if($(this).is(':checked'))
	{
		$('#type').val("4");
		$('#intDomain').closest('.form-group').removeClass('hidden');
	}
	else
	{
		$('#type').val("3");
		$('#intDomain').closest('.form-group').addClass('hidden');
	}
}

function manageProblemForm()
{
	fileExtension = $(this).val();
	if(/\.(txt|dlv)$/gmi.test(fileExtension))
	{
		$('#output').closest('.form-group').removeClass('hidden');
		$('#type').val("1");
	}
	else
	{
		$('#output').closest('.form-group').addClass('hidden');
	}
	if(/\.(zip|rar|7z|tar)$/gmi.test(fileExtension))
	{
		$('#type').val("2");
		
	}
	if(/\.(java|cpp|c)$/gmi.test(fileExtension))
	{
		$('#type').val("3");
		$('#generateInput').closest('.form-check').removeClass('hidden');
		if($("#generateInput").is(':checked'))
		{
			$('#type').val("4");
			$('#intDomain').closest('.form-group').removeClass('hidden');
		}
	}
	else
	{
		$('#intDomain').closest('.form-group').addClass('hidden');
		$('#generateInput').closest('.form-check').addClass('hidden');
	}
}

function newContestChecks(e)
{
	var psw = $('#newContest_password').val();
	var pswConf = $('#newContest_confPassword').val();
	
	if (psw != pswConf)
	{
		if (!$('#pswConfError').length)
		{
			$("#newContest_confPassword").closest('.form-group').addClass('has-error');
			$("#newContest_confPassword").closest('.input-group').after("<span id='pswConfError' class='help-block'>Password does not match the confirm password!</span>");

		}
		e.preventDefault();
	}
	else
	{
		$('#pswConfError').remove();
		$("#newContest_confPassword").closest('.form-group').removeClass('has-error');
	}
}

function getContests()
{
	$.ajax(
	{
		type : "GET", url : "createProblem?req=contests", datatype : "json",
		success : function(data)
		{
			$('#contestName').html("");
			data = $.parseJSON(data);
			var contests = [];
			$.each(data, function(key, val)
			{
				contests.push('<option id="' + val + '">' + val + '</option>');
			})
			$('#contestName').append(contests);
		}
	});
	$.ajax(
	{
		type : "GET", url : "createProblem?req=popularTags", datatype : "json",
		success : function(data)
		{
			data = $.parseJSON(data);
			var tags = [];
			$('#popularTags').html("I Tag più utilizzati: ");
			var i = 0;
			$.each(data, function(key, val)
			{
				tags.push(val[0]+", ");
				if(key == data.length-1)
				{
					$('#popularTags').append("<i>"+val[0]+"</i>");
				}
				else
				{
					$('#popularTags').append("<i>"+val[0]+"</i>, ");
				}
			})
		}
	});
}

function getSubjectsAndJuries()
{
	$.ajax(
	{
		type : "GET", url : "createContest?req=subjects", datatype : "json",
		success : function(data)
		{
			$('#newContest_subjectName').html("");
			data = $.parseJSON(data);
			console.log(data);
			var subjs = [];
			$.each(data, function(key, val)
			{
				subjs.push('<option value="' + key + '">' + val + '</option>');
			})
			console.log(subjs);
			c = subjs;
			$('#newContest_subjectName').append(subjs);
		}
	});
	$.ajax(
	{
		type : "GET", url : "createContest?req=juries", datatype : "json",
		success : function(data)
		{
			$('#newContest_jury').html("");
			data = $.parseJSON(data);
			var juries = [];
			$.each(data, function(key, val)
			{
				juries.push('<option id="' + val + '">' + val + '</option>');
			})
			$('#newContest_jury').append(juries);
		}
	});
}

function nav_addAnswer()
{
	var lastAnswerID = $("#addQuestionModal").find(".answer").last().attr("id");
	
	// If there are no answers, initialize lastAnswerID
	if(typeof lastAnswerID == "undefined")
	{
		lastAnswerID = 0;
	}
	else
	{
		lastAnswerID = lastAnswerID.replace(/answer/gi, "");
	}
	lastAnswerID++;
	$(nav_newAnswer).insertBefore($("#addQuestionModal").find(".questionTags").closest(".form-group"));
	var lastAnswer = $("#addQuestionModal").find(".answer").last();
	
	lastAnswer.attr("id","answer"+lastAnswerID);
	lastAnswer.find("label").last().attr("for", "answer"+lastAnswerID+"");
	lastAnswer.find("input").last().attr("name", "question_answers[question1]");
	lastAnswer.find("input").last().attr("id", "answer"+lastAnswerID+"");
	lastAnswer.find("label").last().html("Answer "+lastAnswerID);
	
	lastAnswer.find("input").first().attr("name", "question_correctAnswer[question1]");
	lastAnswer.find("label").first().attr("for", "answer"+lastAnswerID+"_correct");
	lastAnswer.find("input").first().attr("id", "answer"+lastAnswerID+"_correct");
}

function nav_toggleForm()
{
	if (this.value == 'open')
	{
		$("#addQuestionModal .answer").remove();
	}
	else if (this.value == 'closed')
	{
		nav_addAnswer();
		nav_addAnswer();
		nav_addAnswer();
	}
}

function setCorrectValue()
{
	$("#addQuestionModal").find(".correctAnswer").attr("value","");
	$(this).attr("value","correct");
}

function submitNewQuestion(e)
{
	e.preventDefault();
	
	$('#addQuestionModal').find(".answer").each(function(idx, val)
	{
		var answer = $(this).find("input").last().val();
		$(this).find("input").first().val(answer);
	});
	
	this.submit();
}

$(document).ready(init);
$(document).on('change','input.nav-questionType',nav_toggleForm);
$(document).on('change','input.correctAnswer',setCorrectValue);