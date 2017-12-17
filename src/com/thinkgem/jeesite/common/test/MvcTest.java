package com.thinkgem.jeesite.common.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.thinkgem.jeesite.common.utils.HttpRequest;



/**
 *使用Spring测试模块提供的测试请求功能,测试curd请求的正确性
 * 
 * @desc: rtedu_maven
 * @author: lkun
 * @createTime
 * @history:
 * @version: v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:spring-context.xml",
		"classpath:spring-mvc.xml" })
public class MvcTest {
	@Autowired
	WebApplicationContext context;
	// ����mvc����,��ȡ��������
	MockMvc mockMvc;

	@Before
	public void initMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
@Test
	public void testPage() {
	System.out.println("99999999999999");
		try {
			MvcResult mvcResult = mockMvc.perform(
					MockMvcRequestBuilders.post("/f/app/reg").param("phone", "13662391904"))
					.andReturn();
			MockHttpServletRequest request=mvcResult.getRequest();
		
			
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
public static void main(String[] args) {
	String param="phone=13662391904&password=123456&identCode=6187";
	String result=HttpRequest.sendPost("http://localhost:8080/rtedu/f/app/reg", param);
	System.out.println("====="+result);
	//&password=12345678&identCode=1234
	//8d2c4f6e878a0edbbbb231f788357123b048bb634e3a4c738df597e0
	
	
}

}
