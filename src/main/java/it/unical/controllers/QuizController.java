package it.unical.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

import it.unical.dao.AnswerDAO;
import it.unical.dao.ContestDAO;
import it.unical.dao.QuestionDAO;
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
import it.unical.entities.Quiz;
import it.unical.entities.SubmitAnswer;
import it.unical.entities.SubmitQuiz;
import it.unical.entities.Team;
import it.unical.entities.User;
import it.unical.forms.QuizDTO;
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
	public String addQuiz(final HttpSession session, @RequestBody QuizDTO quizDTO, final Model model,
			HttpServletResponse response)
	{
		_setAccountAttribute(session, model);
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final QuizDAO quizDAO = (QuizDAO) context.getBean("quizDAO");
		final QuestionDAO questionDAO = (QuestionDAO) context.getBean("questionDAO");
		final AnswerDAO answerDAO = (AnswerDAO) context.getBean("answerDAO");
		final Contest contest = contestDAO.getContestByName(quizDTO.getContestName());
		// create quiz
		final Quiz quiz = new Quiz();
		quiz.setContest(contest);
		quiz.setName(quizDTO.getQuizName());
		quiz.setPoints(quizDTO.getQuizPoints());
		quizDAO.create(quiz);
		// create questions
		final Set<Question> questions = new HashSet<>();
		final List<Quiz> quizs = new ArrayList<>();
		quizs.add(quiz);
		for (int i = 0; i < quizDTO.getQuestions().size(); i++)
		{
			final Question question = new Question();
			question.setPoints(quizDTO.getPoints().get(i));
			question.setText(quizDTO.getQuestions().get(i));
			question.setType(getType(quizDTO.getTypes().get(i)));
			question.setQuizs(quizs);
			questionDAO.create(question);
			questions.add(question);
		}
		// update quiz
		quiz.setQuestions(questions);
		quizDAO.update(quiz);

		int indexCorrectAnswer = -1;
		for (final Entry<String, List<String>> entry : quizDTO.getQuestions_answers().entrySet())
		{
			Question findQuestion = null;
			int i = 0;
			for (final Question question : questions)
			{
				if (question.getText().equals(entry.getKey()))
				{
					findQuestion = question;
					indexCorrectAnswer = i;
					break;
				}
				i++;
			}
			// for (int i = 0; i < questions.size(); i++)
			// if (questions.get(i).getText().equals(entry.getKey()))
			// {
			// findQuestion = questions.get(i);
			// indexCorrectAnswer = i;
			// break;
			// }
			final Set<Answer> answers = new HashSet<>();
			for (final String textAnswer : entry.getValue())
			{
				Answer answer = new Answer();
				answer.setText(textAnswer);
				if (!answerDAO.exists(textAnswer))
					answerDAO.create(answer);
				else
					answer = answerDAO.getByText(textAnswer);
				if (quizDTO.getCorrectAnswers().get(indexCorrectAnswer).equals(answer.getText()))
					findQuestion.setCorrectAnswer(answer);
				answers.add(answer);
			}
			findQuestion.setAnswers(answers);
			questionDAO.update(findQuestion);
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/addQuizFake", method = RequestMethod.POST)
	public String addQuizFake(final HttpSession session, @RequestBody QuizDTO quiz, final Model model)
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
		for (final String string : quiz.getTypes())
			System.out.println(string);
		System.out.println("RISPOSTE CORRETTE:");
		for (final String string : quiz.getCorrectAnswers())
			System.out.println(string);
		System.out.println("RISPOSTE");
		for (final Map.Entry<String, List<String>> entry : quiz.getQuestions_answers().entrySet())
			System.out.println(entry.getKey() + "/" + entry.getValue());
		return "redirect:/";
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
				submitAnswer.setOpenAnswer(entry.getValue());
			else
			{
				final Set<Answer> answers = findQuestion.getAnswers();
				for (final Answer answer : answers)
					if (answer.getId().equals(Integer.parseInt(entry.getValue())))
					{
						submitAnswer.setAnswer(answer);
						if(answer.getId().equals(findQuestion.getCorrectAnswer().getId())) {
							multipleScore+=findQuestion.getPoints();
							submitAnswer.setPoints(findQuestion.getPoints());
						}
						break;
					}
			}
			submitAnswerDAO.create(submitAnswer);
		}
		submitQuiz.setMultipleScore(multipleScore);
		submitQuiz.setTotalScore(submitQuiz.getTotalScore() + multipleScore);
		submitQuizDAO.update(submitQuiz);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/getAllQuizSubmit", method = RequestMethod.GET)
	public String getAllQuizSubmit(@RequestParam String idProfessor, final HttpSession session, final Model model) {
		_setAccountAttribute(session, model);
		final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		final User user = userDAO.get(SessionUtils.getUserIdFromSessionOrNull(session));
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final SubmitQuizDAO submitQuizDAO = (SubmitQuizDAO) context.getBean("submitQuizDAO");
		final SubmitAnswerDAO submitAnswerDAO = (SubmitAnswerDAO) context.getBean("submitAnswerDAO");
		final QuizDAO quizDAO = (QuizDAO) context.getBean("quizDAO");
		
		Map<Contest, List<Quiz>> contestQuizsMap = new HashMap<>();
		Map<Quiz, List<SubmitQuiz>> quizSubmitQuizsMap = new HashMap<>();
		Map<SubmitQuiz, List<SubmitAnswer>> submitQuizSubmitAnswersMap = new HashMap<>();
		
		if(user != null && user.isProfessor()) {
			List<Contest> contests = contestDAO.getContestsByProfessor(Integer.parseInt(idProfessor));
			for (Contest contest : contests) {
				final Jury jury = contest.getJury();
				if(jury.getProfessor().getId().equals(Integer.parseInt(idProfessor))) {
					final List<Quiz> quizs = quizDAO.getAllQuizByContest(contest.getIdcontest());
					contestQuizsMap.put(contest, quizs);
					for (final Quiz quiz : quizs) {
						final List<SubmitQuiz> submitQuizs = submitQuizDAO.getAllByQuiz(quiz);
						quizSubmitQuizsMap.put(quiz, submitQuizs);
						for (SubmitQuiz submitQuiz : submitQuizs) {
							final List<SubmitAnswer> submitAnswers = submitAnswerDAO.getBySubmitQuiz(submitQuiz);
							submitQuizSubmitAnswersMap.put(submitQuiz, submitAnswers);
						}
					}
					
				}
			}
		}
		
		model.addAttribute("contestQuizsMap", contestQuizsMap);
		model.addAttribute("quizSubmitQuizsMap", quizSubmitQuizsMap);
		model.addAttribute("submitQuizSubmitAnswersMap", submitQuizSubmitAnswersMap);
		return "MyQuizs";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}