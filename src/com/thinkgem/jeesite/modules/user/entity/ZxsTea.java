package com.thinkgem.jeesite.modules.user.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Email;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 老师Entity
 * 
 * @author ThinkGem
 * @version 2013-12-05
 */
public class ZxsTea extends DataEntity<ZxsTea> {

	private static final long serialVersionUID = 1L;

	private AppPlayInfo appPlayInfo;
	private String sign;
	private String autoLevel = "0";

	private String newPassword;
	private String oldTeaNo;
	private Date lastLoginDate;

	private String teaNo;// 账号 10
	private String pwd;// 密码
	private String teaName;// 老师姓名 20
	private Date birth;// 出生年月日 41
	private Integer age;// 年龄
	private String tel;// 手机号码 40
	private String sex;// 性别 30
	private String phone;// 头像

	private String zhusuanlv;// 珠算合格程度 48
	private String xinsuanlv;// 心算合格程度 49
	private Date entryDate;// 入职时间 42

	private Office office;// 所属机构/学校 5

	private String no;// 身份证 25
	private String email;// 邮箱 45
	private String address;// 地址 52
	private String edu;// 学历 46
	private String ex;// 工作经历 47
	private Integer isStudied;// 是否学过珠心算课程 50
	private String studiedName;// 曾就读机构 51

	private String statu;
	private String token;

	private String root_tmp;

	@ExcelField(title = "是否升级(Y/N)", type = 0, align = 2, sort = 60, dictType = "Y_N")
	public String getAutoLevel() {
		return autoLevel;
	}

	public void setAutoLevel(String autoLevel) {
		this.autoLevel = autoLevel;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getRoot_tmp() {
		return root_tmp;
	}

	public AppPlayInfo getAppPlayInfo() {
		return appPlayInfo;
	}

	public void setAppPlayInfo(AppPlayInfo appPlayInfo) {
		this.appPlayInfo = appPlayInfo;
	}

	public void setRoot_tmp(String root_tmp) {
		this.root_tmp = root_tmp;
	}
	@ExcelField(title = "最后登录时间", type = 1, align = 2, sort = 70)
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static long getSerialversionuid() {

		return serialVersionUID;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldTeaNo() {
		return oldTeaNo;
	}

	public void setOldTeaNo(String oldTeaNo) {
		this.oldTeaNo = oldTeaNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@ExcelField(title = "账号", type = 0, align = 2, sort = 10)
	public String getTeaNo() {
		return teaNo;
	}

	public void setTeaNo(String teaNo) {
		this.teaNo = teaNo;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@ExcelField(title = "姓名", type = 0, align = 2, sort = 20)
	public String getTeaName() {
		return teaName;
	}

	public void setTeaName(String teaName) {
		this.teaName = teaName;
	}

	@ExcelField(title = "出生", type = 0, align = 2, sort = 41)
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@ExcelField(title = "手机号码", type = 0, align = 2, sort = 40)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@ExcelField(title = "性别", type = 0, align = 2, sort = 30, dictType = "sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@ExcelField(title = "机构或学校", type = 0, align = 2, sort = 5)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@ExcelField(title = "珠算合格程度", type = 0, align = 2, sort = 48, dictType = "zhusuanlv")
	public String getZhusuanlv() {
		return zhusuanlv;
	}

	public void setZhusuanlv(String zhusuanlv) {
		this.zhusuanlv = zhusuanlv;
	}

	@ExcelField(title = "心算合格程度", type = 0, align = 2, sort = 49, dictType = "zhusuanlv")
	public String getXinsuanlv() {
		return xinsuanlv;
	}

	public void setXinsuanlv(String xinsuanlv) {
		this.xinsuanlv = xinsuanlv;
	}

	@ExcelField(title = "入职时间", type = 0, align = 2, sort = 42)
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	@ExcelField(title = "身份证", type = 0, align = 2, sort = 25)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Email(message = "邮箱格式不正确")
	@ExcelField(title = "邮箱", type = 0, align = 2, sort = 45)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ExcelField(title = "地址", type = 0, align = 2, sort = 52)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@ExcelField(title = "学历", type = 0, align = 2, sort = 46)
	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	@ExcelField(title = "工作经历", type = 0, align = 2, sort = 47)
	public String getEx() {
		return ex;
	}

	public void setEx(String ex) {
		this.ex = ex;
	}

	@ExcelField(title = "是否学过珠心算课程 ", type = 0, align = 2, sort = 50, dictType = "yes_no")
	public Integer getIsStudied() {
		return isStudied;
	}

	public void setIsStudied(Integer isStudied) {
		this.isStudied = isStudied;
	}

	@ExcelField(title = "曾就读机构", type = 0, align = 2, sort = 51)
	public String getStudiedName() {
		return studiedName;
	}

	public void setStudiedName(String studiedName) {
		this.studiedName = studiedName;
	}

}