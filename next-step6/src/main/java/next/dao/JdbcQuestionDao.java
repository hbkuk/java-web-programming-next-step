package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import core.jdbc.PreparedStatementCreator;
import core.jdbc.RowMapper;
import next.model.Question;

public class JdbcQuestionDao implements QuestionDao {
	private JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
	private static QuestionDao jdbcQuestionDao = new JdbcQuestionDao();
	
	private JdbcQuestionDao() {}
	public static QuestionDao getInstance() {
		return jdbcQuestionDao;
	}
	
    @Override
	public Question insert(Question question) {
        String sql = "INSERT INTO QUESTIONS " + 
                "(writer, title, contents, createdDate, countOfAnswer) " + 
                " VALUES (?, ?, ?, ?, 0)";
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, question.getWriter());
                pstmt.setString(2, question.getTitle());
                pstmt.setString(3, question.getContents());
                pstmt.setTimestamp(4, new Timestamp(question.getTimeFromCreateDate()));
                return pstmt;
            }
        };

        KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        return findById(keyHolder.getId());
    }
    
    @Override
	public List<Question> findAll() {
        String sql = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS "
                + "order by questionId desc";

        RowMapper<Question> rm = new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs) throws SQLException {
                return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"), null,
                        rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
            }

        };

        return jdbcTemplate.query(sql, rm);
    }

    @Override
	public Question findById(long questionId) {
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
                + "WHERE questionId = ?";

        RowMapper<Question> rm = new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs) throws SQLException {
                return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
                        rs.getString("contents"), rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
            }
        };

        return jdbcTemplate.queryForObject(sql, rm, questionId);
    }
    
    @Override
	public void update(Question question) {
    	String sql = "UPDATE QUESTIONS SET TITLE = ?, CONTENTS = ? WHERE QUESTIONID = ?";
    	jdbcTemplate.update(sql, question.getTitle(), question.getContents(), question.getQuestionId());
    }
    
    @Override
	public void delete(long question) {
    	String sql = "DELETE FROM QUESTIONS WHERE QUESTIONID = ?";
    	jdbcTemplate.update(sql, question);
    }
}
