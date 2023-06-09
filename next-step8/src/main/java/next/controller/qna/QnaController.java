package next.controller.qna;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.jdbc.DataAccessException;
import core.mvc.ModelAndView;
import core.nmvc.AbstractNewController;
import next.CannotDeleteException;
import next.controller.UserSessionUtils;
import next.dao.JdbcAnswerRepository;
import next.dao.JdbcQuestionRepository;
import next.model.Answer;
import next.model.Question;
import next.model.Result;
import next.model.User;
import next.service.QnaService;

@Controller
public class QnaController extends AbstractNewController {
	private static final Logger log = LoggerFactory.getLogger(QnaController.class);
	private QnaService qnaService;
	private JdbcQuestionRepository jdbcQuestionRepository;
	private JdbcAnswerRepository jdbcAnswerRepository;
	
	@Inject
	public QnaController ( QnaService qnaService, 
			JdbcQuestionRepository jdbcQuestionRepository, JdbcAnswerRepository jdbcjdbcAnswerRepository) {
		this.qnaService = qnaService;
		this.jdbcQuestionRepository = jdbcQuestionRepository;
		this.jdbcAnswerRepository = jdbcjdbcAnswerRepository;
	}
	
    @RequestMapping(value="/api/qna/addAnswer", method=RequestMethod.POST)
    public ModelAndView addAnswerApi(HttpServletRequest req, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jsonView().addObject("result", Result.fail("Login is required"));
        }

        User user = UserSessionUtils.getUserFromSession(req.getSession());
        Answer answer = new Answer(user.getUserId(), req.getParameter("contents"),
                Long.parseLong(req.getParameter("questionId")));
        log.debug("answer : {}", answer);

        Answer savedAnswer = jdbcAnswerRepository.insert(answer);
        jdbcQuestionRepository.updateCountOfAnswer(savedAnswer.getQuestionId());

        return jsonView().addObject("answer", savedAnswer).addObject("result", Result.ok());
    }
    
    @RequestMapping("/api/qna/deleteQuestion")
    public ModelAndView deleteQuestionApi(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jsonView().addObject("result", Result.fail("Login is required"));
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        try {
            qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(req.getSession()));
            return jsonView().addObject("result", Result.ok());
        } catch (CannotDeleteException e) {
            return jsonView().addObject("result", Result.fail(e.getMessage()));
        }
    }
    
    @RequestMapping("/api/qna/list")
    public ModelAndView listQuestionsApi(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return jsonView().addObject("questions", jdbcQuestionRepository.findAll());
    }
    
    @RequestMapping("/qna/form")
    public ModelAndView formQuestion(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }
        return jspView("/qna/form.jsp");
    }
    
    @RequestMapping(value="/qna/create", method=RequestMethod.POST)
    public ModelAndView createQuestion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jspView("redirect:/users/loginForm");
        }
        User user = UserSessionUtils.getUserFromSession(request.getSession());
        Question question = new Question(user.getUserId(), request.getParameter("title"),
                request.getParameter("contents"));
        jdbcQuestionRepository.insert(question);
        return jspView("redirect:/");
    }
    
    @RequestMapping("/api/qna/deleteAnswer")
    public ModelAndView deleteAnswerApi(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long answerId = Long.parseLong(request.getParameter("answerId"));

        ModelAndView mav = jsonView();
        try {
            jdbcAnswerRepository.delete(answerId);
            mav.addObject("result", Result.ok());
        } catch (DataAccessException e) {
            mav.addObject("result", Result.fail(e.getMessage()));
        }
        return mav;
    }
    
    @RequestMapping(value="/qna/delete", method=RequestMethod.POST)
    public ModelAndView deleteQuestion(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        try {
            qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(req.getSession()));
            return jspView("redirect:/");
        } catch (CannotDeleteException e) {
            return jspView("show.jsp").addObject("question", qnaService.findById(questionId))
                    .addObject("answers", qnaService.findAllByQuestionId(questionId))
                    .addObject("errorMessage", e.getMessage());
        }
    }
    
    @RequestMapping("/qna/show")
    public ModelAndView showQuestion(HttpServletRequest req, HttpServletResponse response) throws Exception {
        long questionId = Long.parseLong(req.getParameter("questionId"));

        Question question = jdbcQuestionRepository.findById(questionId);
        List<Answer> answers = jdbcAnswerRepository.findAllByQuestionId(questionId);

        ModelAndView mav = jspView("/qna/show.jsp");
        mav.addObject("question", question);
        mav.addObject("answers", answers);
        return mav;
    }
    
    @RequestMapping("/qna/updateForm")
    public ModelAndView updateQuestionForm(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        Question question = jdbcQuestionRepository.findById(questionId);
        if (!question.isSameWriter(UserSessionUtils.getUserFromSession(req.getSession()))) {
            throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
        }
        return jspView("/qna/update.jsp").addObject("question", question);
    }
    
    @RequestMapping("/qna/update")
    public ModelAndView updateQuestion(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        Question question = jdbcQuestionRepository.findById(questionId);
        if (!question.isSameWriter(UserSessionUtils.getUserFromSession(req.getSession()))) {
            throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
        }

        Question newQuestion = new Question(question.getWriter(), req.getParameter("title"),
                req.getParameter("contents"));
        question.update(newQuestion);
        jdbcQuestionRepository.update(question);
        return jspView("redirect:/");
    }
}
