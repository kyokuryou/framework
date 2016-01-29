package org.smarty.core.support.jdbc;

import java.util.List;
import org.junit.Test;
import org.smarty.core.bean.Pager;
import org.smarty.core.entity.TestBean;
import org.smarty.core.io.ModelMap;
import org.smarty.core.io.ParameterMap;
import org.smarty.core.test.AbsTestCase;
import org.smarty.core.utils.SpringUtil;

/**
 * @author qul
 * @since LVGG1.1
 */
public class TestSQLSession extends AbsTestCase {

	private SQLSession getSQLSession() {
		setUpSpring("classpath:spring.xml");
		SQLSession sqlSession = SpringUtil.getBean("testSqlSession", SQLSession.class);
		if (sqlSession == null) {
			throw new RuntimeException();
		}
		return sqlSession;
	}

	@Test
	public void testExecuteForSingle1() {
		SQLSession sqlSession = getSQLSession();
		SelectSQL.BEGIN();
		SelectSQL.SELECT("COUNT(1)");
		SelectSQL.FROM("lvgg_test");
		Object re1 = sqlSession.executeForSingle(SelectSQL.SQL());
		System.out.println("re1:" + re1 + "," + re1.getClass());

		InsertSQL.BEGIN();
		InsertSQL.INSERT("lvgg_test");
		InsertSQL.VALUES("test_name", "'测试名称-1'");
		InsertSQL.VALUES("test_val", "'测试值-1'");
		Object re2 = sqlSession.executeForSingle(InsertSQL.SQL());
		System.out.println("re2:" + re2 + "," + re2.getClass());

		CallSQL.BEGIN();
		CallSQL.CALL("test_call");
		CallSQL.VALUE("'测试名称N1'");
		CallSQL.VALUE("'测试值N1'");
		Object re5 = sqlSession.executeForSingle(CallSQL.SQL());
		System.out.println("re5:" + re5 + "," + re5.getClass());
	}

	@Test
	public void testExecuteForSingle2() {
		SQLSession sqlSession = getSQLSession();
		SelectSQL.BEGIN();
		SelectSQL.SELECT("COUNT(1)");
		SelectSQL.FROM("lvgg_test");
		SelectSQL.WHERE("id=:id");
		ParameterMap par1 = new ParameterMap();
		par1.put("id", 1);

		Object re1 = sqlSession.executeForSingle(SelectSQL.SQL(), par1);
		System.out.println("re1:" + re1 + "," + re1.getClass());

		InsertSQL.BEGIN();
		InsertSQL.INSERT("lvgg_test");
		InsertSQL.VALUES("test_name", ":name");
		InsertSQL.VALUES("test_val", ":val");

		ParameterMap par2 = new ParameterMap();
		par2.put("name", "测试名称-2");
		par2.put("val", "测试值-2");
		Object re2 = sqlSession.executeForSingle(InsertSQL.SQL(), par2);
		System.out.println("re2:" + re2 + "," + re2.getClass());

		CallSQL.BEGIN();
		CallSQL.CALL("test_call");
		CallSQL.VALUE(":name");
		CallSQL.VALUE(":val");

		ParameterMap par3 = new ParameterMap();
		par3.put("name", "测试名称N2");
		par3.put("val", "测试值N2");
		Object re5 = sqlSession.executeForSingle(CallSQL.SQL(), par3);
		System.out.println("re5:" + re5 + "," + re5.getClass());
	}

	@Test
	public void testExecuteForSingle3() {
		SQLSession sqlSession = getSQLSession();
		SelectSQL.BEGIN();
		SelectSQL.SELECT("COUNT(1)");
		SelectSQL.FROM("lvgg_test");
		long re1 = sqlSession.executeForSingle(SelectSQL.SQL(), Long.class);
		System.out.println("re1:" + re1);

		InsertSQL.BEGIN();
		InsertSQL.INSERT("lvgg_test");
		InsertSQL.VALUES("test_name", "'测试名称-3'");
		InsertSQL.VALUES("test_val", "'测试值-3'");
		long re2 = sqlSession.executeForSingle(InsertSQL.SQL(), Long.class);
		System.out.println("re2:" + re2);

		CallSQL.BEGIN();
		CallSQL.CALL("test_call");
		CallSQL.VALUE("'测试名称N3'");
		CallSQL.VALUE("'测试值N3'");
		long re5 = sqlSession.executeForSingle(CallSQL.SQL(), Long.class);
		System.out.println("re5:" + re5);
	}

	@Test
	public void testExecuteForSingle4() throws Exception {
		SQLSession sqlSession = getSQLSession();
		SelectSQL.BEGIN();
		SelectSQL.SELECT("COUNT(1)");
		SelectSQL.FROM("lvgg_test");
		SelectSQL.WHERE("id=:id");
		ParameterMap par1 = new ParameterMap();
		par1.put("id", 1);

		long re1 = sqlSession.executeForSingle(SelectSQL.SQL(), par1, Long.class);
		System.out.println("re1:" + re1);

		InsertSQL.BEGIN();
		InsertSQL.INSERT("lvgg_test");
		InsertSQL.VALUES("test_name", ":name");
		InsertSQL.VALUES("test_val", ":val");

		ParameterMap par2 = new ParameterMap();
		par2.put("name", "测试名称-4");
		par2.put("val", "测试值-4");
		long re2 = sqlSession.executeForSingle(InsertSQL.SQL(), par2, Long.class);
		System.out.println("re2:" + re2);

		CallSQL.BEGIN();
		CallSQL.CALL("test_call");
		CallSQL.VALUE(":name");
		CallSQL.VALUE(":val");

		ParameterMap par3 = new ParameterMap();
		par3.put("name", "测试名称N4");
		par3.put("val", "测试值N4");
		long re5 = sqlSession.executeForSingle(CallSQL.SQL(), par3, Long.class);
		System.out.println("re5:" + re5);
	}

	@Test
	public void testExecuteForSingleList1() {
		SQLSession sqlSession = getSQLSession();
		SelectSQL.BEGIN();
		SelectSQL.SELECT("id");
		SelectSQL.FROM("lvgg_test");
		List<?> re1 = sqlSession.executeForSingleList(SelectSQL.SQL());
		System.out.println("re1:" + re1 + "," + re1.getClass());

		CallSQL.BEGIN();
		CallSQL.CALL("test_call_copy");
		CallSQL.VALUE("'测试名称NS1'");
		CallSQL.VALUE("'测试值NS1'");
		List<?> re2 = sqlSession.executeForSingleList(CallSQL.SQL());
		System.out.println("re2:" + re2 + "," + re2.getClass());
	}

	@Test
	public void testExecuteForSingleList2() {
		SQLSession sqlSession = getSQLSession();
		SelectSQL.BEGIN();
		SelectSQL.SELECT("id");
		SelectSQL.FROM("lvgg_test");
		SelectSQL.WHERE("id=:id");
		ParameterMap par1 = new ParameterMap();
		par1.put("id", 1);
		List<?> re1 = sqlSession.executeForSingleList(SelectSQL.SQL(), par1);
		System.out.println("re1:" + re1 + "," + re1.getClass());

		CallSQL.BEGIN();
		CallSQL.CALL("test_call_copy");
		CallSQL.VALUE(":name");
		CallSQL.VALUE(":val");

		ParameterMap par3 = new ParameterMap();
		par3.put("name", "测试名称NS2");
		par3.put("val", "测试值NS2");
		List<?> re5 = sqlSession.executeForSingleList(CallSQL.SQL(), par3);
		System.out.println("re5:" + re5 + "," + re5.getClass());
	}

	@Test
	public void testExecuteForSingleList3() {
		SQLSession sqlSession = getSQLSession();
		SelectSQL.BEGIN();
		SelectSQL.SELECT("id");
		SelectSQL.FROM("lvgg_test");
		List<Integer> re1 = sqlSession.executeForSingleList(SelectSQL.SQL(), Integer.class);
		System.out.println("re1:" + re1);

		CallSQL.BEGIN();
		CallSQL.CALL("test_call_copy");
		CallSQL.VALUE("'测试名称NS3'");
		CallSQL.VALUE("'测试值NS3'");
		List<Integer> re5 = sqlSession.executeForSingleList(CallSQL.SQL(), Integer.class);
		System.out.println("re5:" + re5);
	}

	@Test
	public void testExecuteForSingleList4() {
		SQLSession sqlSession = getSQLSession();
		SelectSQL.BEGIN();
		SelectSQL.SELECT("id");
		SelectSQL.FROM("lvgg_test");
		SelectSQL.WHERE("id=:id");
		ParameterMap par1 = new ParameterMap();
		par1.put("id", 1);

		List<Integer> re1 = sqlSession.executeForSingleList(SelectSQL.SQL(), par1, Integer.class);
		System.out.println("re1:" + re1);

		CallSQL.BEGIN();
		CallSQL.CALL("test_call_copy");
		CallSQL.VALUE(":name");
		CallSQL.VALUE(":val");

		ParameterMap par3 = new ParameterMap();
		par3.put("name", "测试名称NS4");
		par3.put("val", "测试值NS4");
		List<Integer> re5 = sqlSession.executeForSingleList(CallSQL.SQL(), par3, Integer.class);
		System.out.println("re5:" + re5);
	}

	@Test
	public void testExecuteForModel1() {
		SQLSession sqlSession = getSQLSession();
		SelectSQL.BEGIN();
		SelectSQL.SELECT("id,test_name,test_val");
		SelectSQL.FROM("lvgg_test");
		SelectSQL.WHERE("id=1");

		ModelMap re1 = sqlSession.executeForModel(SelectSQL.SQL());
		System.out.println("re1:" + re1);

		InsertSQL.BEGIN();
		InsertSQL.INSERT("lvgg_test");
		InsertSQL.VALUES("test_name", "'测试名称-1-1'");
		InsertSQL.VALUES("test_val", "'测试值-1-1'");
		ModelMap re2 = sqlSession.executeForModel(InsertSQL.SQL());
		System.out.println("re2:" + re2 + "," + re2.getClass());

		CallSQL.BEGIN();
		CallSQL.CALL("test_call");
		CallSQL.VALUE("'测试名称M1'");
		CallSQL.VALUE("'测试值M1'");
		Object re5 = sqlSession.executeForModel(CallSQL.SQL());
		System.out.println("re5:" + re5 + "," + re5.getClass());
	}


	@Test
	public void testExecuteForModel2() {
		SQLSession sqlSession = getSQLSession();
		SelectSQL.BEGIN();
		SelectSQL.SELECT("COUNT(1)");
		SelectSQL.FROM("lvgg_test");
		SelectSQL.WHERE("id=:id");
		ParameterMap par1 = new ParameterMap();
		par1.put("id", 1);

		Object re1 = sqlSession.executeForModel(SelectSQL.SQL(), par1);
		System.out.println("re1:" + re1 + "," + re1.getClass());

		InsertSQL.BEGIN();
		InsertSQL.INSERT("lvgg_test");
		InsertSQL.VALUES("test_name", ":name");
		InsertSQL.VALUES("test_val", ":val");

		ParameterMap par2 = new ParameterMap();
		par2.put("name", "测试名称-2-2");
		par2.put("val", "测试值-2-2");
		Object re2 = sqlSession.executeForModel(InsertSQL.SQL(), par2);
		System.out.println("re2:" + re2 + "," + re2.getClass());

		CallSQL.BEGIN();
		CallSQL.CALL("test_call");
		CallSQL.VALUE(":name");
		CallSQL.VALUE(":val");

		ParameterMap par3 = new ParameterMap();
		par3.put("name", "测试名称M2");
		par3.put("val", "测试值M2");
		Object re5 = sqlSession.executeForModel(CallSQL.SQL(), par3);
		System.out.println("re5:" + re5 + "," + re5.getClass());
	}

	@Test
	public void testExecuteForModel3() {
		SQLSession sqlSession = getSQLSession();
		SelectSQL.BEGIN();
		SelectSQL.SELECT("COUNT(1) AS result");
		SelectSQL.FROM("lvgg_test");
		TestBean re1 = sqlSession.executeForModel(SelectSQL.SQL(), TestBean.class);
		System.out.println("re1:" + re1);

		InsertSQL.BEGIN();
		InsertSQL.INSERT("lvgg_test");
		InsertSQL.VALUES("test_name", "'测试名称-3-3'");
		InsertSQL.VALUES("test_val", "'测试值-3-3'");
		TestBean re2 = sqlSession.executeForModel(InsertSQL.SQL(), TestBean.class);
		System.out.println("re2:" + re2);

		CallSQL.BEGIN();
		CallSQL.CALL("test_call");
		CallSQL.VALUE("'测试名称M3'");
		CallSQL.VALUE("'测试值M3'");
		TestBean re5 = sqlSession.executeForModel(CallSQL.SQL(), TestBean.class);
		System.out.println("re5:" + re5);
	}

	@Test
	public void testExecuteForModel4() throws Exception {
		SQLSession sqlSession = getSQLSession();
		SelectSQL.BEGIN();
		SelectSQL.SELECT("COUNT(1) AS result");
		SelectSQL.FROM("lvgg_test");
		SelectSQL.WHERE("id=:id");
		ParameterMap par1 = new ParameterMap();
		par1.put("id", 1);

		TestBean re1 = sqlSession.executeForModel(SelectSQL.SQL(), par1, TestBean.class);
		System.out.println("re1:" + re1);

		InsertSQL.BEGIN();
		InsertSQL.INSERT("lvgg_test");
		InsertSQL.VALUES("test_name", ":name");
		InsertSQL.VALUES("test_val", ":val");

		ParameterMap par2 = new ParameterMap();
		par2.put("name", "测试名称-4-4");
		par2.put("val", "测试值-4-4");
		TestBean re2 = sqlSession.executeForModel(InsertSQL.SQL(), par2, TestBean.class);
		System.out.println("re2:" + re2);

		CallSQL.BEGIN();
		CallSQL.CALL("test_call");
		CallSQL.VALUE(":name");
		CallSQL.VALUE(":val");

		ParameterMap par3 = new ParameterMap();
		par3.put("name", "测试名称M4");
		par3.put("val", "测试值M4");
		TestBean re5 = sqlSession.executeForModel(CallSQL.SQL(), par3, TestBean.class);
		System.out.println("re5:" + re5);
	}


	@Test
	public void testExecuteForModelList1() {
		SQLSession sqlSession = getSQLSession();
		SelectSQL.BEGIN();
		SelectSQL.SELECT("id");
		SelectSQL.FROM("lvgg_test");
		List<?> re1 = sqlSession.executeForModelList(SelectSQL.SQL());
		System.out.println("re1:" + re1 + "," + re1.getClass());

		CallSQL.BEGIN();
		CallSQL.CALL("test_call_copy");
		CallSQL.VALUE("'测试名称MS1'");
		CallSQL.VALUE("'测试值MS1'");
		List<?> re2 = sqlSession.executeForModelList(CallSQL.SQL());
		System.out.println("re2:" + re2 + "," + re2.getClass());
	}

	//
	@Test
	public void testExecuteForModelList2() {
		SQLSession sqlSession = getSQLSession();
		SelectSQL.BEGIN();
		SelectSQL.SELECT("id");
		SelectSQL.FROM("lvgg_test");
		SelectSQL.WHERE("id=:id");
		ParameterMap par1 = new ParameterMap();
		par1.put("id", 1);
		List<?> re1 = sqlSession.executeForModelList(SelectSQL.SQL(), par1);
		System.out.println("re1:" + re1 + "," + re1.getClass());

		CallSQL.BEGIN();
		CallSQL.CALL("test_call_copy");
		CallSQL.VALUE(":name");
		CallSQL.VALUE(":val");

		ParameterMap par3 = new ParameterMap();
		par3.put("name", "测试名称MS2");
		par3.put("val", "测试值MS2");
		List<?> re5 = sqlSession.executeForModelList(CallSQL.SQL(), par3);
		System.out.println("re5:" + re5 + "," + re5.getClass());
	}

	@Test
	public void testExecuteForModelList3() {
		SQLSession sqlSession = getSQLSession();
		SelectSQL.BEGIN();
		SelectSQL.SELECT("id AS result");
		SelectSQL.FROM("lvgg_test");
		List<TestBean> re1 = sqlSession.executeForModelList(SelectSQL.SQL(), TestBean.class);
		System.out.println("re1:" + re1);

		CallSQL.BEGIN();
		CallSQL.CALL("test_call_copy");
		CallSQL.VALUE("'测试名称MS3'");
		CallSQL.VALUE("'测试值MS3'");
		List<TestBean> re5 = sqlSession.executeForModelList(CallSQL.SQL(), TestBean.class);
		System.out.println("re5:" + re5);
	}

	@Test
	public void testExecuteForModelList4() {
		SQLSession sqlSession = getSQLSession();
		SelectSQL.BEGIN();
		SelectSQL.SELECT("id AS result");
		SelectSQL.FROM("lvgg_test");
		SelectSQL.WHERE("id=:id");
		ParameterMap par1 = new ParameterMap();
		par1.put("id", 1);

		List<TestBean> re1 = sqlSession.executeForModelList(SelectSQL.SQL(), par1, TestBean.class);
		System.out.println("re1:" + re1);

		CallSQL.BEGIN();
		CallSQL.CALL("test_call_copy");
		CallSQL.VALUE(":name");
		CallSQL.VALUE(":val");

		ParameterMap par3 = new ParameterMap();
		par3.put("name", "测试名称MS4");
		par3.put("val", "测试值MS4");
		List<TestBean> re5 = sqlSession.executeForModelList(CallSQL.SQL(), par3, TestBean.class);
		System.out.println("re5:" + re5);
	}

	@Test
	public void testExecuteForPager() {
		SQLSession sqlSession = getSQLSession();
		SelectSQL.BEGIN();
		SelectSQL.SELECT("id,test_name,test_val");
		SelectSQL.FROM("lvgg_test");
		SelectSQL.WHERE("test_name LIKE CONCAT(:name, '%')");


		Pager pager = new Pager();
		pager.addParams("name", "测试名称MS");

		Pager p = sqlSession.executeForPager(SelectSQL.SQL(), pager, TestBean.class);
		System.out.println(p.getList());
		System.out.println(p.getTotalCount());
		System.out.println(p.getPageCount());
		System.out.println(p.getPageNumber());
	}

}
