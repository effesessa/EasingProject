/**
 * @author Alessandro De Simone
 */

function deleteQuiz(quizID)
{
	bootbox.confirm(
	{
		title : "Sei sicuro?",
		message : "Così eliminerai definitivamente il Quiz.",
		buttons :
		{
			cancel :
			{
				label : '<i class="fa fa-times"></i> Annulla'
			},
			confirm :
			{
				label : '<i class="fa fa-check"></i> Conferma'
			}
		},
		callback : function(result)
		{
			if (result)
			{
				$.post("quiz", { op: "deleteQuiz", id: quizID })
					.done(function(){
						$("input[data-id='"+quizID+"']").closest(".list-group-item").fadeOut('slow', function(){
							$(this).remove();
						});
					});
			}
		}
	});
}

function cloneQuiz(quizID)
{
	$('#cloneQ_id').val(quizID);
}

function init()
{
	$('.deleteQuizBtn').click(function()
	{
		deleteQuiz($(this).data("id"));
	});
	$('.cloneQuizBtn').click(function()
	{
		cloneQuiz($(this).data("id"));
	});

	// Get Contests
	$.ajax(
	{
		type : "GET", url : "createProblem?req=contests", datatype : "json",
		success : function(data)
		{
			$('#editQ_contestName').html("");
			data = $.parseJSON(data);
			var contests = [];
			$.each(data, function(key, val)
			{
				contests.push('<option id="' + val + '">' + val + '</option>');
			})
			$('#editQ_contestName').append(contests);
			$('#cloneQ_contestName').append(contests);
		}
	});
}

$(document).ready(init);