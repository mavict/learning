package cn.itcast.action;

import org.apache.struts2.ServletActionContext;

import cn.itcast.lucene.LuceneServiceImpl;
import cn.itcast.pojo.Goods;
import cn.itcast.service.impl.GoodsServiceImpl;

public class GoodsAction{

	private GoodsServiceImpl goodsServiceImpl=null;
	
	private LuceneServiceImpl luceneServiceImpl=null;
	
	public void setLuceneServiceImpl(LuceneServiceImpl luceneServiceImpl) {
		this.luceneServiceImpl = luceneServiceImpl;
	}
	
	public void setGoodsServiceImpl(GoodsServiceImpl goodsServiceImpl) {
		this.goodsServiceImpl = goodsServiceImpl;
	}
	
	private Goods goods;
	
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public String save(){
		System.out.println("----save----");
		goodsServiceImpl.save(goods);
		return "success";
	}
	
	public String get(){
		// 从索引库获取数据存储到request内置对象中
		ServletActionContext.getRequest().setAttribute("goodsList",luceneServiceImpl.query(goods.getGname(),1));
		return "search";
	}
	
	public String get2(){
		ServletActionContext.getRequest().setAttribute("goods",goodsServiceImpl.get(goods.getGid()));
		return "detail";
	}
}
