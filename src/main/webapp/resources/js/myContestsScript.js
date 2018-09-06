/**
 * @author Alessandro De Simone
 */

function toggleExamVisibility(contestID)
{
	bootbox.confirm(
	{
		title : "Are you sure?",
		message : "Exam visibility will change.",
		buttons :
		{
			cancel :
			{
				label : '<i class="fa fa-times"></i> Cancel'
			},
			confirm :
			{
				label : '<i class="fa fa-check"></i> Confirm'
			}
		},
		callback : function(result)
		{
			if (result)
			{
				$.post("examVisibility", { contestID: contestID })
					.done(function()
					{
						var button = $("input[data-id='"+contestID+"']");
						var buttonVal = button.val();
						if(buttonVal.includes("Show"))
						{
							button.val("Hide Exam");
							button.removeClass("btn-success");
							button.addClass("btn-danger");
						}
						else
						{
							button.val("Show Exam");
							button.removeClass("btn-danger");
							button.addClass("btn-success");
						}
					})
			}
		}
	});
}

function init()
{
	$('.visibleExamBtn').click(function()
	{
		toggleExamVisibility($(this).data("id"));
	});
}

$(document).ready(init);