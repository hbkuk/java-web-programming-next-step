package next.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import next.model.Answer;

public class MockAnswerDao implements AnswerDao {
	private Map<Long, Answer> answers = new HashMap<>();
	
	@Override
	public Answer insert(Answer answer) {
		return answers.put(answer.getAnswerId(), answer);
	}
	
	@Override
	public Answer findById(long answerId) {
		return answers.get(answerId);
	}

	@Override
	public List<Answer> findAllByQuestionId(long questionId) {
		return answers.values().stream()
				.filter(a -> a.getQuestionId() == questionId)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long answerId) {
		answers.remove(answerId);
	}
}

