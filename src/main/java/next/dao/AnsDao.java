package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Ans;

public class AnsDao {
	
	
	public void insert( Ans ans ) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, now(), ?)";
		jdbcTemplate.update(
				sql, 
				ans.getWriter(),
				ans.getContents(),
				ans.getQuestionId()
		);
	}
	
	public Ans select(String answerId ) { 
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT * FROM ANSWERS WHERE answerId=?";
		
		RowMapper<Ans> rm = new RowMapper<Ans>() {
			@Override
			public Ans mapRow(ResultSet rs) throws SQLException {
				return new Ans(
						rs.getString("answerId"), 
						rs.getString("writer"), 
						rs.getString("contents"), 
						rs.getString("createdDate"), 
						rs.getString("questionId") );
			}
		};
		
		return jdbcTemplate.queryForObject(sql, rm, answerId); 
	};
	public List<Ans> selectAll(String questionId) { 
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT * FROM ANSWERS WHERE questionId=? order by answerId desc";
		
		RowMapper<Ans> rm = new RowMapper<Ans>() {
			@Override
			public Ans mapRow(ResultSet rs) throws SQLException {
				return new Ans(
						rs.getString("answerId"), 
						rs.getString("writer"), 
						rs.getString("contents"), 
						rs.getString("createdDate"), 
						rs.getString("questionId") ); 
				};
		};
		return jdbcTemplate.query(sql, rm, questionId); 
	};
	public void update( Ans ans ) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "UPDATE ANSWERS SET CONTENTS = ? WHERE ANSWERID = ?";
		jdbcTemplate.update(
				sql, 
				ans.getContents(),
				ans.getAnswerId()
				);
	};
	public void delete( Ans ans ) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "DELETE FROM ANSWERS WHERE ANSWERID = ?";
		jdbcTemplate.update(
				sql, 
				ans.getAnswerId()
				);
	};
}