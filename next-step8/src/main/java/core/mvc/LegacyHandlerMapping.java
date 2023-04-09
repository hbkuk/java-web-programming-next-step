package core.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.nmvc.DispatcherServlet;
import core.nmvc.HandlerMapping;
import next.controller.HomeController;
import next.controller.qna.AddAnswerController;
import next.controller.qna.ApiDeleteQuestionController;
import next.controller.qna.ApiListQuestionController;
import next.controller.qna.CreateFormQuestionController;
import next.controller.qna.CreateQuestionController;
import next.controller.qna.DeleteAnswerController;
import next.controller.qna.DeleteQuestionController;
import next.controller.qna.ShowQuestionController;
import next.controller.qna.UpdateFormQuestionController;
import next.controller.qna.UpdateQuestionController;
import next.dao.AnswerRepository;
import next.dao.JdbcAnswerRepository;
import next.dao.JdbcQuestionRepository;
import next.dao.QuestionRepository;
import next.service.QnaService;

public class LegacyHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    public void initMapping() {
        QuestionRepository questionRepository = new JdbcQuestionRepository();
        AnswerRepository answerRepository = new JdbcAnswerRepository();
        QnaService qnaService = new QnaService(questionRepository, answerRepository);
        mappings.put("/", (Controller) new HomeController(questionRepository));
        mappings.put("/qna/show", new ShowQuestionController(questionRepository, answerRepository));
        mappings.put("/qna/form", new CreateFormQuestionController());
        mappings.put("/qna/create", new CreateQuestionController(questionRepository));
        mappings.put("/qna/updateForm", new UpdateFormQuestionController(questionRepository));
        mappings.put("/qna/update", new UpdateQuestionController(questionRepository));
        mappings.put("/qna/delete", new DeleteQuestionController(qnaService));
        mappings.put("/api/qna/deleteQuestion", new ApiDeleteQuestionController(qnaService));
        mappings.put("/api/qna/list", new ApiListQuestionController(questionRepository));
        mappings.put("/api/qna/addAnswer", new AddAnswerController(questionRepository, answerRepository));
        mappings.put("/api/qna/deleteAnswer", new DeleteAnswerController(answerRepository));

        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String url) {
        return mappings.get(url);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }

    @Override
    public Controller getHandler(HttpServletRequest request) {
        return mappings.get(request.getRequestURI());
    }
}
