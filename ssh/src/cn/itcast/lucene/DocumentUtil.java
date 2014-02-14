package cn.itcast.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

import cn.itcast.pojo.Category;
import cn.itcast.pojo.Goods;

/*
 * ��Ŀ��: ���еĹ�����(û��ȫ������)
 * 
 * ���������õ���ģʽ,��ģʽ
 * 
 * */
public class DocumentUtil {

	public static Document GoodsToDocument(Goods goods){
		// ��Goodsת��Ϊlucene����ʶ���document����
		Document doc=new Document();
		// ��goods�����е�ÿ������,ת��Ϊlucene�е��ֶΣ�ע��Field���ֶ�����ֻ����String����
		doc.add(new Field("gid", goods.getGid().toString(),Store.YES,Index.NOT_ANALYZED));
		doc.add(new Field("gname", goods.getGname(),Store.YES,Index.ANALYZED));
		doc.add(new Field("gpic", goods.getGpic(), Store.YES, Index.NO));
		doc.add(new Field("gprice", goods.getGprice().toString(),Store.YES,Index.NO));
//		doc.add(new Field("ctype"),goods.getCategory().getCtype(),Store.YES,Index.NOT_ANALYZED);
		doc.add(new Field("gremark", goods.getGremark(),Store.YES,Index.ANALYZED));
		return doc;
	}
	
	public static Goods DocumentToGoods(Document doc){
		// ��Document����ת�������Լ�ʶ�������
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
