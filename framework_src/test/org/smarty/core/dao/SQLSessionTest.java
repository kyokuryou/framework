package org.smarty.core.dao;

import org.smarty.core.support.cache.CacheMessage;
import org.smarty.core.support.jdbc.SQLSession;
import org.smarty.core.test.AbsTestCase;
import org.smarty.core.utils.SpringUtil;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * sqlSession测试
 */
public class SQLSessionTest extends AbsTestCase {


    public void testQuery() throws SQLException {
        setUpSpring("spring.xml");
        SQLSession sqlSession = SpringUtil.getBean("jkzxSqlSession", SQLSession.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mappingStatus", "1");
        System.out.println(sqlSession.queryForObject("SELECT COUNT(1) FROM DATAINT.KEY_METHOD_MAPPING MAPPING WHERE MAPPING.MAPPING_STATUS = :mappingStatus", map));
    }

    @Test
    public void testDao(){
        setUpSpring("spring.xml");
        initCache();

        AdminRoleDao ard = SpringUtil.getBean("adminRoleDao", AdminRoleDao.class);
        ard.getByAdmin(null);

    }

    public final void initCache() {
        Map<String, Integer> caches = new HashMap<String, Integer>();
        caches.put("system", 512);
        caches.put("temporary", 512);
        CacheMessage cm = new CacheMessage("q1w2e3r4t5");
        cm.initCacheMap(caches);
    }
}
