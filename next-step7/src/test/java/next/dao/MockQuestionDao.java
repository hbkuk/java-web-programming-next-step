package next.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import next.model.Question;

public class MockQuestionDao implements QuestionDao{
	private Map<Long, Question> questions = new HashMap<>();
	
	@Override
	public Question insert(Question question) {
		return questions.put(question.getQuestionId(), question);
	}

	@Override
	public List<Question> findAll() {
		return new ArrayList<>(questions.values());
	}

	@Override
	public Question findById(long questionId) {
		// TODO Auto-generated method stub
		return questions.get(questionId);
	}

	@Override
	public void update(Question newQuestion) {
		findById(newQuestion.getQuestionId()).update(newQuestion);
	}

	@Override
	public void delete(long questionId) {
		questions.remove(questionId);
	}

	@Override
	public void updateCountOfAnswer(long questionId) {
	}

}
