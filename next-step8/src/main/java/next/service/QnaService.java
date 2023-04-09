package next.service;

import java.util.List;

import core.annotation.Inject;
import core.annotation.Service;
import next.CannotDeleteException;
import next.dao.AnswerRepository;
import next.dao.QuestionRepository;
import next.model.Answer;
import next.model.Question;
import next.model.User;

@Service
public class QnaService {
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
    
    @Inject
    public QnaService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public Question findById(long questionId) {
        return questionRepository.findById(questionId);
    }

    public List<Answer> findAllByQuestionId(long questionId) {
        return answerRepository.findAllByQuestionId(questionId);
    }

    public void deleteQuestion(long questionId, User user) throws CannotDeleteException {
        Question question = questionRepository.findById(questionId);
        if (question == null) {
            throw new CannotDeleteException("존재하지 않는 질문입니다.");
        }

        List<Answer> answers = answerRepository.findAllByQuestionId(questionId);
        if (question.canDelete(user, answers)) {
            questionRepository.delete(questionId);
        }
    }
}
