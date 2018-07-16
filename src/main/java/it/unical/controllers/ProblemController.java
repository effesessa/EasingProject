package it.unical.controllers;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import it.unical.core.DirFilesManager;
import it.unical.core.Engine;
import it.unical.core.SubmissionHandler;
import it.unical.core.Verdict;
import it.unical.core.strategy.TypeContext;
import it.unical.dao.ContestDAO;
import it.unical.dao.MembershipDAO;
import it.unical.dao.ProblemDAO;
import it.unical.dao.SubmitDAO;
import it.unical.dao.TagDAO;
import it.unical.dao.TeamDAO;
import it.unical.dao.UserDAO;
import it.unical.entities.Contest;
import it.unical.entities.Membership;
import it.unical.entities.Problem;
import it.unical.entities.Submit;
import it.unical.entities.Tag;
import it.unical.entities.Team;
import it.unical.entities.User;
import it.unical.forms.AddProblemForm;
import it.unical.forms.SubmitForm;
import it.unical.utils.Judge;
import it.unical.utils.SessionUtils;
import it.unical.utils.Status;
import it.unical.utils.TypeFileExtension;

@Controller
public class ProblemController
{
	private static final Logger logger = LoggerFactory.getLogger(LogInController.class);
	private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	@Autowired
	private WebApplicationContext context;

	private void _addTags(AddProblemForm problemForm, final Problem problem, final TagDAO tagDAO)
	{
		final String[] tags = problemForm.getProblemTags().split(",");
		for (final String tag : tags)
		{
			final Tag t = new Tag();
			t.setProblem(problem);
			t.setValue(tag);
			tagDAO.create(t);
		}
	}

	private void _setAccountAttribute(HttpSession session, Model model)
	{
		if (SessionUtils.isUser(session))
		{
			final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
			final User user = userDAO.get(SessionUtils.getUserIdFromSessionOrNull(session));
			model.addAttribute("user", user);
			model.addAttribute("typeSession", "Account");
			model.addAttribute("userLogged", true);
		}
		else
			model.addAttribute("typeSession", "Login");
	}

	/*
	 * @RequestMapping(value = "/addProblem", method = RequestMethod.POST)
	 * public String addProblem(HttpSession session, @ModelAttribute
	 * AddProblemForm problemForm, Model model) throws IOException { // TODO
	 * Controllare che non esista un altro Problema dello stesso // Contest con
	 * lo stesso nome setAccountAttribute(session, model); final ProblemDAO
	 * problemDAO = (ProblemDAO) context.getBean("problemDAO"); final Problem
	 * problem = new Problem(); final ContestDAO contestDAO = (ContestDAO)
	 * context.getBean("contestDAO"); final Contest contest =
	 * contestDAO.getContestByName(problemForm.getContestName());
	 *
	 * switch (Integer.parseInt(problemForm.getType())) { case 1: {
	 *
	 * String pathTest = problemForm.getPathTest(); String pathSol =
	 * problemForm.getPathSol();
	 *
	 * pathTest = pathTest.replace("file:///", "/"); pathSol =
	 * pathSol.replace("file:///", "/");
	 *
	 * final File file1 = new File(pathTest.trim()); final byte[] fileData1 =
	 * new byte[(int) file1.length()]; final File file2 = new File(pathSol);
	 * byte[] fileData2 = new byte[(int) file2.length()]; try { final
	 * FileInputStream fileInputStream1 = new FileInputStream(file1);
	 * fileInputStream1.read(fileData1); fileInputStream1.close(); } catch
	 * (final Exception e) { e.printStackTrace(); return "redirect:/"; }
	 *
	 * try { final FileInputStream fileInputStream2 = new
	 * FileInputStream(file2); fileInputStream2.read(fileData2);
	 * fileInputStream2.close(); } catch (final Exception e) { fileData2 = null;
	 * e.printStackTrace();
	 *
	 * }
	 *
	 * problem.setName(problemForm.getName());
	 * problem.setType(problemForm.getType()); //problem.setId_contest(contest);
	 * problem.setJury(contest.getJury()); problem.setTimelimit((float) 1000.0);
	 * problem.setSol(fileData2); problem.setTest(fileData1);
	 * problemDAO.create(problem); logger.info("inserito"); return "redirect:/";
	 * } case 2: { String pathTest = problemForm.getPathZip();
	 *
	 * pathTest = pathTest.replace("file:///", "/");
	 *
	 * final File file1 = new File(pathTest.trim()); final byte[] fileData1 =
	 * new byte[(int) file1.length()]; try { final FileInputStream
	 * fileInputStream1 = new FileInputStream(file1);
	 * fileInputStream1.read(fileData1); fileInputStream1.close(); } catch
	 * (final Exception e) { e.printStackTrace(); return "redirect:/"; }
	 *
	 * problem.setName(problemForm.getName());
	 * problem.setType(problemForm.getType()); problem.setId_contest(contest);
	 * problem.setJury(contest.getJury()); problem.setTimelimit((float) 1000.0);
	 * problem.setTest(fileData1); problemDAO.create(problem);
	 *
	 * return "redirect:/"; } case 3: { String pathTest =
	 * problemForm.getPathAlgorithm();
	 *
	 * pathTest = pathTest.replace("file:///", "/"); final File file1 = new
	 * File(pathTest.trim()); final byte[] fileData1 = new byte[(int)
	 * file1.length()];
	 *
	 * try { final FileInputStream fileInputStream1 = new
	 * FileInputStream(file1); fileInputStream1.read(fileData1);
	 * fileInputStream1.close(); } catch (final Exception e) {
	 * e.printStackTrace(); return "redirect:/"; }
	 *
	 * pathTest = pathTest.replace("/Main.java", "");
	 *
	 * final Judge judge = new Judge("java", "");
	 *
	 * String result = judge.compile("java", "", pathTest);
	 *
	 * result = judge.execute("java", "", 1000, pathTest);
	 *
	 * final byte[] solution = result.getBytes();
	 *
	 * problem.setName(problemForm.getName());
	 * problem.setType(problemForm.getType()); problem.setId_contest(contest);
	 * problem.setJury(contest.getJury()); problem.setTimelimit((float) 1000.0);
	 * problem.setSol(solution); problemDAO.create(problem);
	 *
	 * return "redirect:/"; }
	 *
	 * case 4: {
	 *
	 * String pathTest = problemForm.getPathAlgorithm();
	 *
	 * pathTest = pathTest.replace("file:///", "/"); final File file1 = new
	 * File(pathTest.trim()); final byte[] fileData1 = new byte[(int)
	 * file1.length()];
	 *
	 * try { final FileInputStream fileInputStream1 = new
	 * FileInputStream(file1); fileInputStream1.read(fileData1);
	 * fileInputStream1.close(); } catch (final Exception e) {
	 * e.printStackTrace(); return "redirect:/"; }
	 *
	 * pathTest = pathTest.replace("/Main.java", "");
	 *
	 * final Judge judge = new Judge("java", ""); final String domain =
	 * problemForm.getDomain();
	 *
	 * String test = null;
	 *
	 * if (domain.equals("Array Integer")) { final TestCase testcase = new
	 * ArrayTest(); test = testcase.generate(); }
	 *
	 * String result = judge.compile("java", "", pathTest);
	 *
	 * result = judge.execute("java", test, 1000, pathTest);
	 * logger.info(result); if (result.equals("RUN_ERROR")) return "redirect:/";
	 * final byte[] solution = result.getBytes();
	 *
	 * System.out.println(result);
	 *
	 * problem.setName(problemForm.getName());
	 * problem.setType(problemForm.getType()); problem.setId_contest(contest);
	 * problem.setJury(contest.getJury()); problem.setTimelimit((float) 1000.0);
	 * problem.setTest(test.getBytes()); problem.setSol(solution);
	 * problemDAO.create(problem); } } return "redirect:/"; }
	 */

	@RequestMapping(value = "/addProblem", method = RequestMethod.POST)
	public String addProblem(HttpSession session, @ModelAttribute AddProblemForm problemForm, Model model)
			throws IOException
	{
		final TypeContext typeContext = TypeContext.getInstance();
		typeContext.setStrategy(problemForm.getTestcase().getOriginalFilename());
		final Problem problem = typeContext.prepareToSave(problemForm);
		if (typeContext.getVerdict().getStatus() == Status.SUCCESS)
		{
			final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
			final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
			final Contest contest = contestDAO.getContestByName(problemForm.getContestName());
			problem.setId_contest(contest);
			problem.setJury(contest.getJury());
			problemDAO.create(problem);

			final TagDAO tagDAO = (TagDAO) context.getBean("tagDAO");
			_addTags(problemForm, problem, tagDAO);
		}
		else
			System.out.println("TODO redirect with popup errore" + typeContext.getVerdict().getStatus());
		return "redirect:/";
	}

	@RequestMapping(value = "/myProblems", method = RequestMethod.GET)
	public String addProblem(HttpSession session, Model model) throws IOException
	{
		_setAccountAttribute(session, model);
		final User user = (User) model.asMap().get("user");

		if (user == null || !user.isProfessor())
			return "redirect:/";

		final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");

		// final List<String> professorContests =
		// contestDAO.getContestsNamesByProfessor(user.getId());
		final List<Contest> professorContests = contestDAO.getContestsByProfessor(user.getId());
		final List<Problem> problemsByProfessor = problemDAO.getProblemsByProfessor(user.getId());

		model.addAttribute("contests", professorContests);
		model.addAttribute("problems", problemsByProfessor);
		return "myproblems";
	}

	@RequestMapping(value = "/createProblem", method = RequestMethod.GET)
	public void addProblem(@RequestParam String req, HttpSession session, Model model, HttpServletResponse response)
	{
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		final ObjectMapper mapper = new ObjectMapper();
		final int userID = SessionUtils.getUserIdFromSessionOrNull(session);
		if (SessionUtils.isLoggedIn(session) && req.equals("contests"))
		{
			final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
			final List<String> contests = contestDAO.getContestsNamesByProfessor(userID);
			try
			{
				mapper.writeValue(response.getOutputStream(), contests);
			}
			catch (final IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (SessionUtils.isLoggedIn(session) && req.equals("tags"))
		{
			final TagDAO tagDAO = (TagDAO) context.getBean("tagDAO");
			final List<String> tags = tagDAO.getAllTagValues();
			try
			{
				mapper.writeValue(response.getOutputStream(), tags);
			}
			catch (final IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (SessionUtils.isLoggedIn(session) && req.equals("popularTags"))
		{
			final TagDAO tagDAO = (TagDAO) context.getBean("tagDAO");
			final List<String> tags = tagDAO.getMostPopularTags();
			try
			{
				mapper.writeValue(response.getOutputStream(), tags);
			}
			catch (final IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public String confirm(@RequestParam String teamname, @RequestParam String problemid, @RequestParam String path,
			HttpSession session, Model model)
	{
		_setAccountAttribute(session, model);

		final SubmitDAO submitDAO = (SubmitDAO) context.getBean("submitDAO");

		final TeamDAO teamDAO = (TeamDAO) context.getBean("teamDAO");
		final Team team = teamDAO.getByName(teamname);

		Submit submit = submitDAO.getAllSubmitByProblemAndTeamFake(Integer.parseInt(problemid), team.getId());

		final File fileSolution = new File(path);
		final byte[] fileData = new byte[(int) fileSolution.length()];

		final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
		final Problem problem = problemDAO.get(Integer.parseInt(problemid));

		if (submit != null)
			submitDAO.delete(submit);
		final LocalDate localDate = LocalDate.now();

		submit = new Submit();
		submit.setIdTeam(team);
		submit.setProblem(problem);
		submit.setInfo(problem.getName());
		// set the score eventually here
		submit.setDate(DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate));
		submit.setSolution(fileData);

		submitDAO.create(submit);
		return "redirect:/";

	}

	@RequestMapping(value = "/downloadSubmit/{submitId}", method = RequestMethod.GET)
	public void downloadSubmit(@PathVariable String submitId, HttpSession session, HttpServletResponse response)
	{
		final SubmitDAO submitDAO = (SubmitDAO) context.getBean("submitDAO");
		final Submit submit = submitDAO.get(Integer.parseInt(submitId));
		final String mimeType = TypeFileExtension.getMimeType(submit.getType());
		response.setContentType(mimeType);
		response.setHeader("Content-disposition",
				"attachment; filename=" + Engine.BASE_NAME_SUBMIT + Engine.DOT + submit.getType());
		final byte[] data = submit.getSolution();
		response.setContentLength(data.length);
		try
		{
			response.getOutputStream().write(data);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/problem", method = RequestMethod.POST)
	public String editOrDeleteProblem(@RequestParam String op, @RequestParam String id,
			@RequestParam(required = false) String contestName, @ModelAttribute AddProblemForm problemForm,
			HttpSession session, Model model)
	{
		final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final Problem problem = problemDAO.get(Integer.parseInt(id));
		final Integer userID = SessionUtils.getUserIdFromSessionOrNull(session);

		if (op != null && userID != null && userID.equals(problem.getJury().getProfessor().getId()))
			switch (op)
			{
			case "deleteProblem":
				problemDAO.delete(problem);
				break;
			case "editProblem":
				try
				{
					// if (problemForm.getTestcase() != null
					// && StringUtils.compatible(problemForm.getTestcase(),
					// problem.getType()))
					// {
					// problem.setTest(problemForm.getTestcase().getBytes());
					// problem.setType(StringUtils.getExtension(problemForm.getTestcase().getOriginalFilename()));
					// }
					final Contest newContest = contestDAO.getContestByName(problemForm.getContestName());
					problem.setName(problemForm.getName());
					problem.setDescription(problemForm.getDescription());
					if (!problemForm.getDownload().isEmpty())
						problem.setDownload(problemForm.getDownload().getBytes());
					problem.setId_contest(newContest);
					problem.setTimelimit((float) TimeUnit.SECONDS.toMillis(problemForm.getTimeout()));
					problem.setShow_testcase(problemForm.isShow_testcase());

					problemDAO.update(problem);

					final TagDAO tagDAO = (TagDAO) context.getBean("tagDAO");
					tagDAO.deleteAllTagsByProblem(problem.getId_problem());
					_addTags(problemForm, problem, tagDAO);
				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}
				break;
			case "cloneProblem":
				final Contest newContest = contestDAO.getContestByName(contestName);
				problem.setId_contest(newContest);
				problem.setTags(null);
				// TODO Effettuare controlli (es. Evitare più Problemi con lo
				// stesso Nome in un Contest)
				final TagDAO tagDAO = (TagDAO) context.getBean("tagDAO");
				problemDAO.create(problem);

				final List<Tag> problemTags = tagDAO.getAllTagsByProblem(Integer.parseInt(id));
				for (final Tag tag : problemTags)
				{
					tag.setProblem(problem);
					tagDAO.create(tag);
				}

				break;
			default:
				// Operation not supported
				break;
			}
		return "redirect:/myProblems";
	}

	private ArrayList<String> executeZip(Team team, byte[] data, String pathSol)
	{

		final Judge judge = new Judge("java", team.getName());
		///////// SCOMPATTO L'ARCHIVIO CHE SI TROVA IN DATA//////
		InputStream fis = null;
		ZipInputStream zipIs = null;
		ZipEntry zEntry = null;
		boolean found = false;
		final ArrayList<String> info = new ArrayList<>();
		try
		{
			fis = new ByteArrayInputStream(data);
			zipIs = new ZipInputStream(new BufferedInputStream(fis));
			int cont = 0;
			int contWrong = 0;

			while ((zEntry = zipIs.getNextEntry()) != null)
			{
				cont++;
				System.out.println(zEntry.getName());
				final byte[] test = new byte[(int) zEntry.getSize()];
				zipIs.read(test, 0, test.length);
				final String strTestCase = new String(test, StandardCharsets.UTF_8);
				zEntry = zipIs.getNextEntry();
				System.out.println(zEntry.getName());
				final byte[] sol = new byte[(int) zEntry.getSize()];
				zipIs.read(sol);
				final String strSol = new String(sol, StandardCharsets.UTF_8);
				String result = judge.compile("java", team.getName(), pathSol);

				///////////////// Judge every file in zip///////////////
				if (result.equals("COMPILE_SUCCESS"))
				{
					// info.add("Compilation result: "+result);
					result = judge.execute("java", strTestCase, 1000, pathSol);

					if (result.contains("RUN_ERROR") || result.equals("TLE"))
						info.add("execution result: " + result);
					//// controllare l'esecuzione/////
					final String match = judge.match(result, strSol);
					if (!match.equals("RIGHT"))
					{
						contWrong++;
						found = true;
						info.add("Mismatch with: " + zEntry.getName());
					}
				}

			}
			zipIs.close();
			if (!found)
			{
				logger.info("corretto");
				info.add("No mismatch found");
				info.add("All tests passed!!");

				return info;
			}
			else
			{
				logger.info("errato");
				info.add("Test Failed: " + contWrong + "/" + cont);
				return info;
			}

		}
		catch (final FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (final IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	/*
	 * final TypeContext typeContext = TypeContext.getInstance();
	 * typeContext.setStrategy(problemForm.getTestcase().getOriginalFilename());
	 * final Problem problem = typeContext.prepareToSave(problemForm); if
	 * (typeContext.getStatus() == Status.SUCCESS) { final ProblemDAO problemDAO
	 * = (ProblemDAO) context.getBean("problemDAO"); final ContestDAO contestDAO
	 * = (ContestDAO) context.getBean("contestDAO"); final Contest contest =
	 * contestDAO.getContestByName(problemForm.getContestName());
	 * problem.setId_contest(contest); problemDAO.create(problem);
	 *
	 * final TagDAO tagDAO = (TagDAO) context.getBean("tagDAO"); for (final
	 * String tag : tags) { final Tag t = new Tag(); t.setProblem(problem);
	 * t.setValue(tag); tagDAO.create(t); } }
	 */

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String newsubmit(@ModelAttribute SubmitForm submitForm, HttpSession session, Model model) throws IOException
	{
		_setAccountAttribute(session, model);
		System.out.println("********************submit*********************");

		final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
		final Problem problem = problemDAO.get(submitForm.getIdProblem());
		System.out.println(submitForm.getIdProblem());
		System.out.println(submitForm.getTeam());
		System.out.println(submitForm.getSolution().getOriginalFilename());
		final TypeContext typeContext = TypeContext.getInstance();
		typeContext.setStrategy(Engine.BASE_NAME_INPUT + Engine.DOT + problem.getType());
		final DirFilesManager dirFilesManager = new DirFilesManager();
		final Verdict verdict = typeContext.submit(problem, submitForm, dirFilesManager);
		System.out.println(verdict.getStatus());
		SubmissionHandler.save(context, problem, submitForm, verdict, dirFilesManager);
		System.out.println("********************submit*********************");
		return "redirect:/";
	}

	// Vista di un Problema
	// Vista di origine non utilizzata
	// Lista dei problemi già integrata nella Vista dei Contest
	@RequestMapping(value = "/problem", method = RequestMethod.GET)
	public void problemMainView(@RequestParam String op, @RequestParam String id, HttpSession session, Model model,
			HttpServletResponse response)
	{
		_setAccountAttribute(session, model);
		if (op.equals("editProblem"))
		{
			response.setContentType("text/html; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			final ObjectMapper mapper = new ObjectMapper();
			final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
			final Problem problem = problemDAO.get_JoinFetch(Integer.parseInt(id));

			final Set<String> fieldsFilter = new HashSet<>();
			fieldsFilter.add("jury");
			fieldsFilter.add("id_contest");
			fieldsFilter.add("submits");
			fieldsFilter.add("test");
			fieldsFilter.add("type");
			fieldsFilter.add("sol");
			fieldsFilter.add("download");

			logger.info(problem.getId_problem().toString());
			final FilterProvider filters = new SimpleFilterProvider().addFilter("userFilter",
					SimpleBeanPropertyFilter.serializeAllExcept(fieldsFilter));
			try
			{
				mapper.writer(filters).writeValue(response.getOutputStream(), problem);
			}
			catch (final IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			// Non utilizzata
			final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
			final Problem problem = problemDAO.get(Integer.parseInt(id));

			final SubmitDAO submitDAO = (SubmitDAO) context.getBean("submitDAO");
			final List<Submit> submits = submitDAO.getAllSubmitByProblem(problem.getId_problem());

			final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
			final Contest contest = contestDAO.get(problem.getId_contest().getIdcontest());

			final MembershipDAO membershipDAO = (MembershipDAO) context.getBean("membershipDAO");
			final List<Membership> memberships = membershipDAO
					.getTeamByStudent(SessionUtils.getUserIdFromSessionOrNull(session));

			model.addAttribute("memberships", memberships);
			model.addAttribute("problem", problem);
			model.addAttribute("submits", submits);
			model.addAttribute("contest", contest);

			// return "problemview";
		}
	}

	@RequestMapping(value = "/viewSubmits", method = RequestMethod.GET)
	public String viewProblemSubmits(@RequestParam String id, HttpSession session, Model model,
			HttpServletResponse response)
	{
		_setAccountAttribute(session, model);

		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
		final SubmitDAO submitDAO = (SubmitDAO) context.getBean("submitDAO");
		final Problem problem = problemDAO.get(Integer.parseInt(id));

		final List<Submit> submits = submitDAO.getAllSubmitByProblem(problem.getId_problem());
		final Set<Team> teams = new HashSet<>();
		for (final Submit submit : submits)
			teams.add(submit.getTeam());

		final Map<Team, List<Submit>> submitsPerTeam = new HashMap<>();
		for (final Team team : teams)
		{
			final List<Submit> submitsTmp = new ArrayList<>();
			for (final Submit submit : submits)
				if (submit.getTeam().equals(team))
					submitsTmp.add(submit);
			submitsPerTeam.put(team, submitsTmp);
		}

		model.addAttribute("submitsPerTeam", submitsPerTeam);
		return "viewProblemSubmits";
	}

	@RequestMapping(value = "/viewSubmit", method = RequestMethod.GET)
	public String viewSubmit(@RequestParam String submitId, HttpSession session, Model model)
	{
		_setAccountAttribute(session, model);
		final SubmitDAO submitDAO = (SubmitDAO) context.getBean("submitDAO");
		final Submit submit = submitDAO.get(Integer.parseInt(submitId));
		String submitFile = null;
		try
		{
			submitFile = new String(submit.getSolution(), "UTF-8");
		}
		catch (final UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		submitFile = submitFile.replaceAll("<", "&lt;");
		submitFile = submitFile.replaceAll(">", "&gt;");
		model.addAttribute("submit", submit);
		model.addAttribute("submitFile", submitFile);
		model.addAttribute("language", TypeFileExtension.highlight.get(submit.getType()));
		return "viewSubmit";
	}

	// Submit soluzione
	/*
	 * @RequestMapping(value = "/submit", method = RequestMethod.POST) public
	 * String submit(@RequestParam String problemId, HttpSession
	 * session, @RequestParam("team") String team1,
	 *
	 * @RequestParam String path, Model model) throws IOException {
	 * setAccountAttribute(session, model);
	 *
	 * final SubmitDAO submitDAO = (SubmitDAO) context.getBean("submitDAO");
	 *
	 * final TeamDAO teamDAO = (TeamDAO) context.getBean("teamDAO"); final Team
	 * team = teamDAO.getByName(team1);
	 *
	 * final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
	 * final Problem problem = problemDAO.get(Integer.parseInt(problemId));
	 *
	 * Submit submit =
	 * submitDAO.getAllSubmitByProblemAndTeamFake(Integer.parseInt(problemId),
	 * team.getId()); switch (Integer.parseInt(problem.getType())) { case 1: {
	 * String pathSol = path; final byte[] data = problem.getTest(); final
	 * byte[] data2 = problem.getSol();
	 *
	 * pathSol = pathSol.replace("file:///", "/");
	 *
	 * final File fileSolution = new File(pathSol); final byte[] fileData = new
	 * byte[(int) fileSolution.length()];
	 *
	 * pathSol = pathSol.replace("/Main.java", "");
	 *
	 * final Judge judge = new Judge("java", team.getName());
	 *
	 * String result = judge.compile("java", team.getName(), pathSol);
	 *
	 * final String strTestCase = new String(data, StandardCharsets.UTF_8);
	 * final String strSolution = new String(data2, StandardCharsets.UTF_8);
	 *
	 * if (result.equals("COMPILE_SUCCESS")) { result = judge.execute("java",
	 * strTestCase, 1000, pathSol);
	 *
	 * final String match = judge.match(result, strSolution); if
	 * (match.equals("RIGHT")) { final LocalDate localDate = LocalDate.now();
	 * logger.info("corretto"); if (submit != null) { submitDAO.delete(submit);
	 * submit.setIdTeam(team); submit.setProblem(problem);
	 * submit.setInfo(problem.getName());
	 * submit.setDate(DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate)
	 * ); // set the score eventually here submit.setSolution(fileData);
	 * submitDAO.create(submit); return "redirect:/"; } else { submit = new
	 * Submit(); submit.setIdTeam(team); submit.setProblem(problem);
	 * submit.setInfo(problem.getName()); // set the score eventually here
	 * submit.setDate(DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate)
	 * ); submit.setSolution(fileData); submitDAO.create(submit); return
	 * "redirect:/"; } } else { logger.info("errato"); return "redirect:/"; } }
	 * else return "redirect:/"; }
	 *
	 * case 2: { String pathSol = path; final byte[] data = problem.getTest();
	 *
	 * pathSol = pathSol.replace("file:///", "/");
	 *
	 * final File fileSolution = new File(pathSol); final byte[] fileData = new
	 * byte[(int) fileSolution.length()];
	 *
	 * pathSol = pathSol.replace("/Main.java", "");
	 *
	 * final ArrayList<String> info = executeZip(team, data, pathSol);
	 * model.addAttribute("problem", problem); model.addAttribute("infos",
	 * info); model.addAttribute("team", team); model.addAttribute("path",
	 * path);
	 *
	 * return "addProblemConfirmation"; }
	 *
	 * case 3: { final LocalDate localDate = LocalDate.now(); String pathSol =
	 * path; final byte[] data2 = problem.getSol();
	 *
	 * pathSol = pathSol.replace("file:///", "/");
	 *
	 * final File fileSolution = new File(pathSol); final byte[] fileData = new
	 * byte[(int) fileSolution.length()];
	 *
	 * pathSol = pathSol.replace("/Main.java", "");
	 *
	 * final Judge judge = new Judge("java", team.getName());
	 *
	 * String result = judge.compile("java", team.getName(), pathSol);
	 *
	 * final String strSolution = new String(data2, StandardCharsets.UTF_8);
	 *
	 * if (result.equals("COMPILE_SUCCESS")) { result = judge.execute("java",
	 * "", 1000, pathSol);
	 *
	 * final String match = judge.match(result, strSolution); if
	 * (match.equals("RIGHT")) { logger.info("corretto"); if (submit != null) {
	 * submitDAO.delete(submit); submit.setIdTeam(team);
	 * submit.setProblem(problem); submit.setInfo(problem.getName()); // set the
	 * score eventually here
	 * submit.setDate(DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate)
	 * ); submit.setSolution(fileData); submitDAO.create(submit); return
	 * "problemview"; } else { submit = new Submit(); submit.setIdTeam(team);
	 * submit.setProblem(problem); submit.setInfo(problem.getName()); // set the
	 * score eventually here
	 * submit.setDate(DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate)
	 * ); submit.setSolution(fileData); submitDAO.create(submit); return
	 * "problemview"; } } else { logger.info("errato"); return "redirect:/"; } }
	 * else return "redirect:/"; }
	 *
	 * case 4: { final LocalDate localDate = LocalDate.now(); String pathSol =
	 * path; final byte[] data = problem.getTest(); final byte[] data2 =
	 * problem.getSol();
	 *
	 * pathSol = pathSol.replace("file:///", "/");
	 *
	 * final File fileSolution = new File(pathSol); final byte[] fileData = new
	 * byte[(int) fileSolution.length()];
	 *
	 * pathSol = pathSol.replace("/Main.java", "");
	 *
	 * final Judge judge = new Judge("java", team.getName());
	 *
	 * String result = judge.compile("java", team.getName(), pathSol);
	 *
	 * final String strTestCase = new String(data, StandardCharsets.UTF_8);
	 * System.out.println("Gli passo questo test case: " + strTestCase); final
	 * String strSolution = new String(data2, StandardCharsets.UTF_8);
	 *
	 * if (result.equals("COMPILE_SUCCESS")) { result = judge.execute("java",
	 * strTestCase, 1000, pathSol);
	 *
	 * final String match = judge.match(result, strSolution); if
	 * (match.equals("RIGHT")) { logger.info("corretto"); if (submit != null) {
	 * submitDAO.delete(submit); submit.setIdTeam(team);
	 * submit.setProblem(problem); submit.setInfo(problem.getName());
	 * submit.setDate(DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate)
	 * ); // set the score eventually here submit.setSolution(fileData);
	 * submitDAO.create(submit); return "redirect:/"; } else { submit = new
	 * Submit(); submit.setIdTeam(team); submit.setProblem(problem);
	 * submit.setInfo(problem.getName()); // set the score eventually here
	 * submit.setDate(DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate)
	 * ); submit.setSolution(fileData); submitDAO.create(submit); return
	 * "redirect:/"; } } else { logger.info("errato"); return "redirect:/"; } }
	 * else return "redirect:/"; }
	 *
	 * } return "redirect:/";
	 *
	 * }
	 */
}