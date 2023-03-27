package next.model;

public class Qna {
	private String questionId;
	private String writer;
	private String title;
	private String contents;
	private String createDate;
	private String countOfAnswer;
	
	public Qna(String writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}
	
	public Qna(String questionId, String writer, String title, String contents, String createDate,
			String countOfAnswer) {
		super();
		this.questionId = questionId;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createDate = createDate;
		this.countOfAnswer = countOfAnswer;
	}
	
	public void update(String questionId, String writer, String title, String contents ) {
		this.questionId = questionId;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}
	
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCountOfAnswer() {
		return countOfAnswer;
	}
	public void setCountOfAnswer(String countOfAnswer) {
		this.countOfAnswer = countOfAnswer;
	}

	@Override
	public String toString() {
		return "Qna [questionId=" + questionId + ", writer=" + writer + ", title=" + title + ", contents=" + contents
				+ ", createDate=" + createDate + ", countOfAnswer=" + countOfAnswer + "]";
	}
	
}
