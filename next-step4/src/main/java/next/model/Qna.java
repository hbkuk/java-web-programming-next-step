package next.model;

import java.util.Date;

public class Qna {
	private long questionId;
	private String writer;
	private String title;
	private String contents;
	private Date createDate;
	private int countOfAnswer;
	
	public Qna(String writer, String title, String contents) {
		this(0, writer, title, contents, new Date(), 0);
	}
	
	public Qna(long questionId, String writer, String title, String contents, Date createDate,
			int countOfAnswer) {
		this.questionId = questionId;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createDate = createDate;
		this.countOfAnswer = countOfAnswer;
	}
	
	public void update(long questionId, String writer, String title, String contents ) {
		this.questionId = questionId;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}
	
	public long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(long questionId) {
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getCountOfAnswer() {
		return countOfAnswer;
	}
	public void setCountOfAnswer(int countOfAnswer) {
		this.countOfAnswer = countOfAnswer;
	}

	@Override
	public String toString() {
		return "Qna [questionId=" + questionId + ", writer=" + writer + ", title=" + title + ", contents=" + contents
				+ ", createDate=" + createDate + ", countOfAnswer=" + countOfAnswer + "]";
	}
	
}
