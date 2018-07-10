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
			}
		}
	});
}

function editProblem(problemID)
{
	// Clear form
	$('#id').val("");
	$('#editP_problemName').val("");
	$('#editP_description').val("");
	$('#editP_timeout').val("");
	$("#editP_show_testcase").prop("checked", false);
	$('#editP_problemTags').tagsinput('removeAll');
	
	// Get Problem Object and fill Edit form
	$.ajax(
	{
		type : "GET", url : "problem?op=editProblem&id=" + problemID, datatype : "json",
		success : function(data)
		{
			data = $.parseJSON(data);
			console.log(data.id_problem);
			$('#id').val(data.id_problem);
			$('#editP_problemName').val(data.name);
			$('#editP_description').val(data.description);
			$('#editP_timeout').val(data.timelimit / 1000);
			if(data.show_testcase)
			{
				$("#editP_show_testcase").prop("checked", true);
			}
			else
			{
				$("#editP_show_testcase").prop("checked", false);
			}
			if (typeof data.tags !== 'undefined' && data.tags.length > 0) {
				data.tags.forEach(function(i){
					$('#editP_problemTags').tagsinput('add', i.value);
				});
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
	$('.editProblemBtn').click(function()
	{
		editProblem($(this).data("id"));
	});

	// Get Tags for Typeahead
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
			
			var tagsnamesEdit = new Bloodhound({
				datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
				queryTokenizer: Bloodhound.tokenizers.whitespace,
				local: $.map(tags, function (tag) {
					return {
						name: tag
					};
				})
			});
			tagsnamesEdit.initialize();
			
			$('#editP_tagsDiv input').tagsinput({
				typeaheadjs: [{
					minLength: 1,
					highlight: true,
				},{
					minlength: 1,
					name: 'tagsnamesEdit',
					displayKey: 'name',
					valueKey: 'name',
					source: tagsnamesEdit.ttAdapter()
				}],
				freeInput: true,
				confirmKeys: [13, 44, 32],
				trimValue: true
			});
		}
	});
	
	// Get Contests
	$.ajax(
	{
		type : "GET", url : "createProblem?req=contests", datatype : "json",
		success : function(data)
		{
			$('#editP_contestName').html("");
			data = $.parseJSON(data);
			var contests = [];
			$.each(data, function(key, val)
			{
				contests.push('<option id="' + val + '">' + val + '</option>');
			})
			$('#editP_contestName').append(contests);
		}
	});
	
	// Get popular Tags
	$.ajax(
	{
		type : "GET", url : "createProblem?req=popularTags", datatype : "json",
		success : function(data)
		{
			data = $.parseJSON(data);
			var tags = [];
			$('#editP_popularTags').html("I Tag più utilizzati: ");
			var i = 0;
			$.each(data, function(key, val)
			{
				tags.push(val[0]+", ");
				if(key == data.length-1)
				{
					$('#editP_popularTags').append("<i>"+val[0]+"</i>");
				}
				else
				{
					$('#editP_popularTags').append("<i>"+val[0]+"</i>, ");
				}
			})
		}
	});
}

$(document).ready(init);