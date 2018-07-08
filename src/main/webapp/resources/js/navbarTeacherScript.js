/**
 * 
 */
var c = [];
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

$(document).ready(init);