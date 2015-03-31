package com.dllx.security.common;

import com.dllx.logger.RuntimeLogger;
import com.dllx.security.bean.Resource;
import com.dllx.security.dao.admin.IResourceDao;
import com.dllx.security.dao.admin.IRoleDao;
import com.dllx.security.service.admin.ResourceService;
import com.dllx.security.service.admin.RoleService;
import com.dllx.utils.LogicUtil;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RegexRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 后台权限、资源对应关系
 */
@Component
public class SecurityMetadata implements FilterInvocationSecurityMetadataSource {
    private static RuntimeLogger logger = new RuntimeLogger(SecurityMetadata.class);

    @javax.annotation.Resource
    private ResourceService resourceService;
    @javax.annotation.Resource
    private RoleService roleService;


    //加载所有资源与权限的关系
    protected Map<String, Collection<ConfigAttribute>> buildRequestMap() {
        List<Resource> resources = resourceService.getAllResource();
        if (LogicUtil.isEmptyCollection(resources)) {
            return new LinkedHashMap<String, Collection<ConfigAttribute>>();
        }
        Map<String, Collection<ConfigAttribute>> resourceMap = new LinkedHashMap<String, Collection<ConfigAttribute>>();
        Collection<ConfigAttribute> configAttributes = new LinkedList<ConfigAttribute>();
        for (Resource resource : resources) {
            //以权限名封装为Spring的security Object
            String roleString = SecurityUtil.toRoleString(roleService.getRoleByResource(resource.getPkId()));
            ConfigAttribute configAttribute = new SecurityConfig(roleString);
            configAttributes.add(configAttribute);
            resourceMap.put(resource.getValue(), configAttributes);
        }
        return resourceMap;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        FilterInvocation fi = ((FilterInvocation) o);
        HttpServletRequest request = fi.getHttpRequest();

        Map<String, Collection<ConfigAttribute>> requestMap = buildRequestMap();
        if (LogicUtil.isNotEmptyMap(requestMap)) {
            // 检测请求与当前资源匹配的正确性
            Set<String> keySet = requestMap.keySet();

            for (String key : keySet) {

                RequestMatcher rm = new AntPathRequestMatcher(key);
                if (rm.matches(request))
                    return requestMap.get(key);
            }
        }
        return new LinkedList<ConfigAttribute>();
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Map<String, Collection<ConfigAttribute>> requestMap = buildRequestMap();
        if (LogicUtil.isNotEmptyMap(requestMap)) {
            Collection<ConfigAttribute> list = new LinkedList<ConfigAttribute>();
            Collection<Collection<ConfigAttribute>> values = requestMap.values();
            for (Collection<ConfigAttribute> configAttributes : values) {
                list.addAll(configAttributes);
            }
            return list;
        }
        return new LinkedList<ConfigAttribute>();
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}