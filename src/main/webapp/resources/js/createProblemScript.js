/**
 * 
 */

function init()
{
	var fileExtension;
	$("#testcase").on('change',function(){
		fileExtension = $(this).val();
		if(/\.txt$/gmi.test(fileExtension))
		{
			$('#output').closest('.form-group').removeClass('hidden');
		}
		else
		{
			$('#output').closest('.form-group').addClass('hidden');
		}
		if(/\.(java|cpp|c)$/gmi.test(fileExtension))
		{
			$('#generateInput').closest('.form-check').removeClass('hidden');
			if($("#generateInput").is(':checked'))
			{
				$('#intDomain').closest('.form-group').removeClass('hidden');
			}
		}
		else
		{
			$('#intDomain').closest('.form-group').addClass('hidden');
			$('#generateInput').closest('.form-check').addClass('hidden');
		}
	});
	$("#generateInput").on('change',function(){
		if($(this).is(':checked'))
		{
			$('#intDomain').closest('.form-group').removeClass('hidden');
		}
		else
		{
			$('#intDomain').closest('.form-group').addClass('hidden');
		}
	});
}

function getContests()
{
	$.ajax(
	{
		type : "GET", url : "createProblem?req=contests", datatype : "json",
		success : function(data)
		{
			$('#contest').html("");
			data = $.parseJSON(data);
			var contests = [];
			$.each(data, function(key, val)
			{
				contests.push('<option id="' + val + '">' + val + '</option>');
			})
			$('#contest').append(contests);
		}
	});
}

$(document).ready(init);