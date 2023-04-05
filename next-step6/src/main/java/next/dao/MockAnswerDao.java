package next.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import next.model.Answer;

public class MockAnswerDao implements AnswerDao {
	private Map<Long, Answer> answers = new HashMap<>();

	@Override
	public Answer insert(Answer answer) {
		// TODO Auto-generated method stub
		return answers.put( answer.getAnswerId(), answer);
	}

	@Override
	public Answer findById(long answerId) {
		// TODO Auto-generated method stub
		return answers.get(answerId);
	}

	@Override
	public List<Answer> findAllByQuestionId(long questionId) {
		// TODO Auto-generated method stub
		return answers.values().stream()
				.filter(a -> a.getAnswerId() == questionId)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long answerId) {
		answers.remove(answerId);
	}
	@Override
	public void deleteAll(Long questionId) {
	}
	@Override
	public void increaseComment(Long questionId) {
	}

	@Override
	public void decreaseComment(Long questionId) {
	}
}
