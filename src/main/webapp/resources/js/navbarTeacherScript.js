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
	});
	$("#generateInput").on('change',function(){
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
	});
	
	$("#newSub_year").datepicker( {
	    format: "yyyy",
	    viewMode: "years", 
	    minViewMode: "years"
	});
	$("#newContest_deadline").datepicker( {
		format: "dd-mm-yyyy"
	});
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
			var subjs = [];
			$.each(data, function(key, val)
			{
				subjs.push('<option id="' + val + '">' + val + '</option>');
			})
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

$(document).ready(init);