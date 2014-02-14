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
		// ��ȡ����ҵ���߼���,���ò�ѯ�ķ���,��ѯ���������Ϣ,�洢��Application���ö�����
		// ��ȡApplication���ö�����Spring�����ļ�
		ApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		categoryServiceImpl=(CategoryServiceImpl)context.getBean("categoryServiceImpl");
		event.getServletContext().setAttribute("categorys",categoryServiceImpl.query());
		// ��ȡ��Ŀ�ľ���·��,д�뵽properties
		System.out.println(event.getServletContext().getRealPath("/"));
	}

}
