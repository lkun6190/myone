/**
 * Copyright &copy; 2012-2014 <a href="http://www.huayingsoft.com">JGGFrame</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.supcan.annotation.treelist.cols.SupCol;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.utils.AdminUtils;

/**
 * 用户Entity
 * 
 * @author ThinkGem
 * @version 2013-12-05
 */
public class User extends DataEntity<User> {

	private static final long serialVersionUID = 1L;

	private Office company; // 归属公司
	private Office office; // 归属部门
	private String loginName;// 登录名
	private String password;// 密码
	private String no; // 身份证号
	private String name; // 姓名
	private String email; // 邮箱
	private String identfy;
	private String phone; // 电话
	private String mobile; // 手机
	private String userType;// 用户类型
	private String loginIp; // 最后登陆IP
	private Date loginDate; // 最后登陆日期
	private String loginFlag; // 是否允许登陆
	private String photo; // 头像

	private String oldLoginName;// 原登录名
	private String newPassword; // 新密码

	private String oldLoginIp; // 上次登陆IP
	private Date oldLoginDate; // 上次登陆日期

	private String sex;

	private Date birth;// 出生年月日
	private String address;// 地址
	private String edu;// 学历
	private String ex;// 工作经历
	private String isStudied;// 是否学过珠心算课程
	private String studiedName;// 曾就读机构
	private Integer zhusuanlv;// 珠算程度
	private Integer xinsuanlv;// 心算程度
	
	private String classTemp;//负责的班级
	
	
	public String getClassTemp() {
		return classTemp;
	}

	public void setClassTemp(String classTemp) {
		this.classTemp = classTemp;
	}

	@ExcelField(title = "身份", type = 0, align = 2, sort = 69)
	public String getIdentfy() {
		return identfy;
	}

	public void setIdentfy(String identfy) {
		this.identfy = identfy;
	}

	@ExcelField(title = "性别", type = 0, align = 2, sort = 71, dictType = "sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@ExcelField(title = "出生年月日", type = 0, align = 2, sort = 72)
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@ExcelField(title = "地址", type = 0, align = 2, sort = 73)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@ExcelField(title = "学历", type = 0, align = 2, sort = 74)
	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	@ExcelField(title = "工作经历", type = 0, align = 2, sort = 75)
	public String getEx() {
		return ex;
	}

	public void setEx(String ex) {
		this.ex = ex;
	}

	@ExcelField(title = "是否修读过珠心算课程:", align = 2, sort = 76, dictType = "yes_no")
	public String getIsStudied() {
		return isStudied;
	}

	public void setIsStudied(String isStudied) {
		this.isStudied = isStudied;
	}

	@ExcelField(title = "曾就读过的机构名:", type = 0, align = 2, sort = 77)
	public String getStudiedName() {
		return studiedName;
	}

	public void setStudiedName(String studiedName) {
		this.studiedName = studiedName;
	}

	@ExcelField(title = "珠算合格程度:", type = 0, align = 2, sort = 78, dictType = "zhusuanlv")
	public Integer getZhusuanlv() {
		return zhusuanlv;
	}

	public void setZhusuanlv(Integer zhusuanlv) {
		this.zhusuanlv = zhusuanlv;
	}

	@ExcelField(title = "心算合格程度:", type = 0, align = 2, sort = 79, dictType = "zhusuanlv")
	public Integer getXinsuanlv() {
		return xinsuanlv;
	}

	public void setXinsuanlv(Integer xinsuanlv) {
		this.xinsuanlv = xinsuanlv;
	}

	public String getBinded() {
		return binded;
	}

	public void setBinded(String binded) {
		this.binded = binded;
	}

	private String binded; // 绑定手机状态

	private Role role; // 根据角色查询用户条件

	private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表

	public User() {
		super();
		this.loginFlag = Global.YES;
	}

	public User(String id) {
		super(id);
	}

	public User(String id, String loginName) {
		super(id);
		this.loginName = loginName;
	}

	public User(Role role) {
		super();
		this.role = role;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	@SupCol(isUnique = "true", isHide = "true")
	public String getId() {
		return id;
	}

	@JsonIgnore
	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	@JsonIgnore
	@NotNull(message = "归属代理机构不能为空")
	@ExcelField(title = "归属代理机构", type = 0, align = 2, sort = 25)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@Length(min = 1, max = 100, message = "登录名长度必须介于 1 和 100 之间")
	@ExcelField(title = "登录名", type = 0, align = 2, sort = 30)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@JsonIgnore
	@Length(min = 1, max = 100, message = "密码长度必须介于 1 和 100 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Length(min = 1, max = 100, message = "姓名长度必须介于 1 和 100 之间")
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 40)
	public String getName() {
		return name;
	}

	@ExcelField(title = "身份证", type = 0, align = 2, sort = 45)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Email(message = "邮箱格式不正确")
	@Length(min = 0, max = 200, message = "邮箱长度必须介于 1 和 200 之间")
	@ExcelField(title = "邮箱", align = 1, sort = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(min = 0, max = 200, message = "电话长度必须介于 1 和 200 之间")
	@ExcelField(title = "电话", align = 2, sort = 60)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min = 0, max = 200, message = "手机长度必须介于 1 和 200 之间")
	@ExcelField(title = "手机", align = 2, sort = 70)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@ExcelField(title = "备注", align = 1, sort = 900)
	public String getRemarks() {
		return remarks;
	}

	@Length(min = 0, max = 100, message = "用户类型长度必须介于 1 和 100 之间")
	@ExcelField(title = "用户类型", align = 2, sort = 80, dictType = "sys_office_type")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@ExcelField(title = "创建时间", type = 1, align = 1, sort = 90)
	public Date getCreateDate() {
		return createDate;
	}

	@ExcelField(title = "最后登录IP", type = 1, align = 1, sort = 100)
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title = "最后登录日期", type = 1, align = 1, sort = 110)
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getOldLoginName() {
		return oldLoginName;
	}

	public void setOldLoginName(String oldLoginName) {
		this.oldLoginName = oldLoginName;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldLoginIp() {
		if (oldLoginIp == null) {
			return loginIp;
		}
		return oldLoginIp;
	}

	public void setOldLoginIp(String oldLoginIp) {
		this.oldLoginIp = oldLoginIp;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOldLoginDate() {
		if (oldLoginDate == null) {
			return loginDate;
		}
		return oldLoginDate;
	}

	public void setOldLoginDate(Date oldLoginDate) {
		this.oldLoginDate = oldLoginDate;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@JsonIgnore
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@JsonIgnore
	public List<String> getRoleIdList() {
		List<String> roleIdList = Lists.newArrayList();
		for (Role role : roleList) {
			roleIdList.add(role.getId());
		}
		return roleIdList;
	}

	public void setRoleIdList(List<String> roleIdList) {
		roleList = Lists.newArrayList();
		for (String roleId : roleIdList) {
			Role role = new Role();
			role.setId(roleId);
			roleList.add(role);
		}
	}

	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	public String getRoleNames() {
		return Collections3.extractToString(roleList, "name", ",");
	}

	public boolean isAdmin() {
		return isAdmin(this.id);
	}

	public static boolean isAdmin(String id) {
		return id != null && "1".equals(id);
	}

	@Override
	public String toString() {
		return id;
	}
}