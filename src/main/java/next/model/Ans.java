package next.model;

public class Ans {
	private String answerId;
	private String writer;
	private String contents;
	private String createdDate;
	private String questionId;
	
	
	
	public Ans(String answerId, String writer, String contents, String createdDate, String questionId) {
		super();
		this.answerId = answerId;
		this.writer = writer;
		this.contents = contents;
		this.createdDate = createdDate;
		this.questionId = questionId;
	}
	
	public Ans(String writer, String contents, String questionId) {
		super();
		this.writer = writer;
		this.contents = contents;
		this.questionId = questionId;
	}
	
	public void update(String contents) {
		this.contents = contents;
	}
	
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getcreatedDate() {
		return createdDate;
	}
	public void setcreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	
	@Override
	public String toString() {
		return "Ans [answerId=" + answerId + ", writer=" + writer + ", contents=" + contents + ", createdDate="
				+ createdDate + ", questionId=" + questionId + "]";
	}
}
