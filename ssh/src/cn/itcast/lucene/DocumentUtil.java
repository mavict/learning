package cn.itcast.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

import cn.itcast.pojo.Category;
import cn.itcast.pojo.Goods;

/*
 * 项目中: 所有的工具类(没有全局属性)
 * 
 * 都可以设置单例模式,类模式
 * 
 * */
public class DocumentUtil {

	public static Document GoodsToDocument(Goods goods){
		// 把Goods转化为lucene可以识别的document类型
		Document doc=new Document();
		// 把goods对象中的每个属性,转化为lucene中的字段，注意Field的字段类型只能是String类型
		doc.add(new Field("gid", goods.getGid().toString(),Store.YES,Index.NOT_ANALYZED));
		doc.add(new Field("gname", goods.getGname(),Store.YES,Index.ANALYZED));
		doc.add(new Field("gpic", goods.getGpic(), Store.YES, Index.NO));
		doc.add(new Field("gprice", goods.getGprice().toString(),Store.YES,Index.NO));
//		doc.add(new Field("ctype"),goods.getCategory().getCtype(),Store.YES,Index.NOT_ANALYZED);
		doc.add(new Field("gremark", goods.getGremark(),Store.YES,Index.ANALYZED));
		return doc;
	}
	
	public static Goods DocumentToGoods(Document doc){
		// 把Document类型转化我们自己识别的类型
		Goods goods=new Goods();
		goods.setGid(Integer.parseInt(doc.get("gid")));
		goods.setGname(doc.get("gname"));
		goods.setGprice(Double.parseDouble(doc.get("gprice")));
		goods.setGpic(doc.get("gpic"));
		goods.setGremark(doc.get("gremark"));
		Category category=new Category();
		category.setCtype(doc.get("ctype"));
		goods.setCategory(category);
		return goods;
	}
}
