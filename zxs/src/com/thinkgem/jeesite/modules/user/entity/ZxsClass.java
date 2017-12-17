package com.thinkgem.jeesite.modules.user.entity;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 班级Entity
 * 
 * @author ThinkGem
 * @version 2013-12-05
 */
public class ZxsClass extends DataEntity<ZxsClass> {

	private static final long serialVersionUID = 1L;


	private String oldClassNo;//旧班级名称
	
	private String oldTeaId;// 旧的老师id

	private String classNo;// 班级名称 10

	private String className;// 课室名称 11

	private Integer classStuNum;// 学生人数
	private String studyDate;// 上课时间
	private String studyTimeStart;// 上课开始时段 13
	private String studyTimeEnd;// 上课结束时段 14

	private Office office;// 所属机构/学校 9
	private ZxsTea zxsTea;// 上课负责老师 15

	private String zxsTeaIdTemp;
	

	public String getOldClassNo() {
		return oldClassNo;
	}

	public void setOldClassNo(String oldClassNo) {
		this.oldClassNo = oldClassNo;
	}

	private List<String> studyDateIdList;// 上课时间
	


	public List<String> getStudyDateIdList() {
		return studyDateIdList;
	}

	public void setStudyDateIdList(List<String> studyDateIdList) {
		this.studyDateIdList = studyDateIdList;
	}

	public String getZxsTeaIdTemp() {
		return zxsTeaIdTemp;
	}

	public void setZxsTeaIdTemp(String zxsTeaIdTemp) {
		this.zxsTeaIdTemp = zxsTeaIdTemp;
	}

	@Length(min = 1, max = 30, message = "班级名称 长度必须介于 1 和 30之间")
	@ExcelField(title = "班级名称", align = 2, sort = 10)
	public String getClassNo() {
		return classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	@Length(min = 1, max = 30, message = "课室名称长度必须介于 1 和 30之间")
	@ExcelField(title = "课室名称", align = 2, sort = 11)
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getClassStuNum() {
		return classStuNum;
	}

	public void setClassStuNum(Integer classStuNum) {
		this.classStuNum = classStuNum;
	}

	@ExcelField(title = "上课日程", align = 2, sort = 12)
	public String getStudyDate() {
		return studyDate;
	}

	public void setStudyDate(String studyDate) {
		this.studyDate = studyDate;
	}

	@ExcelField(title = "上课开始时段", align = 2, sort = 13)
	public String getStudyTimeStart() {
		return studyTimeStart;
	}

	public void setStudyTimeStart(String studyTimeStart) {
		this.studyTimeStart = studyTimeStart;
	}

	@ExcelField(title = "上课结束时段", align = 2, sort = 14)
	public String getStudyTimeEnd() {
		return studyTimeEnd;
	}

	public void setStudyTimeEnd(String studyTimeEnd) {
		this.studyTimeEnd = studyTimeEnd;
	}

	@ExcelField(title = "机构或学校", align = 2, sort = 9)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	//@ExcelField(title = "负责老师", align = 2, sort = 15)
	public ZxsTea getZxsTea() {
		return zxsTea;
	}

	public void setZxsTea(ZxsTea zxsTea) {
		this.zxsTea = zxsTea;
	}

	public String getOldTeaId() {
		return oldTeaId;
	}

	public void setOldTeaId(String oldTeaId) {
		this.oldTeaId = oldTeaId;
	}

}