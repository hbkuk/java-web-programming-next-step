package next.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import next.model.Question;

public class MockQuestionDao implements QuestionDao {
	private Map<Long, Question> questions = new HashMap<>();
	@Override
	public Question insert(Question question) {
		// TODO Auto-generated method stub
		return questions.put(question.getQuestionId(), question );
	}

	@Override
	public List<Question> findAll() {
		return questions.values().stream()
				.collect(Collectors.toList());
	}

	@Override
	public Question findById(long questionId) {
		// TODO Auto-generated method stub
		return questions.get(questions);
	}

	@Override
	public void update(Question question) {
		findById(question.getQuestionId()).update(question);
	}

	@Override
	public void delete(long question) {
		questions.remove(question);

	}

}
