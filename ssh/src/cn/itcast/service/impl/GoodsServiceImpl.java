package cn.itcast.service.impl;

import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.itcast.lucene.LuceneServiceImpl;
import cn.itcast.pojo.Goods;

public class GoodsServiceImpl {

	private HibernateTemplate hibernateTemplate=null;
	
//	private LuceneServiceImpl luceneServiceImpl=null;
//	
//	public void setLuceneServiceImpl(LuceneServiceImpl luceneServiceImpl) {
//		this.luceneServiceImpl = luceneServiceImpl;
//	}
	
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public void save(Goods goods){
		hibernateTemplate.save(goods);
//		luceneServiceImpl.saveGoods(goods);
	}
	
	public Goods get(int gid){
		return hibernateTemplate.get(Goods.class, gid);
	}
	
	public void delete(int gid){
		Goods goods=new Goods();
		goods.setGid(gid);
		System.out.println("-----delete----");
		hibernateTemplate.delete(goods);
	}
}
