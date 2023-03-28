package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Qna;

public class QnaDao {
	
	public List<Qna> selectAll( ) { 
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT * FROM QUESTIONS";
		
		RowMapper<Qna> rm = new RowMapper<Qna>() {
			@Override
			public Qna mapRow(ResultSet rs) throws SQLException {
				return new Qna(Long.parseLong(rs.getString("questionId")), rs.getString("writer"), rs.getString("title"),
						rs.getString("contents"), rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer") );
			}
		};
		return jdbcTemplate.query(sql, rm);
	};
	public Qna select( String questionId ) { 
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT * FROM QUESTIONS WHERE QUESTIONID=?";
		
		RowMapper<Qna> rm = new RowMapper<Qna>() {
			@Override
			public Qna mapRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return new Qna(Long.parseLong(rs.getString("questionId")), rs.getString("writer"), rs.getString("title"),
						rs.getString("contents"), rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer") );
			}
		};
		return jdbcTemplate.queryForObject(sql, rm, questionId);
	};
	public void insert( Qna qna ) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, now(), 0)";
		jdbcTemplate.update(
				sql, 
				qna.getWriter(),
				qna.getTitle(),
				qna.getContents()
		);
	};
	public void update( Qna qna ) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "UPDATE QUESTIONS SET TITLE = ? , CONTENTS = ? WHERE QUESTIONID = ? AND WRITER = ?";
		jdbcTemplate.update(
				sql,
				qna.getTitle(),
				qna.getContents(),
				qna.getQuestionId(),
				qna.getWriter()
				);
	};
	public void delete( Qna qna ) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "delete FROM QUESTIONS WHERE QUESTIONID = ? AND WRITER = ?";
		jdbcTemplate.update(
				sql,
				qna.getQuestionId(),
				qna.getWriter()
				);
	};
}
