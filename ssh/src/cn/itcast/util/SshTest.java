package cn.itcast.util;


import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.lucene.LuceneServiceImpl;
import cn.itcast.lucene.LuceneUtil;
import cn.itcast.pojo.Category;
import cn.itcast.pojo.Goods;
import cn.itcast.service.impl.GoodsServiceImpl;

public class SshTest {
	
	private static GoodsServiceImpl goodsServiceImpl=null;
	
	private static LuceneServiceImpl luceneServiceImpl=null;
	
	private static ClassPathXmlApplicationContext context=null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context=new ClassPathXmlApplicationContext("applicationContext-*.xml");
		goodsServiceImpl=(GoodsServiceImpl)context.getBean("goodsServiceImpl");
		luceneServiceImpl=(LuceneServiceImpl)context.getBean("luceneServiceImpl");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		luceneServiceImpl=null;
		goodsServiceImpl=null;
		context.destroy();
	}
	
//	public static void main(String[] args) {
//		new ClassPathXmlApplicationContext("applicationContext-*.xml");
//	}
	
	@Test
	public void testHibernate(){
		Session session=HibernateSessionFactory.getSession();
		session.getTransaction().begin();
		Goods goods=new Goods();
		goods.setGid(1);
		goods.setGname("hello world ");
		goods.setGpic("1.jpg");
		goods.setGprice(34.5);
		goods.setGremark("简单介绍");
		goods.setGxremark("详细介绍");
		//添加类别信息
		Category category=new Category();
		category.setCid(1);
		goods.setCategory(category);
		session.save(goods);
		session.getTransaction().commit();
	}
	
	@Test  //测试Hibernate Spring整合没有事务
	public void testHibernateSpring(){
		Goods goods=new Goods();
		goods.setGid(1); // 
		goods.setGname("hello world2 ");
		goods.setGpic("1.jpg");
		goods.setGprice(34.5);
		goods.setGremark("简单介绍");
		goods.setGxremark("详细介绍");
		//添加类别信息
		Category category=new Category();
		category.setCid(1);
		goods.setCategory(category);
		goodsServiceImpl.save(goods);
	}
	
	@Test
	public void testLuceneUtil(){
		Goods goods=new Goods();
		goods.setGid(1);
		goods.setGname("hello world ");
		goods.setGpic("1.jpg");
		goods.setGprice(34.5);
		goods.setGremark("简单介绍");
		goods.setGxremark("详细介绍");
		//添加类别信息
		Category category=new Category();
		category.setCid(1);
		goods.setCategory(category);
		luceneServiceImpl.saveGoods(goods);
	}
	@Test
	public void testDelete(){
		goodsServiceImpl.delete(11);
	}

}
