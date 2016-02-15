package org.smarty.security.bean;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.smarty.core.utils.LogicUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSecurity implements UserDetails {
	private String username;
	private String password;
	private Date lockedDate;
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;
	private boolean isEnabled;
	private Collection<? extends GrantedAuthority> authorities;

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

	public Date getLockedDate() {
		return lockedDate;
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

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		isAccountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		isAccountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		isCredentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		isEnabled = enabled;
	}

	public void setAuthorities(List<String> authorities) {
		if (LogicUtil.isEmptyCollection(authorities)) {
			return;
		}
		List<GrantedAuthority> galist = new LinkedList<GrantedAuthority>();
		for (String auth : authorities) {
			galist.add(new SimpleGrantedAuthority(auth));
		}
		this.authorities = galist;
	}

	@Override
	public int hashCode() {
		return username.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		UserSecurity user = (UserSecurity) obj;
		return this.username.equals(user.getUsername());
	}
}
