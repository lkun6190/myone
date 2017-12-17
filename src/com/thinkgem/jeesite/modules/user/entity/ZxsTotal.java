package com.thinkgem.jeesite.modules.user.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 班级Entity
 * 
 * @author ThinkGem
 * @version 2013-12-05
 */
public class ZxsTotal extends DataEntity<ZxsTotal> {

	private static final long serialVersionUID = 1L;

	private Integer oneOfficeNum;// 一级代理数量
	private Integer twoOfficeNum;// 二级代理数量
	private Integer threeOfficeNum;// 三级代理数量
	private Integer classNum;// 班级数量
	private Integer stuNum;// 学生的数量
	private Integer teaNum;// 老师的数量
	private Integer userStuNum;// 使用的学生数量
	private Integer onlineStuNum;// 在线学生数量
	private Integer flagStuNum;// 激活的学生数量
	private Integer userTeaNum;// 使用的老师数量
	private Integer onlineTeaNum;// 在线老师数量
	private Integer flagTeaNum;// 激活的老师数量
	private Integer month;// 月份
	private Integer year;// 年份
	private Office office;

	public Integer getMonth() {

		return month;
	}

	public void setMonth(Integer month) {

		this.month = month;
	}

	public Integer getYear() {

		return year;
	}

	public Integer getFlagStuNum() {
		return flagStuNum;
	}

	public void setFlagStuNum(Integer flagStuNum) {
		this.flagStuNum = flagStuNum;
	}

	public Integer getFlagTeaNum() {
		return flagTeaNum;
	}

	public void setFlagTeaNum(Integer flagTeaNum) {
		this.flagTeaNum = flagTeaNum;
	}

	public void setYear(Integer year) {

		this.year = year;
	}

	public Office getOffice() {

		return office;
	}

	public void setOffice(Office office) {

		this.office = office;
	}

	public Integer getOneOfficeNum() {

		return oneOfficeNum;
	}

	public void setOneOfficeNum(Integer oneOfficeNum) {

		this.oneOfficeNum = oneOfficeNum;
	}

	public Integer getTwoOfficeNum() {

		return twoOfficeNum;
	}

	public void setTwoOfficeNum(Integer twoOfficeNum) {

		this.twoOfficeNum = twoOfficeNum;
	}

	public Integer getThreeOfficeNum() {

		return threeOfficeNum;
	}

	public void setThreeOfficeNum(Integer threeOfficeNum) {

		this.threeOfficeNum = threeOfficeNum;
	}

	public Integer getClassNum() {

		return classNum;
	}

	public void setClassNum(Integer classNum) {

		this.classNum = classNum;
	}

	public Integer getStuNum() {

		return stuNum;
	}

	public void setStuNum(Integer stuNum) {

		this.stuNum = stuNum;
	}

	public Integer getTeaNum() {

		return teaNum;
	}

	public void setTeaNum(Integer teaNum) {

		this.teaNum = teaNum;
	}

	public Integer getUserStuNum() {

		return userStuNum;
	}

	public void setUserStuNum(Integer userStuNum) {

		this.userStuNum = userStuNum;
	}

	public Integer getOnlineStuNum() {
		return onlineStuNum;
	}

	public void setOnlineStuNum(Integer onlineStuNum) {
		this.onlineStuNum = onlineStuNum;
	}

	public Integer getUserTeaNum() {
		return userTeaNum;
	}

	public void setUserTeaNum(Integer userTeaNum) {
		this.userTeaNum = userTeaNum;
	}

	public Integer getOnlineTeaNum() {
		return onlineTeaNum;
	}

	public void setOnlineTeaNum(Integer onlineTeaNum) {
		this.onlineTeaNum = onlineTeaNum;
	}

}