package org.core.security.bean;

import org.core.Model;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class UserSecurity extends Model implements UserDetails {
    private String username;
    private String password;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSecurity(Map<String, Object> data, List<GrantedAuthority> authorities) {
        this.username = (String) data.get("username");
        this.password = (String) data.get("password");
        this.isAccountNonExpired = (Boolean) data.get("is_expired");
        this.isAccountNonLocked = (Boolean) data.get("is_locked");
        this.isCredentialsNonExpired = (Boolean) data.get("is_credentials_expired");
        this.isCredentialsNonExpired = (Boolean) data.get("is_credentials_expired");
        this.isEnabled = (Boolean) data.get("is_enabled");
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }


    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        UserSecurity user = (UserSecurity)obj;
        return this.username.equals(user.getUsername());
    }
}
