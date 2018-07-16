package it.unical.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import it.unical.dao.AnswerDAO;
import it.unical.dao.ContestDAO;
import it.unical.dao.QuestionAnswerDAO;
import it.unical.dao.QuestionDAO;
import it.unical.dao.QuizDAO;
import it.unical.dao.QuizQuestionDAO;
import it.unical.dao.UserDAO;
import it.unical.entities.Answer;
import it.unical.entities.Contest;
import it.unical.entities.Question;
import it.unical.entities.QuestionAnswer;
import it.unical.entities.Quiz;
import it.unical.entities.QuizQuestion;
import it.unical.entities.User;
import it.unical.forms.AddQuizForm;
import it.unical.utils.SessionUtils;

@Controller
public class QuizController
{

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
	public String addQuiz(final HttpSession session, @ModelAttribute final AddQuizForm addQuizForm, final Model model)
	{
		_setAccountAttribute(session, model);
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final QuizDAO quizDAO = (QuizDAO) context.getBean("quizDAO");
		final QuestionDAO questionDAO = (QuestionDAO) context.getBean("questionDAO");
		final AnswerDAO answerDAO = (AnswerDAO) context.getBean("answerDAO");
		final QuizQuestionDAO quizQuestionDAO = (QuizQuestionDAO) context.getBean("quizQuestionDAO");
		final QuestionAnswerDAO questionAnswerDAO = (QuestionAnswerDAO) context.getBean("questionAnswerDAO");

		final Contest contest = contestDAO.get(addQuizForm.getContestId());
		final Quiz quiz = new Quiz();
		quiz.setContest(contest);
		quiz.setName(addQuizForm.getQuizName());
		quiz.setPoints(addQuizForm.getQuizPoints());
		quizDAO.create(quiz);
		final List<Question> questions = new ArrayList<>();
		for (int i = 0; i < addQuizForm.getQuestions().size(); i++)
		{
			final Question question = new Question();
			question.setPoints(addQuizForm.getPoints().get(i));
			question.setText(addQuizForm.getQuestions().get(i));
			questionDAO.create(question);
			questions.add(question);
			final QuizQuestion quizQuestion = new QuizQuestion();
			quizQuestion.setQuiz(quiz);
			quizQuestion.setQuestion(question);
			quizQuestionDAO.create(quizQuestion);
		}

		int indexCorrectAnswer = -1;
		for (final Entry<String, List<String>> entry : addQuizForm.getQuestions_answers().entrySet())
		{
			Question findQuestion = null;
			for (int i = 0; i < questions.size(); i++)
				if (questions.get(i).getText().equals(entry.getKey()))
				{
					findQuestion = questions.get(i);
					indexCorrectAnswer = i;
					break;
				}
			for (final String textAnswer : entry.getValue())
			{
				final Answer answer = new Answer();
				answer.setText(textAnswer);
				if (!answerDAO.exists(answer))
					answerDAO.create(answer);
				if (addQuizForm.getCorrectAnswers().get(indexCorrectAnswer).equals(answer.getText()))
				{
					findQuestion.setCorrectAnswer(answer);
					questionDAO.update(findQuestion);
				}
				final QuestionAnswer questionAnswer = new QuestionAnswer();
				questionAnswer.setAnswer(answer);
				questionAnswer.setQuestion(findQuestion);
				questionAnswerDAO.create(questionAnswer);
			}
		}

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
}