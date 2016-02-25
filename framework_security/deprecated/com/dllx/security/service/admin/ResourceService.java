package com.dllx.security.service.admin;

import com.dllx.bean.Pager;
import com.dllx.logger.RuntimeLogger;
import com.dllx.security.bean.Resource;
import com.dllx.security.dao.admin.IResourceDao;
import com.dllx.security.dao.admin.impl.ResourceDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * Service实现类 - 资源
 */

@Service
@Transactional
public class ResourceService {
    private static Log logger = LogFactory.getLog(ResourceService.class);

    @javax.annotation.Resource
    private IResourceDao resourceDao;

    public List<Resource> getAllResource(){
        try {
            return resourceDao.findResourceListAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据ID获得资源
     * @param id 资源ID
     * @return 资源
     */
    public Resource getResource(String id){
        return null;
    }

    /**
     * 检查名字是否存在
     * @param newName 新名字
     * @return true:存在,false:不存在
     */
    public boolean isExistName(String newName) {
        return false;
    }

    /**
     * 检查值是否存在
     * @param newValue 新值
     * @return true:存在,false:不存在
     */
    public boolean isExistValue(String newValue) {
        return false;
    }

    /**
     * 分页查询
     * @param pager 分页信息
     * @return 分页结果
     */
    public Pager findByPager(Pager pager){
        if(pager == null){
            pager = new Pager();
        }
        return pager;
    }

    /**
     * 更新资源
     * @param resource 资源
     */
    public void updateResource(Resource resource){

    }

    /**
     * 删除资源单个/批量
     * @param ids id
     */
    public void deleteResource(String... ids){

    }

    /**
     * 插入资源
     * @param resource 资源
     */
    public void saveResource(Resource resource){

    }



}