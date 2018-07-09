/**
 * @author Alessandro De Simone
 */

function deleteProblem(problemID)
{
	bootbox.confirm(
	{
		title : "Sei sicuro?",
		message : "Così eliminerai definitivamente il Problema.",
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
				$.post("problem", { op: "deleteProblem", id: problemID })
					.done(function(){
						$("input[data-id='"+problemID+"']").closest(".list-group-item").fadeOut('slow', function(){
							$(this).remove();
						});
					});

//				$.get("problem?op=deleteProblem&id=" + problemID, function(){
//					$("input[data-id='"+problemID+"']").closest(".list-group-item").fadeOut('slow', function(){
//						$(this).remove();
//					});
//				});
			}
		}
	});
}

function init()
{
	$('.deleteProblemBtn').click(function()
	{
		deleteProblem($(this).data("id"));
	});
}

$(document).ready(init);