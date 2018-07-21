package it.unical.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unical.dao.AnswerDAO;
import it.unical.dao.ContestDAO;
import it.unical.dao.QuestionDAO;
import it.unical.dao.QuizDAO;
import it.unical.dao.QuizTagDAO;
import it.unical.dao.SubmitAnswerDAO;
import it.unical.dao.SubmitQuizDAO;
import it.unical.dao.TeamDAO;
import it.unical.dao.UserDAO;
import it.unical.entities.Answer;
import it.unical.entities.Contest;
import it.unical.entities.Jury;
import it.unical.entities.Question;
import it.unical.entities.Question.Type;
import it.unical.entities.Quiz;
import it.unical.entities.QuizTag;
import it.unical.entities.SubmitAnswer;
import it.unical.entities.SubmitQuiz;
import it.unical.entities.Team;
import it.unical.entities.User;
import it.unical.forms.AddQuizForm;
import it.unical.forms.SubmitQuizForm;
import it.unical.utils.SessionUtils;

@Controller
public class QuizController
{
	private static final Logger logger = LoggerFactory.getLogger(LogInController.class);

	@Autowired
	private WebApplicationContext context;

	private void _setAccountAttribute(final HttpSession session, final Model model)
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

	@RequestMapping(value = "/addQuiz", method = RequestMethod.POST)
	public String addQuiz(final HttpSession session, @ModelAttribute AddQuizForm addQuizForm, final Model model,
			HttpServletResponse response)
	{
		_setAccountAttribute(session, model);
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final QuizDAO quizDAO = (QuizDAO) context.getBean("quizDAO");
		final QuestionDAO questionDAO = (QuestionDAO) context.getBean("questionDAO");
		final AnswerDAO answerDAO = (AnswerDAO) context.getBean("answerDAO");
		final Contest contest = contestDAO.getContestByName(addQuizForm.getContestName());
		final QuizTagDAO quizTagDAO = (QuizTagDAO) context.getBean("quizTagDAO");

		// create quiz
		final Quiz quiz = new Quiz();
		quiz.setContest(contest);
		quiz.setName(addQuizForm.getQuizName());
		quiz.setPoints(addQuizForm.getQuizPoints());
		quizDAO.create(quiz);
		// create questions
		final Set<Question> questions = new LinkedHashSet<>();
		final List<Quiz> quizes = new ArrayList<>();
		quizes.add(quiz);
		for (int i = 0; i < addQuizForm.getQuestions().size(); i++)
		{
			final String questionKey = "question" + (i + 1);
			final Question question = new Question();
			question.setPoints(addQuizForm.getPoints().get(i));
			question.setText(addQuizForm.getQuestions().get(i));
			question.setType(getType(addQuizForm.getQuestion_types().get(questionKey)));
			question.setQuizs(quizes);
			questionDAO.create(question);
			questions.add(question);
		}
		// update quiz
		quiz.setQuestions(questions);
		quizDAO.update(quiz);

		// create answers, tags and update questions
		int i = 1;
		for (final Question question : questions)
		{
			final String questionKey = "question" + i;
			final Set<Answer> answers = new LinkedHashSet<>();
			if (addQuizForm.getQuestions_answers().containsKey(questionKey))
			{
				for (final String textAnswer : addQuizForm.getQuestions_answers().get(questionKey))
				{
					Answer answer = new Answer();
					answer.setText(textAnswer);
					if (!answerDAO.exists(textAnswer))
						answerDAO.create(answer);
					else
					{
						answer = answerDAO.getByText(textAnswer);
						question.setCorrectAnswer(answer);
					}
					answers.add(answer);
				}
				question.setAnswers(answers);
				questionDAO.update(question);
			}

			if (addQuizForm.getQuestions_tags().containsKey(questionKey))
			{
				final String tagsToSplitted = addQuizForm.getQuestions_tags().get(questionKey);
				for (final String quizTagValue : tagsToSplitted.split(","))
				{
					final QuizTag quizTag = new QuizTag();
					quizTag.setQuiz(quiz);
					quizTag.setValue(quizTagValue);
					quizTagDAO.create(quizTag);
				}
			}
			i++;
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/addQuizFake", method = RequestMethod.POST)
	public String addQuizFake(final HttpSession session, @ModelAttribute AddQuizForm quiz, final Model model)
	{
		System.out.println(quiz.getContestName());
		System.out.println(quiz.getQuizName());
		System.out.println(quiz.getQuizPoints());
		System.out.println("DOMANDE:");
		for (final String string : quiz.getQuestions())
			System.out.println(string);
		System.out.println("PUNTI:");
		for (final Integer string : quiz.getPoints())
			System.out.println(string);
		System.out.println("TIPOLOGIA:");
		for (final Map.Entry<String, String> entry : quiz.getQuestion_types().entrySet())
			System.out.println(entry.getKey() + "/" + entry.getValue());
		System.out.println("RISPOSTE CORRETTE:");
		for (final Map.Entry<String, String> entry : quiz.getCorrectAnswers().entrySet())
			System.out.println(entry.getKey() + "/" + entry.getValue());
		System.out.println("RISPOSTE");
		for (final Map.Entry<String, List<String>> entry : quiz.getQuestions_answers().entrySet())
			System.out.println(entry.getKey() + "/" + entry.getValue());
		System.out.println("TAGS");
		for (final Map.Entry<String, String> entry : quiz.getQuestions_tags().entrySet())
			System.out.println(entry.getKey() + "/" + entry.getValue());
		return "redirect:/";
	}

	@RequestMapping(value = "/quiz", method = RequestMethod.GET)
	public void ajaxRequest(@RequestParam String req, HttpSession session, Model model, HttpServletResponse response)
	{
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		final ObjectMapper mapper = new ObjectMapper();
		final int userID = SessionUtils.getUserIdFromSessionOrNull(session);
		if (SessionUtils.isLoggedIn(session) && req.equals("tags"))
		{
			final QuizTagDAO tagDAO = (QuizTagDAO) context.getBean("quizTagDAO");
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
	}

	@RequestMapping(value = "/createQuiz", method = RequestMethod.GET)
	public String createQuiz(HttpSession session, Model model)
	{
		_setAccountAttribute(session, model);
		final User user = (User) model.asMap().get("user");

		if (user == null || !user.isProfessor())
			return "redirect:/";
		else
			return "createQuiz";
	}

	@RequestMapping(value = "/myQuizs", method = RequestMethod.GET)
	public String getAllQuizs(final HttpSession session, final Model model)
	{
		_setAccountAttribute(session, model);
		final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		final QuizDAO quizDAO = (QuizDAO) context.getBean("quizDAO");
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final User user = userDAO.get(SessionUtils.getUserIdFromSessionOrNull(session));
		if (user != null && user.isProfessor())
		{
			final Map<Contest, List<Quiz>> contestQuizsMap = new HashMap<>();
			final List<Contest> contests = contestDAO.getContestsByProfessor(user.getId());
			for (final Contest contest : contests)
			{
				final Jury jury = contest.getJury();
				if (jury.getProfessor().getId().equals(user.getId()))
				{
					final List<Quiz> quizs = quizDAO.getAllQuizByContest(contest.getIdcontest());
					contestQuizsMap.put(contest, quizs);
				}
			}
			model.addAttribute("contestQuizsMap", contestQuizsMap);
			return "myQuizs";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/quizSubmits", method = RequestMethod.GET)
	public String getAllSubmitsByQuiz(@RequestParam String idQuiz, final HttpSession session, final Model model)
	{
		_setAccountAttribute(session, model);
		final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		final User user = userDAO.get(SessionUtils.getUserIdFromSessionOrNull(session));
		if (user != null && user.isProfessor())
		{
			final QuizDAO quizDAO = (QuizDAO) context.getBean("quizDAO");
			final Quiz quiz = quizDAO.get(Integer.parseInt(idQuiz));
			if (quiz.getContest().getJury().getProfessor().getId().equals(user.getId()))
			{
				final SubmitQuizDAO submitQuizDAO = (SubmitQuizDAO) context.getBean("submitQuizDAO");
				final SubmitAnswerDAO submitAnswerDAO = (SubmitAnswerDAO) context.getBean("submitAnswerDAO");
				final List<SubmitQuiz> submitQuizs = submitQuizDAO.getAllByQuiz(quiz);
				final Map<SubmitQuiz, List<SubmitAnswer>> submitQuizSubmitAnswersMap = new HashMap<>();
				for (final SubmitQuiz submitQuiz : submitQuizs)
				{
					final List<SubmitAnswer> submitAnswers = submitAnswerDAO.getBySubmitQuiz(submitQuiz);
					submitQuizSubmitAnswersMap.put(submitQuiz, submitAnswers);
				}
				model.addAttribute("submitQuizSubmitAnswersMap", submitQuizSubmitAnswersMap);
				return "viewQuizSubmits";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/toBeCorrection", method = RequestMethod.GET)
	public String getAllSubmitToBeCorrection(@RequestParam String idProfessor, final HttpSession session,
			final Model model)
	{
		_setAccountAttribute(session, model);
		final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		final User user = userDAO.get(SessionUtils.getUserIdFromSessionOrNull(session));
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final SubmitQuizDAO submitQuizDAO = (SubmitQuizDAO) context.getBean("submitQuizDAO");
		final SubmitAnswerDAO submitAnswerDAO = (SubmitAnswerDAO) context.getBean("submitAnswerDAO");
		final QuizDAO quizDAO = (QuizDAO) context.getBean("quizDAO");

		final Map<Contest, List<Quiz>> contestQuizsMap = new HashMap<>();
		final Map<Quiz, List<SubmitQuiz>> quizSubmitQuizsMap = new HashMap<>();
		final Map<SubmitQuiz, List<SubmitAnswer>> submitQuizSubmitAnswersMap = new HashMap<>();

		if (user != null && user.isProfessor())
		{
			final List<Contest> contests = contestDAO.getContestsByProfessor(Integer.parseInt(idProfessor));
			for (final Contest contest : contests)
			{
				final Jury jury = contest.getJury();
				if (jury.getProfessor().getId().equals(Integer.parseInt(idProfessor)))
				{
					final List<Quiz> quizs = quizDAO.getAllQuizByContest(contest.getIdcontest());
					contestQuizsMap.put(contest, quizs);
					for (final Quiz quiz : quizs)
					{
						final List<SubmitQuiz> submitQuizs = submitQuizDAO.getAllToBeCorrectionByQuiz(quiz);
						quizSubmitQuizsMap.put(quiz, submitQuizs);
						for (final SubmitQuiz submitQuiz : submitQuizs)
						{
							final List<SubmitAnswer> submitAnswers = submitAnswerDAO.getBySubmitQuiz(submitQuiz);
							submitQuizSubmitAnswersMap.put(submitQuiz, submitAnswers);
						}
					}

				}
			}
			model.addAttribute("contestQuizsMap", contestQuizsMap);
			model.addAttribute("quizSubmitQuizsMap", quizSubmitQuizsMap);
			model.addAttribute("submitQuizSubmitAnswersMap", submitQuizSubmitAnswersMap);
			return "quizsubmitsToBeCorrection";
		}
		return "redirect:/";
	}

	private Question.Type getType(String typeFrontEnd)
	{
		switch (typeFrontEnd)
		{
		case "closed":
			return Type.MULTIPLE;
		case "open":
			return Type.OPEN;
		default:
			return null;
		}
	}

	@RequestMapping(value = "/showQuiz", method = RequestMethod.GET)
	public String showQuiz(@RequestParam String quizName, final HttpSession session, final Model model)
	{
		// if you want pass quizId, change @RequestParam String quizId and use
		// quizDAO.get(Integer.parseInt(quizId));
		_setAccountAttribute(session, model);
		final QuizDAO quizDAO = (QuizDAO) context.getBean("quizDAO");
		final Quiz quiz = quizDAO.getByName(quizName); // quizDAO.get(Integer.parseInt(quizId));
		model.addAttribute("questions", quiz.getQuestions());

		// ALE: the answers you should get from the questions objects without
		// problems but
		// if there are problems or you are more comfortable, then, it uncomment
		// below to pass answers directly

		/*
		 * Map<Question,Set<Answer>> question_answers = new HashMap<>(); for
		 * (Question question : quiz.getQuestions()) {
		 * question_answers.put(question, question.getAnswers()); }
		 * model.addAttribute("question_answers", question_answers);
		 */

		return "nomePaginaPerMostrareilQuiz";
	}

	@RequestMapping(value = "/submitQuiz", method = RequestMethod.POST)
	public String submitQuiz(final HttpSession session, @ModelAttribute SubmitQuizForm submitQuizForm,
			final Model model)
	{
		final QuizDAO quizDAO = (QuizDAO) context.getBean("quizDAO");
		final SubmitQuizDAO submitQuizDAO = (SubmitQuizDAO) context.getBean("submitQuizDAO");
		final SubmitAnswerDAO submitAnswerDAO = (SubmitAnswerDAO) context.getBean("submitAnswerDAO");
		final TeamDAO teamDAO = (TeamDAO) context.getBean("teamDAO");
		final Quiz quiz = quizDAO.get(submitQuizForm.getQuizID());
		final Team team = teamDAO.getByName(submitQuizForm.getTeamName());

		final SubmitQuiz submitQuiz = new SubmitQuiz();
		submitQuiz.setQuiz(quiz);
		submitQuiz.setTeam(team);
		submitQuizDAO.create(submitQuiz);
		final Set<Question> questions = quiz.getQuestions();

		int multipleScore = 0;
		boolean correction = false;
		for (final Map.Entry<String, String> entry : submitQuizForm.getQuestion_answer().entrySet())
		{
			Question findQuestion = null;
			for (final Question question : questions)
				if (question.getId().equals(Integer.parseInt(entry.getKey())))
				{
					findQuestion = question;
					break;
				}
			final SubmitAnswer submitAnswer = new SubmitAnswer();
			submitAnswer.setSubmitQuiz(submitQuiz);
			submitAnswer.setQuestion(findQuestion);
			if (findQuestion.getType() == Question.Type.OPEN)
			{
				submitAnswer.setOpenAnswer(entry.getValue());
				correction = true;
			}
			else
			{
				final Set<Answer> answers = findQuestion.getAnswers();
				for (final Answer answer : answers)
					if (answer.getId().equals(Integer.parseInt(entry.getValue())))
					{
						submitAnswer.setAnswer(answer);
						if (answer.getId().equals(findQuestion.getCorrectAnswer().getId()))
						{
							multipleScore += findQuestion.getPoints();
							submitAnswer.setPoints(findQuestion.getPoints());
						}
						break;
					}
			}
			submitAnswerDAO.create(submitAnswer);
		}
		submitQuiz.setCorrection(correction);
		submitQuiz.setMultipleScore(multipleScore);
		submitQuiz.setTotalScore(submitQuiz.getTotalScore() + multipleScore);
		submitQuizDAO.update(submitQuiz);
		return "redirect:/";
	}

}