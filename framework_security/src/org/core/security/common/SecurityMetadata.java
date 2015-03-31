package org.core.security.common;

import org.core.logger.RuntimeLogger;
import org.core.security.service.SecurityService;
import org.core.security.utils.SecurityUtil;
import org.core.utils.LogicUtil;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 后台权限、资源对应关系
 */
public class SecurityMetadata implements FilterInvocationSecurityMetadataSource {
    private static RuntimeLogger logger = new RuntimeLogger(SecurityMetadata.class);

    private SecurityService securityService;

    //加载所有资源与权限的关系
    protected Map<String, Collection<ConfigAttribute>> buildRequestMap() {
        List<Map<String,Object>> resources = securityService.getAllResource();
        if (LogicUtil.isEmptyCollection(resources)) {
            return new LinkedHashMap<String, Collection<ConfigAttribute>>();
        }
        Map<String, Collection<ConfigAttribute>> resourceMap = new LinkedHashMap<String, Collection<ConfigAttribute>>();
        Collection<ConfigAttribute> configAttributes = new LinkedList<ConfigAttribute>();
        for (Map<String,Object> resource : resources) {
            //以权限名封装为Spring的security Object
            String roleString = SecurityUtil.toRoleString(securityService.getRoleByResource(resource.get("pk_id").toString()));
            ConfigAttribute configAttribute = new SecurityConfig(roleString);
            configAttributes.add(configAttribute);
            resourceMap.put(resource.get("value").toString(), configAttributes);
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

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}