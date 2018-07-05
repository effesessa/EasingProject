/**
 * 
 */
function init()
{
	var form = $('#newTeamForm');

	form.on('submit', checks);
}

function checks(e)
{
	var teamName = $('#newTeam_name').val();
	var member2 = $('#newTeam_member2').val();
	var member3 = $('#newTeam_member3').val();

	if(!(/^\d+$/.test(member2)))
	{
		if (!$('#member2Error').length)
		{
			$('#newTeam_member2').closest('.form-group').addClass('has-error');
			$('#newTeam_member2').closest('.input-group').after("<span id='member2Error' class='help-block'>La Matricola può contenere solo cifre!</span>");
		}
		e.preventDefault();
	}
	else
	{
		$('#member2Error').remove();
		$("#newTeam_member2").closest('.form-group').removeClass('has-error');
	}
	
	if(!(/^\d+$/.test(member3)))
	{
		if (!$('#member3Error').length)
		{
			$('#newTeam_member3').closest('.form-group').addClass('has-error');
			$('#newTeam_member3').closest('.input-group').after("<span id='member3Error' class='help-block'>La Matricola può contenere solo cifre!</span>");
		}
		e.preventDefault();
	}
	else
	{
		$('#member3Error').remove();
		$("#newTeam_member3").closest('.form-group').removeClass('has-error');
	}
}

$(document).ready(init);