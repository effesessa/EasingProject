package it.unical.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import it.unical.dao.AnswerDAO;
import it.unical.dao.ContestDAO;
import it.unical.dao.QuestionDAO;
import it.unical.dao.QuestionTagDAO;
import it.unical.dao.QuizDAO;
import it.unical.dao.SubmitAnswerDAO;
import it.unical.dao.SubmitQuizDAO;
import it.unical.dao.TeamDAO;
import it.unical.dao.UserDAO;
import it.unical.entities.Answer;
import it.unical.entities.Contest;
import it.unical.entities.Jury;
import it.unical.entities.Question;
import it.unical.entities.Question.Type;
import it.unical.entities.QuestionTag;
import it.unical.entities.Quiz;
import it.unical.entities.SubmitAnswer;
import it.unical.entities.SubmitQuiz;
import it.unical.entities.Team;
import it.unical.entities.User;
import it.unical.forms.AddQuestionsForm;
import it.unical.forms.AddQuizForm;
import it.unical.forms.RandomQuestionForm;
import it.unical.forms.SubmitQuizForm;
import it.unical.utils.HibernateAwareObjectMapper;
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

	@RequestMapping(value = "/addQuestions", method = RequestMethod.POST)
	public String addQuestions(final HttpSession session, @ModelAttribute AddQuestionsForm form, final Model model)
	{

		System.out.println("DOMANDE:");
		for (final String string : form.getQuestions())
			System.out.println(string);
		System.out.println("PUNTI:");
		for (final Map.Entry<String, Integer> entry : form.getQuestion_points().entrySet())
			System.out.println(entry.getKey() + "/" + entry.getValue());
		System.out.println("TIPOLOGIA:");
		for (final Map.Entry<String, String> entry : form.getQuestion_types().entrySet())
			System.out.println(entry.getKey() + "/" + entry.getValue());
		if (form.getQuestion_correctAnswer() != null)
		{
			System.out.println("RISPOSTE CORRETTE:");
			for (final Map.Entry<String, String> entry : form.getQuestion_correctAnswer().entrySet())
				System.out.println(entry.getKey() + "/" + entry.getValue());
			System.out.println("RISPOSTE");
			for (final Map.Entry<String, List<String>> entry : form.getQuestion_answers().entrySet())
				System.out.println(entry.getKey() + "/" + entry.getValue());
		}
		System.out.println("TAGS");
		for (final Map.Entry<String, String> entry : form.getQuestion_tags().entrySet())
			System.out.println(entry.getKey() + "/" + entry.getValue());

		_setAccountAttribute(session, model);
		final AnswerDAO answerDAO = (AnswerDAO) context.getBean("answerDAO");
		final QuestionDAO questionDAO = (QuestionDAO) context.getBean("questionDAO");
		final QuestionTagDAO questionTagDAO = (QuestionTagDAO) context.getBean("questionTagDAO");

		final List<Question> questions = new ArrayList<>();
		// for (final String questionText : form.getQuestions())
		// {
		// final Question question = new Question();
		// question.setText(questionText);
		// question.setPoints(form.getQuestion_points().get(questionText));
		// System.out.println();
		// System.out.println(questionText);
		// question.setType(getType(form.getQuestion_types().get(questionText)));
		// questionDAO.create(question);
		// questions.add(question);
		// }

		for (int i = 0; i < form.getQuestions().size(); i++)
		{
			final Question question = new Question();
			final String questionText = form.getQuestions().get(i);
			final String questionKey = "question" + (i + 1);
			question.setText(questionText);
			question.setPoints(form.getQuestion_points().get(questionKey));
			System.out.println();
			System.out.println(questionText);
			question.setType(getType(form.getQuestion_types().get(questionKey)));
			questionDAO.create(question);
			questions.add(question);
		}

		int i = 1;
		final Set<Answer> answers = new LinkedHashSet<>();
		for (final Question question : questions)
		{
			final String questionKey = "question" + i;

			if (form.getQuestion_answers() != null && form.getQuestion_answers().containsKey(questionKey))
			{
				for (final String textAnswer : form.getQuestion_answers().get(questionKey))
				{
					Answer answer = new Answer();
					answer.setText(textAnswer);
					if (!answerDAO.exists(textAnswer))
						answerDAO.create(answer);
					else
						answer = answerDAO.getByText(textAnswer);
					answers.add(answer);
				}
				if (form.getQuestion_correctAnswer().containsKey(questionKey))
				{
					final Answer correctAnswer = answerDAO.getByText(form.getQuestion_correctAnswer().get(questionKey));
					question.setCorrectAnswer(correctAnswer);
				}
				question.setAnswers(answers);
			}
			if (form.getQuestion_tags().containsKey(questionKey))
			{
				final String tagsToSplitted = form.getQuestion_tags().get(questionKey);
				final Set<QuestionTag> tags = new LinkedHashSet<>();
				for (final String questionTagValue : tagsToSplitted.split(","))
				{
					if (questionTagValue.equals(""))
						continue;
					final QuestionTag questionTag = new QuestionTag();
					questionTag.setQuestion(question);
					questionTag.setValue(questionTagValue);
					questionTagDAO.create(questionTag);
					tags.add(questionTag);
				}
				question.setTags(tags);
			}
			questionDAO.update(question);
			i++;
		}
		return "redirect:/";
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
		final QuestionTagDAO questionTagDAO = (QuestionTagDAO) context.getBean("questionTagDAO");

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
		int key = 1;
		for (int i = 0; i < addQuizForm.getQuestions().size();)
		{
			// final String questionKey = "question" + (i + 1);
			final String questionKey = "question" + key;

			if (addQuizForm.getQuestion_types().containsKey(questionKey))
			{
				final Question question = new Question();
				question.setPoints(addQuizForm.getPoints().get(i));
				question.setText(addQuizForm.getQuestions().get(i));
				question.setType(getType(addQuizForm.getQuestion_types().get(questionKey)));
				question.setQuizzes(quizes);
				questionDAO.create(question);
				if (addQuizForm.getQuestions_tags() != null || addQuizForm.getQuestions_tags().isEmpty())
					if (questionDAO.exists(addQuizForm.getQuestions().get(i)))
					{
						final Question existingQuestion = questionDAO
								.getDistinctByText(addQuizForm.getQuestions().get(i));
						for (final QuestionTag questionTag : existingQuestion.getTags())
						{
							final QuestionTag copyQuestionTag = new QuestionTag();
							copyQuestionTag.setQuestion(question);
							copyQuestionTag.setValue(questionTag.getValue());
							questionTagDAO.create(copyQuestionTag);
							question.getTags().add(copyQuestionTag);
						}
					}
				questionDAO.update(question);
				questions.add(question);
				i++;
			}
			key++;
		}
		// update quiz
		quiz.setQuestions(questions);
		quizDAO.update(quiz);

		// create answers, tags and update questions
		int i = 1;
		for (final Question question : questions)
		{
			String questionKey = "question" + i;
			while (!addQuizForm.getQuestion_types().containsKey(questionKey))
			{
				i++;
				questionKey = "question" + i;
			}
			final Set<Answer> answers = new LinkedHashSet<>();
			if (addQuizForm.getQuestions_answers() != null
					&& addQuizForm.getQuestions_answers().containsKey(questionKey))
			{
				for (final String textAnswer : addQuizForm.getQuestions_answers().get(questionKey))
				{
					Answer answer = new Answer();
					answer.setText(textAnswer);
					if (!answerDAO.exists(textAnswer))
						answerDAO.create(answer);
					else
						answer = answerDAO.getByText(textAnswer);
					answers.add(answer);
				}
				if (addQuizForm.getCorrectAnswers().containsKey(questionKey))
				{
					final Answer correctAnswer = answerDAO.getByText(addQuizForm.getCorrectAnswers().get(questionKey));
					question.setCorrectAnswer(correctAnswer);
				}
				question.setAnswers(answers);
				questionDAO.update(question);
			}

			if (addQuizForm.getQuestions_tags().containsKey(questionKey))
			{
				final String tagsToSplitted = addQuizForm.getQuestions_tags().get(questionKey);
				final Set<QuestionTag> tags = new LinkedHashSet<>();
				for (final String questionTagValue : tagsToSplitted.split(","))
				{
					if (questionTagValue.equals(""))
						continue;
					final QuestionTag questionTag = new QuestionTag();
					questionTag.setQuestion(question);
					questionTag.setValue(questionTagValue);
					questionTagDAO.create(questionTag);
					tags.add(questionTag);
				}
				question.setTags(tags);
				questionDAO.update(question);
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
			final QuestionTagDAO tagDAO = (QuestionTagDAO) context.getBean("questionTagDAO");
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

	@RequestMapping(value = "/quiz", method = RequestMethod.POST)
	public String editOrDeleteOrCloneQuiz(@RequestParam String op, @RequestParam String id,
			@RequestParam(required = false) String contestName, @ModelAttribute AddQuizForm form, HttpSession session,
			Model model)
	{
		final QuizDAO quizDAO = (QuizDAO) context.getBean("quizDAO");
		final QuestionDAO questionDAO = (QuestionDAO) context.getBean("questionDAO");
		final AnswerDAO answerDAO = (AnswerDAO) context.getBean("answerDAO");
		final QuestionTagDAO questionTagDAO = (QuestionTagDAO) context.getBean("questionTagDAO");
		final SubmitQuizDAO submitQuizDAO = (SubmitQuizDAO) context.getBean("submitQuizDAO");
		final SubmitAnswerDAO submitAnswerDAO = (SubmitAnswerDAO) context.getBean("submitAnswerDAO");

		final Integer userID = SessionUtils.getUserIdFromSessionOrNull(session);
		if (op != null && userID != null)
		{ // userID.equals(problem.getJury().getProfessor().getId()
			final Quiz quiz = quizDAO.get(Integer.parseInt(id));
			switch (op)
			{
			case "deleteQuiz":
				final Set<Question> questions = quiz.getQuestions();

				for (final Question question : questions)
				{
					System.out.println("question:" + question.getText());
					for (final QuestionTag questionTag : question.getTags())
					{
						System.out.println("questionTag:" + questionTag.getValue());
						questionTagDAO.delete(questionTag);
					}
				}

				final List<SubmitQuiz> submitQuizzes = submitQuizDAO.getAllByQuiz(quiz.getId());
				for (final SubmitQuiz submitQuiz : submitQuizzes)
				{
					final List<SubmitAnswer> submitAnswers = submitAnswerDAO.getBySubmitQuiz(submitQuiz.getId());
					for (final SubmitAnswer submitAnswer : submitAnswers)
						submitAnswerDAO.delete(submitAnswer);
					submitQuizDAO.delete(submitQuiz);
				}
				quizDAO.delete(quiz);
				for (final Question question : questions)
				{
					final List<Answer> answers = answerDAO.getOrphanAnswersByQuestion(question.getId());
					questionDAO.delete(question);
					for (final Answer answer : answers)
					{
						System.out.println("answer:" + answer.getText());
						answerDAO.delete(answer);
					}
				}
				break;
			default:
				break;
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/myQuizzes", method = RequestMethod.GET)
	public String getAllQuizzes(final HttpSession session, final Model model)
	{
		_setAccountAttribute(session, model);
		final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		final QuizDAO quizDAO = (QuizDAO) context.getBean("quizDAO");
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final User user = userDAO.get(SessionUtils.getUserIdFromSessionOrNull(session));
		if (user != null && user.isProfessor())
		{
			final Map<Contest, List<Quiz>> contestQuizzesMap = new HashMap<>();
			final List<Contest> contests = contestDAO.getContestsByProfessor(user.getId());
			for (final Contest contest : contests)
			{
				final Jury jury = contest.getJury();
				if (jury.getProfessor().getId().equals(user.getId()))
				{
					final List<Quiz> quizzes = quizDAO.getAllQuizByContest(contest.getIdcontest());
					contestQuizzesMap.put(contest, quizzes);
				}
			}
			model.addAttribute("contestQuizzesMap", contestQuizzesMap);
			return "myQuizzes";
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
				final List<SubmitQuiz> submitQuizzes = submitQuizDAO.getAllByQuiz(quiz);
				final Map<SubmitQuiz, List<SubmitAnswer>> submitQuizSubmitAnswersMap = new HashMap<>();
				for (final SubmitQuiz submitQuiz : submitQuizzes)
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

	@RequestMapping(value = "/correctQuizzes", method = RequestMethod.GET)
	public String getAllSubmitToBeCorrection(final HttpSession session, final Model model)
	{
		_setAccountAttribute(session, model);
		final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		final User user = userDAO.get(SessionUtils.getUserIdFromSessionOrNull(session));
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final SubmitQuizDAO submitQuizDAO = (SubmitQuizDAO) context.getBean("submitQuizDAO");
		final SubmitAnswerDAO submitAnswerDAO = (SubmitAnswerDAO) context.getBean("submitAnswerDAO");
		final QuizDAO quizDAO = (QuizDAO) context.getBean("quizDAO");

		final Map<Contest, List<Quiz>> contestQuizzesMap = new HashMap<>();
		final Map<Quiz, List<SubmitQuiz>> quizSubmitQuizzesMap = new HashMap<>();
		final Map<SubmitQuiz, List<SubmitAnswer>> submitQuizSubmitAnswersMap = new HashMap<>();

		if (user != null && user.isProfessor())
		{
			final List<Contest> contests = contestDAO.getContestsByProfessor(user.getId());
			for (final Contest contest : contests)
			{
				final Jury jury = contest.getJury();
				if (jury.getProfessor().getId().equals(user.getId()))
				{
					final List<Quiz> quizzes = quizDAO.getAllQuizByContest(contest.getIdcontest());
					contestQuizzesMap.put(contest, quizzes);
					for (final Quiz quiz : quizzes)
					{
						final List<SubmitQuiz> submitQuizzes = submitQuizDAO.getAllToBeCorrectionByQuiz(quiz);
						quizSubmitQuizzesMap.put(quiz, submitQuizzes);
						for (final SubmitQuiz submitQuiz : submitQuizzes)
						{
							final List<SubmitAnswer> submitAnswers = submitAnswerDAO.getBySubmitQuiz(submitQuiz);
							submitQuizSubmitAnswersMap.put(submitQuiz, submitAnswers);
						}
					}

				}
			}
			model.addAttribute("contestQuizzesMap", contestQuizzesMap);
			model.addAttribute("quizSubmitQuizzesMap", quizSubmitQuizzesMap);
			model.addAttribute("submitQuizSubmitAnswersMap", submitQuizSubmitAnswersMap);
			return "correctQuizzes";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/generateQuestions", method = RequestMethod.POST)
	public void getRandomQuestions(final HttpSession session, @RequestBody RandomQuestionForm randomQuestionForm,
			final Model model, HttpServletResponse response)
	{
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		// System.out.println("=============================");
		// System.out.println("NUMERO DOMANDE");
		// for (final Integer number :
		// randomQuestionForm.getNumberOfQuestions())
		// System.out.println(number);
		// System.out.println("TAGS");
		// for (final String number : randomQuestionForm.getQuestionTagValues())
		// System.out.println(number);
		// System.out.println("=============================");
		final ObjectMapper mapper = new HibernateAwareObjectMapper();
		final QuestionDAO questionDAO = (QuestionDAO) context.getBean("questionDAO");
		final AnswerDAO answerDAO = (AnswerDAO) context.getBean("answerDAO");
		final Map<String, List<Answer>> questionAnswersMap = new HashMap<>();

		List<Question> questions = new ArrayList<>();
		for (int i = 0; i < randomQuestionForm.getQuestionTagValues().size(); i++)
		{
			questions = questionDAO.getRandomQuestions(randomQuestionForm.getQuestionTagValues().get(i),
					randomQuestionForm.getNumberOfQuestions().get(i));
			for (final Question question : questions)
			{
				final List<Answer> answers = answerDAO.getAnswersByQuestion(question.getId());
				questionAnswersMap.put(question.toString(), answers);
			}
		}

		final Set<String> fieldsFilter = new HashSet<>();
		fieldsFilter.add("quizzes");
		fieldsFilter.add("answers");
		fieldsFilter.add("questions");
		// fieldsFilter.add("tags");
		fieldsFilter.add("correctAnswer");
		final FilterProvider filters = new SimpleFilterProvider().addFilter("questionFilter",
				SimpleBeanPropertyFilter.serializeAllExcept(fieldsFilter));

		try
		{
			mapper.writer(filters).writeValue(response.getOutputStream(), questionAnswersMap);
		}
		catch (final IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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