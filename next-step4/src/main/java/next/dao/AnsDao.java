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
import next.model.Ans;

public class AnsDao {
	
	
	public Ans insert( Ans ans ) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, ans.getWriter());
				pstmt.setString(2, ans.getContents());
				pstmt.setTimestamp(3, new Timestamp( ans.getTimeFromCreateDate()));
				pstmt.setLong(4, ans.getQuestionId());
				return pstmt;
			}
		};
		KeyHolder holder = new KeyHolder();
		jdbcTemplate.update(psc, holder);
		return select( holder.getId() );
	}
	
	public Ans select(long answerId ) { 
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT * FROM ANSWERS WHERE answerId=?";
		
		RowMapper<Ans> rm = new RowMapper<Ans>() {
			@Override
			public Ans mapRow(ResultSet rs) throws SQLException {
				return new Ans(
						Long.parseLong( rs.getString("answerId") ), 
						rs.getString("writer"), 
						rs.getString("contents"), 
						rs.getTimestamp("createdDate"), 
						Long.parseLong( rs.getString("questionId") ));
			}
		};
		
		return jdbcTemplate.queryForObject(sql, rm, answerId); 
	};
	public List<Ans> selectAll(long questionId) { 
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT * FROM ANSWERS WHERE questionId=? order by answerId desc";
		
		RowMapper<Ans> rm = new RowMapper<Ans>() {
			@Override
			public Ans mapRow(ResultSet rs) throws SQLException {
				return new Ans(
						Long.parseLong( rs.getString("answerId") ), 
						rs.getString("writer"), 
						rs.getString("contents"), 
						rs.getTimestamp("createdDate"), 
						Long.parseLong( rs.getString("questionId") ));
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