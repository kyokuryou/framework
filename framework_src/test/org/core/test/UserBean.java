package org.core.test;

import org.core.Model;

import java.util.Date;

public class UserBean extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6981656520598964220L;

	/**
	 *用户ID
	 */
	private Long id;
	/**
	 *用户姓名
	 */
	private String userName;
	/**
	 *角色姓名
	 */
	private String roleName;
	/**
	 *用户登陆名
	 */
	private String loadName;
	/**
	 *用户登陆密码
	 */
	private String password;
	/**
	 *所属单位ID
	 */
	private Long cityOrgId;
	/**
	 *所属单位称
	 */
	private String cityOrgName;
	/**
	 *实际所属单位称
	 */
	private String actrualOrgName;
	/**
	 *联系电话
	 */
	private String tel;
	/**
	 *用户状态
	 */
	private Integer userState;
	/**
	 *创建时间
	 */
	private Date createTime;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the loadName
	 */
	public String getLoadName() {
		return loadName;
	}
	/**
	 * @param loadName the loadName to set
	 */
	public void setLoadName(String loadName) {
		this.loadName = loadName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the cityOrgId
	 */
	public Long getCityOrgId() {
		return cityOrgId;
	}
	/**
	 * @param cityOrgId the cityOrgId to set
	 */
	public void setCityOrgId(Long cityOrgId) {
		this.cityOrgId = cityOrgId;
	}
	/**
	 * @return the cityOrgName
	 */
	public String getCityOrgName() {
		return cityOrgName;
	}
	/**
	 * @param cityOrgName the cityOrgName to set
	 */
	public void setCityOrgName(String cityOrgName) {
		this.cityOrgName = cityOrgName;
	}
	/**
	 * @return the actrualOrgName
	 */
	public String getActrualOrgName() {
		return actrualOrgName;
	}
	/**
	 * @param actrualOrgName the actrualOrgName to set
	 */
	public void setActrualOrgName(String actrualOrgName) {
		this.actrualOrgName = actrualOrgName;
	}
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * @return the userState
	 */
	public Integer getUserState() {
		return userState;
	}
	/**
	 * @param userState the userState to set
	 */
	public void setUserState(Integer userState) {
		this.userState = userState;
	}
	
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
