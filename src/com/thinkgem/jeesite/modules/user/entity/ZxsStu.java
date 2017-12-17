package com.thinkgem.jeesite.modules.user.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 学生Entity
 * 
 * @author ThinkGem
 * @version 2013-12-05
 */
public class ZxsStu extends DataEntity<ZxsStu> {

	private static final long serialVersionUID = 1L;
	
	private List<String> zxsClaArr;

	
	private String autoLevel="0";

	private String oldStuNo;// 老账号

	private AppPlayInfo appPlayInfo;
	private String sign;//'0'表示新生，1旧生
	
	private String stuNo;// 学生账号
	private String pwd;// 密码
	private String token;// token值

	private String stuName;// 学生姓名
	
	private String zxsClaString;
	
	private String score;
	
	
	

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@ExcelField(title = "是否升级(Y/N)", type = 0, align = 2, sort = 60, dictType = "Y_N")
	public String getAutoLevel() {
		return autoLevel;
	}

	public void setAutoLevel(String autoLevel) {
		this.autoLevel = autoLevel;
	}

	public List<String> getZxsClaArr() {
		return zxsClaArr;
	}

	public void setZxsClaArr(List<String> zxsClaArr) {
		this.zxsClaArr = zxsClaArr;
	}

	public String getZxsClaString() {
		return zxsClaString;
	}

	public void setZxsClaString(String zxsClaString) {
		this.zxsClaString = zxsClaString;
	}

	

	public AppPlayInfo getAppPlayInfo() {
		return appPlayInfo;
	}

	public void setAppPlayInfo(AppPlayInfo appPlayInfo) {
		this.appPlayInfo = appPlayInfo;
	}

	//@Pattern(regexp = "^([0-9]{0,10})$", message = "代币必须是数字且长度不大于10位")
	private String gold;// 代币

	private Integer age;// 年龄

	private String phone;// 头像

	private Date birth;// 出生年月日

	private String no;// 身份证号码

	private String email;// 电子邮箱

	private Integer isStudied;// 是否学过珠心算课程
	private String studiedName;// 曾就读机构
	private Integer payment;// 是否缴费 0没有，1有
	private Date payDate;// 缴费时间

	private String tel;// 手机号码

	private String parentName;// 家长姓名

	private String parentTel;// 家长电话

	private String rex;// 与家长的关系

	private String address;// 地址
	private String sex;// 性别 1男2女
	private String zhusuanlv;// 珠算合格程度
	private String xinsuanlv;// 心算合格程度
	private Date entryDate;// 入学时间

	private String newPassword; // 新密码

	private Date lastLoginDate;

	private String root_tmp;
	
	

	public String getRoot_tmp() {
		return root_tmp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
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

	public String getOldStuNo() {

		return oldStuNo;
	}

	public void setOldStuNo(String oldStuNo) {

		this.oldStuNo = oldStuNo;
	}

	public String getNewPassword() {

		return newPassword;
	}

	public void setNewPassword(String newPassword) {

		this.newPassword = newPassword;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	private ZxsClass zxsClass;// 所在班级
	private Office office;// 所在机构/学校
	private String statu;// 是否在线 0未在线 1在线

	@NotNull(message = "所在班级不能为空")
	@ExcelField(title = "班级名称", type = 0, align = 2, sort = 10)
	public ZxsClass getZxsClass() {
		return zxsClass;
	}

	public void setZxsClass(ZxsClass zxsClass) {
		this.zxsClass = zxsClass;
	}

	@ExcelField(title = "机构学校", type = 0, align = 2, sort = 9)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@NotNull(message = "账号不能为空")
	@ExcelField(title = "学生账号", type = 0, align = 2, sort = 1)
	public String getStuNo() {
		return stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@NotNull(message = "姓名不能为空")
	@ExcelField(title = "学生姓名", align = 2, sort = 8)
	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	//@ExcelField(title = "代币", align = 2, sort = 16)
	public String getGold() {
		return gold;
	}

	public void setGold(String gold) {
		this.gold = gold;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@ExcelField(title = "出生", align = 2, sort = 12)
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@ExcelField(title = "身份证", align = 2, sort = 13)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Email(message = "邮箱格式不正确")
	@ExcelField(title = "邮箱", align = 2, sort = 15)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ExcelField(title = "是否修读过珠心算课程", align = 2, sort = 17, dictType = "yes_no")
	public Integer getIsStudied() {
		return isStudied;
	}

	public void setIsStudied(Integer isStudied) {
		this.isStudied = isStudied;
	}

	@ExcelField(title = "曾就读过的机构名", type = 0, align = 2, sort = 18)
	public String getStudiedName() {
		return studiedName;
	}

	public void setStudiedName(String studiedName) {
		this.studiedName = studiedName;
	}

	@ExcelField(title = "是否缴费", type = 0, align = 2, sort = 19, dictType = "yes_no")
	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	@ExcelField(title = "缴费时间", type = 0, align = 2, sort = 20)
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	@ExcelField(title = "手机", align = 2, sort = 14)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@ExcelField(title = "家长姓名", type = 0, align = 2, sort = 24)
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@ExcelField(title = "家长电话", type = 0, align = 2, sort = 25)
	public String getParentTel() {
		return parentTel;
	}

	public void setParentTel(String parentTel) {
		this.parentTel = parentTel;
	}

	@ExcelField(title = "家长关系", type = 0, align = 2, sort = 26)
	public String getRex() {
		return rex;
	}

	public void setRex(String rex) {
		this.rex = rex;
	}

	@ExcelField(title = "地址", type = 0, align = 2, sort = 27)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@ExcelField(title = "性别", type = 0, align = 2, sort = 11, dictType = "sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@ExcelField(title = "珠算合格程度", type = 0, align = 2, sort = 21, dictType = "zhusuanlv")
	public String getZhusuanlv() {
		return zhusuanlv;
	}

	public void setZhusuanlv(String zhusuanlv) {
		this.zhusuanlv = zhusuanlv;
	}

	@ExcelField(title = "心算合格程度", type = 0, align = 2, sort = 22, dictType = "zhusuanlv")
	public String getXinsuanlv() {
		return xinsuanlv;
	}

	public void setXinsuanlv(String xinsuanlv) {
		this.xinsuanlv = xinsuanlv;
	}

	@ExcelField(title = "入学时间", type = 0, align = 2, sort = 23)
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

}