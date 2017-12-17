package com.thinkgem.jeesite.modules.user.entity;

import java.util.ArrayList;
import java.util.List;

public class StudyDate {
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static List<StudyDate> getStudyDateList() {
		List<StudyDate> allStudyDate = new ArrayList<StudyDate>();
		StudyDate stu1 = new StudyDate();
		StudyDate stu2 = new StudyDate();
		StudyDate stu3 = new StudyDate();
		StudyDate stu4 = new StudyDate();
		StudyDate stu5 = new StudyDate();
		StudyDate stu6 = new StudyDate();
		StudyDate stu7 = new StudyDate();
		stu1.setId("一");
		stu1.setName("周一");
		allStudyDate.add(stu1);
		stu2.setId("二");
		stu2.setName("周二");
		allStudyDate.add(stu2);
		stu3.setId("三");
		stu3.setName("周三");
		allStudyDate.add(stu3);
		stu4.setId("四");
		stu4.setName("周四");
		allStudyDate.add(stu4);
		stu5.setId("五");
		stu5.setName("周五");
		allStudyDate.add(stu5);
		stu6.setId("六");
		stu6.setName("周六");
		allStudyDate.add(stu6);
		stu7.setId("日");
		stu7.setName("周日");
		allStudyDate.add(stu7);
		return allStudyDate;
	}
}
