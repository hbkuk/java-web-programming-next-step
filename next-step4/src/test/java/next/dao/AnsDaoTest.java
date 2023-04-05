package next.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import next.model.Ans;

public class AnsDaoTest {
    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }
    
    @Test
    public void select() {
    	Ans expected = new Ans(
    			"테스터1", 
    			"테스트 내용1", 
    			1);
    	
    	AnsDao ansDao = new AnsDao();
    	ansDao.insert(expected);
    	Ans actual = ansDao.select(6);
    	
    	assertEquals(6, actual.getAnswerId());
    }
    @Test
    public void selectAll() {
    	Ans ans1 = new Ans(
    			"테스터1", 
    			"테스트 내용1", 
    			1);
    	Ans ans2 = new Ans(
    			"테스터2", 
    			"테스트 내용2", 
    			1);
    	AnsDao ansDao = new AnsDao();
    	ansDao.insert(ans1);
    	ansDao.insert(ans2);
    	
    	assertEquals(2, ansDao.selectAll(ans1.getQuestionId()).size());
    }
    
    @Test
    public void update() {
    	Ans ans1 = new Ans(
    			"테스터1", 
    			"테스트 내용1", 
    			1);
    	AnsDao ansDao = new AnsDao();
    	ansDao.insert(ans1);
    	
    	Ans updateAns = ansDao.select(6);
    	
    	updateAns.update("수정");
    	ansDao.update(updateAns);
    	
    	assertEquals("수정", ansDao.select(6).getContents());
    }
    @Test(expected=NullPointerException.class)
    public void delete() {
    	Ans ans1 = new Ans(
    			"테스터1", 
    			"테스트 내용 6", 
    			1);
    	AnsDao ansDao = new AnsDao();
    	ansDao.insert(ans1);
    	
    	Ans deleteAns = ansDao.select(6);
    	ansDao.delete(deleteAns);
    	
    	ansDao.select(6).getContents();
    }
    
}
