package next.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import next.model.Qna;

public class QnaDaoTest {
    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }
    
    @Test
    public void selectAll() {
    	QnaDao qnaDao = new QnaDao();
    	
    	assertEquals("자바지기", qnaDao.select("1").getWriter());
    	assertEquals(8, qnaDao.selectAll().size());
    }
    
    @Test
    public void insert() {
    	Qna expected = new Qna(
    			"자바지기",
    			"테스트 제목11", 
    			"테스트 내용11");
    	QnaDao qnaDao = new QnaDao();
    	qnaDao.insert(expected);
    	Qna actual = qnaDao.select("9");
    	
    	assertEquals(expected.getTitle(), actual.getTitle());
    }
    
    @Test
    public void update() {
    	Qna previousUpdateQna = new Qna(
    			"자바지기",
    			"테스트 제목 1", 
    			"테스트 내용 1");
    	QnaDao qnaDao = new QnaDao();
    	qnaDao.insert(previousUpdateQna);
    	
    	Qna updateQna = qnaDao.select("9");
    	updateQna.update(9, "자비지기", "테스트 제목 2", "테스트 내용 2");
    	qnaDao.update(updateQna);
    	
    	assertNotEquals(qnaDao.select("9").getTitle(), updateQna.getTitle());
    }
    
    @Test(expected=NullPointerException.class)
    public void delete() {
    	QnaDao qnaDao = new QnaDao();
    	Qna previouseDeleteQna = qnaDao.select("1");
    	
    	qnaDao.delete(previouseDeleteQna);
    	Qna deleteQna = qnaDao.select("1");
    	deleteQna.getWriter();
    }
}
