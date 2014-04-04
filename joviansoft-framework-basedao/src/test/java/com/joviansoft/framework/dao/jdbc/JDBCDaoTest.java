package com.joviansoft.framework.dao.jdbc;

import com.joviansoft.framework.dao.IJdbcDao;
import com.joviansoft.framework.query.IResultSetListMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:hzzy-frame.xml")
//@Transactional
public class JDBCDaoTest {

    @Autowired
    private IJdbcDao jdbcDao;

    @Before
	public void loadConfig(){

	}
	@Test
	public void testJdbc() {
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:hzzy-framework.xml");
//		//fail("Not yet implemented");
//		IJdbcDao dao = (IJdbcDao)ctx.getBean("jdbcDao");
		IResultSetListMap list = jdbcDao.getAllList("select * from xt_yh");
        int length = jdbcDao.getTotalRecordCount("select count(0) from xt_yh");
        assertEquals(245, length,0);
		System.out.println("XXXXXXXXXXXXXXXXXX****************" + list.size());
		assertEquals(list.size()>0, true);
	//	assertEquals(1, 0);
	}

}
