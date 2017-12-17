package com.thinkgem.jeesite.modules.user.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 玩家信息Entity
 * 
 * @author ThinkGem
 * @version 2013-12-05
 */
public class AppPlayInfo extends DataEntity<AppPlayInfo> {

	private static final long serialVersionUID = 1L;

	private String level, emp, worksLevel, worksEmp, multiLevel, multiEmp, divisLevel, divisEmp, powerFlag,
			qinglongLevel, qinglongEmp,

			qilingLevel, qilingEmp,

			baihuLevel, baihuEmp,

			zhuqueLevel, zhuqueEmp,

			xuanwuLevel, xuanwuEmp, therionFlag, gold,

			strawNum, pearNum, hamburgerNum, watermelonNum, baNum, coreScore, fiveScore, tenaScore, tenmScore,
			multiScore, divisScore, pkAm, pkMulti, pkDivis, skyAm, skyDivis, skyMulti, zsTestScore, xsTestScore,
			zsScore, xsScore, lessonsHis, authLevel, contestFlag, adminId, adminType, token;
	private Integer flashAm, flashDivis, flashMulti, tsAm, tsMulti, tsDivis;

	public Integer getFlashAm() {
		return flashAm;
	}

	public void setFlashAm(Integer flashAm) {
		this.flashAm = flashAm;
	}

	public Integer getFlashDivis() {
		return flashDivis;
	}

	public void setFlashDivis(Integer flashDivis) {
		this.flashDivis = flashDivis;
	}

	public Integer getFlashMulti() {
		return flashMulti;
	}

	public void setFlashMulti(Integer flashMulti) {
		this.flashMulti = flashMulti;
	}

	public Integer getTsAm() {
		return tsAm;
	}

	public void setTsAm(Integer tsAm) {
		this.tsAm = tsAm;
	}

	public Integer getTsMulti() {
		return tsMulti;
	}

	public void setTsMulti(Integer tsMulti) {
		this.tsMulti = tsMulti;
	}

	public Integer getTsDivis() {
		return tsDivis;
	}

	public void setTsDivis(Integer tsDivis) {
		this.tsDivis = tsDivis;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getGold() {
		return gold;
	}

	public void setGold(String gold) {
		this.gold = gold;
	}

	public String getTherionFlag() {
		return therionFlag;
	}

	public void setTherionFlag(String therionFlag) {
		this.therionFlag = therionFlag;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getEmp() {
		return emp;
	}

	public void setEmp(String emp) {
		this.emp = emp;
	}

	public String getWorksLevel() {
		return worksLevel;
	}

	public void setWorksLevel(String worksLevel) {
		this.worksLevel = worksLevel;
	}

	public String getWorksEmp() {
		return worksEmp;
	}

	public void setWorksEmp(String worksEmp) {
		this.worksEmp = worksEmp;
	}

	public String getMultiLevel() {
		return multiLevel;
	}

	public void setMultiLevel(String multiLevel) {
		this.multiLevel = multiLevel;
	}

	public String getMultiEmp() {
		return multiEmp;
	}

	public void setMultiEmp(String multiEmp) {
		this.multiEmp = multiEmp;
	}

	public String getDivisLevel() {
		return divisLevel;
	}

	public void setDivisLevel(String divisLevel) {
		this.divisLevel = divisLevel;
	}

	public String getDivisEmp() {
		return divisEmp;
	}

	public void setDivisEmp(String divisEmp) {
		this.divisEmp = divisEmp;
	}

	public String getPowerFlag() {
		return powerFlag;
	}

	public void setPowerFlag(String powerFlag) {
		this.powerFlag = powerFlag;
	}

	public String getQinglongLevel() {
		return qinglongLevel;
	}

	public void setQinglongLevel(String qinglongLevel) {
		this.qinglongLevel = qinglongLevel;
	}

	public String getQinglongEmp() {
		return qinglongEmp;
	}

	public void setQinglongEmp(String qinglongEmp) {
		this.qinglongEmp = qinglongEmp;
	}

	public String getQilingLevel() {
		return qilingLevel;
	}

	public void setQilingLevel(String qilingLevel) {
		this.qilingLevel = qilingLevel;
	}

	public String getQilingEmp() {
		return qilingEmp;
	}

	public void setQilingEmp(String qilingEmp) {
		this.qilingEmp = qilingEmp;
	}

	public String getBaihuLevel() {
		return baihuLevel;
	}

	public void setBaihuLevel(String baihuLevel) {
		this.baihuLevel = baihuLevel;
	}

	public String getBaihuEmp() {
		return baihuEmp;
	}

	public void setBaihuEmp(String baihuEmp) {
		this.baihuEmp = baihuEmp;
	}

	public String getZhuqueLevel() {
		return zhuqueLevel;
	}

	public void setZhuqueLevel(String zhuqueLevel) {
		this.zhuqueLevel = zhuqueLevel;
	}

	public String getZhuqueEmp() {
		return zhuqueEmp;
	}

	public void setZhuqueEmp(String zhuqueEmp) {
		this.zhuqueEmp = zhuqueEmp;
	}

	public String getXuanwuLevel() {
		return xuanwuLevel;
	}

	public void setXuanwuLevel(String xuanwuLevel) {
		this.xuanwuLevel = xuanwuLevel;
	}

	public String getXuanwuEmp() {
		return xuanwuEmp;
	}

	public void setXuanwuEmp(String xuanwuEmp) {
		this.xuanwuEmp = xuanwuEmp;
	}

	public String getStrawNum() {
		return strawNum;
	}

	public void setStrawNum(String strawNum) {
		this.strawNum = strawNum;
	}

	public String getPearNum() {
		return pearNum;
	}

	public void setPearNum(String pearNum) {
		this.pearNum = pearNum;
	}

	public String getHamburgerNum() {
		return hamburgerNum;
	}

	public void setHamburgerNum(String hamburgerNum) {
		this.hamburgerNum = hamburgerNum;
	}

	public String getWatermelonNum() {
		return watermelonNum;
	}

	public void setWatermelonNum(String watermelonNum) {
		this.watermelonNum = watermelonNum;
	}

	public String getBaNum() {
		return baNum;
	}

	public void setBaNum(String baNum) {
		this.baNum = baNum;
	}

	public String getCoreScore() {
		return coreScore;
	}

	public void setCoreScore(String coreScore) {
		this.coreScore = coreScore;
	}

	public String getFiveScore() {
		return fiveScore;
	}

	public void setFiveScore(String fiveScore) {
		this.fiveScore = fiveScore;
	}

	public String getTenaScore() {
		return tenaScore;
	}

	public void setTenaScore(String tenaScore) {
		this.tenaScore = tenaScore;
	}

	public String getTenmScore() {
		return tenmScore;
	}

	public void setTenmScore(String tenmScore) {
		this.tenmScore = tenmScore;
	}

	public String getMultiScore() {
		return multiScore;
	}

	public void setMultiScore(String multiScore) {
		this.multiScore = multiScore;
	}

	public String getDivisScore() {
		return divisScore;
	}

	public void setDivisScore(String divisScore) {
		this.divisScore = divisScore;
	}

	public String getPkAm() {
		return pkAm;
	}

	public void setPkAm(String pkAm) {
		this.pkAm = pkAm;
	}

	public String getPkMulti() {
		return pkMulti;
	}

	public void setPkMulti(String pkMulti) {
		this.pkMulti = pkMulti;
	}

	public String getPkDivis() {
		return pkDivis;
	}

	public void setPkDivis(String pkDivis) {
		this.pkDivis = pkDivis;
	}

	public String getSkyAm() {
		return skyAm;
	}

	public void setSkyAm(String skyAm) {
		this.skyAm = skyAm;
	}

	public String getSkyDivis() {
		return skyDivis;
	}

	public void setSkyDivis(String skyDivis) {
		this.skyDivis = skyDivis;
	}

	public String getSkyMulti() {
		return skyMulti;
	}

	public void setSkyMulti(String skyMulti) {
		this.skyMulti = skyMulti;
	}

	public String getZsTestScore() {
		return zsTestScore;
	}

	public void setZsTestScore(String zsTestScore) {
		this.zsTestScore = zsTestScore;
	}

	public String getXsTestScore() {
		return xsTestScore;
	}

	public void setXsTestScore(String xsTestScore) {
		this.xsTestScore = xsTestScore;
	}

	public String getZsScore() {
		return zsScore;
	}

	public void setZsScore(String zsScore) {

		this.zsScore = zsScore;
	}

	public String getXsScore() {
		return xsScore;
	}

	public void setXsScore(String xsScore) {
		this.xsScore = xsScore;
	}

	public String getLessonsHis() {
		return lessonsHis;
	}

	public void setLessonsHis(String lessonsHis) {
		this.lessonsHis = lessonsHis;
	}

	public String getAuthLevel() {
		return authLevel;
	}

	public void setAuthLevel(String authLevel) {
		this.authLevel = authLevel;
	}

	public String getContestFlag() {
		return contestFlag;
	}

	public void setContestFlag(String contestFlag) {
		this.contestFlag = contestFlag;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminType() {
		return adminType;
	}

	public void setAdminType(String adminType) {
		this.adminType = adminType;
	}

}