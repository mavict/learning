package cn.itcast.service.impl;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.itcast.pojo.Category;

public class CategoryServiceImpl {

	private HibernateTemplate hibernateTemplate;
	
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> query(){
		return hibernateTemplate.find("FROM Category");
	}
}
