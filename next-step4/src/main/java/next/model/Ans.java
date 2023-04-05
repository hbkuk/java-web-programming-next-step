package next.model;

import java.util.Date;

public class Ans {
	private long answerId;
	private String writer;
	private String contents;
	private Date createdDate;
	private long questionId;
	
	
	
	public Ans(long answerId, String writer, String contents, Date createdDate, long questionId) {
		this.answerId = answerId;
		this.writer = writer;
		this.contents = contents;
		this.createdDate = createdDate;
		this.questionId = questionId;
	}
	
	public Ans(String writer, String contents, long questionId) {
		this( 0, writer, contents, new Date(), questionId);
	}
	
	public void update(String contents) {
		this.contents = contents;
	}
	
	public long getAnswerId() {
		return answerId;
	}
	public void setAnswerId(long answerId) {
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
	public Date getcreatedDate() {
		return createdDate;
	}
	public void setcreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	
    public long getTimeFromCreateDate() {
        return this.createdDate.getTime();
    }
	
	@Override
	public String toString() {
		return "Ans [answerId=" + answerId + ", writer=" + writer + ", contents=" + contents + ", createdDate="
				+ createdDate + ", questionId=" + questionId + "]";
	}
}
