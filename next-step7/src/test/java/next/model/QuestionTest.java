package next.model;

import java.util.Date;

public class QuestionTest {
	public static Question newQuestion( String writer ) {
		return new Question(1L, writer, "title", "contents", new Date(), 0);
	}
}
