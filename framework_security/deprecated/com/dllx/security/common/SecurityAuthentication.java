package com.dllx.security.common;

import com.dllx.security.bean.Admin;
import com.dllx.security.bean.Role;
import com.dllx.security.dao.admin.impl.AdminDao;
import com.dllx.security.dao.admin.impl.RoleDao;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * 后台权限认证
 */
@Component
public class SecurityAuthentication implements UserDetailsService {
    @Resource
    private AdminDao adminDao;
    @Resource
    private RoleDao roleDao;

    public Admin loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        Admin admin = adminDao.findByUserName(username);
        if (admin == null) {
            throw new UsernameNotFoundException("管理员[" + username + "]不存在!");
        }
        admin.setAuthorities(getGrantedAuthorities(admin.getPkId()));
        return admin;
    }

    /**
     * 获得管理角色List
     * @param adminId 管理员ID
     * @return 角色List
     */
    public List<GrantedAuthority> getGrantedAuthorities(String adminId) {
        List<GrantedAuthority> grantedAuthorities = new LinkedList<GrantedAuthority>();
        List<Role> roles = roleDao.findRoleListByAdmin(adminId);
        for (Role role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getValue()));
        }
        return grantedAuthorities;
    }

}