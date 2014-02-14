package cn.itcast.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itcast.service.impl.CategoryServiceImpl;

public class SshServletContextListener implements ServletContextListener {
	
	private CategoryServiceImpl categoryServiceImpl=null;

	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub

	}

	public void contextInitialized(ServletContextEvent event) {
		// 获取类别的业务逻辑类,调用查询的方法,查询所有类别信息,存储到Application内置对象中
		// 获取Application内置对象中Spring配置文件
		ApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		categoryServiceImpl=(CategoryServiceImpl)context.getBean("categoryServiceImpl");
		event.getServletContext().setAttribute("categorys",categoryServiceImpl.query());
		// 获取项目的绝对路径,写入到properties
		System.out.println(event.getServletContext().getRealPath("/"));
	}

}
