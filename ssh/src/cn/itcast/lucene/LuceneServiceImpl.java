package cn.itcast.lucene;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.aspectj.lang.JoinPoint;
import org.wltea.analyzer.lucene.IKQueryParser;

import cn.itcast.pojo.Goods;

/*
 * 完成索引的插入和查询功能
 * */
public class LuceneServiceImpl {
	
	private LuceneUtil luceneUtil=null;
	
	private HighlighterUtil highlighterUtil=null;
	
	public void setHighlighterUtil(HighlighterUtil highlighterUtil) {
		this.highlighterUtil = highlighterUtil;
	}

	public void setLuceneUtil(LuceneUtil luceneUtil) {
		this.luceneUtil = luceneUtil;
	}
	
	public void save(JoinPoint joinPoint){
		this.saveGoods((Goods)joinPoint.getArgs()[0]);
	}
	
	// 把商品信息添加到索引库中
	public void saveGoods(Goods goods){
		// 创建一个indexWriter
		IndexWriter indexWriter=null;
		try {
			indexWriter=luceneUtil.getIndexWriter();
			// 把document添加到索引库中
			indexWriter.addDocument(DocumentUtil.GoodsToDocument(goods));
			// 提交
			indexWriter.commit();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	// 把商品信息添加到索引库中
	public void updateGoods(Goods goods){
		// 创建一个indexWriter
		IndexWriter indexWriter=null;
		try {
			indexWriter=luceneUtil.getIndexWriter();
			/*
			 * 先删除文档,在追加文档
			 * Term: 删除文档的条件,一般来说我们都是根据主键删除
			 * doc:追加的新文档
			 * */
			indexWriter.updateDocument(new Term("gid",goods.getGid().toString()),DocumentUtil.GoodsToDocument(goods));
			// 提交
			indexWriter.commit();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public void delete(JoinPoint joinPoint){
		this.deleteGoods(Integer.parseInt(joinPoint.getArgs()[0].toString()));
	}
	
	// 把商品信息添加到索引库中
	public void deleteGoods(int gid){
		// 创建一个indexWriter
		IndexWriter indexWriter=null;
		try {
			indexWriter=luceneUtil.getIndexWriter();
			indexWriter.deleteDocuments(new Term("gid",gid+""));
			// 提交
			indexWriter.commit();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public List<Goods> query(String gname,int currentPage){
		int size=100;  // 2*5--->10条
		List<Goods> goodsList=new ArrayList<Goods>();
		// 创建查询工具类
		IndexSearcher indexSearcher=null;
		try {
			Query query=IKQueryParser.parseMultiField(new String[]{"gname","gremark"}, gname);
			indexSearcher=luceneUtil.getIndexSearcher();
			/*
			 * 可以在查询之前设置排序功能
			 * 
			 * Lucene中有两种方式: 
			 *    1: 按照字段排序,SortField
			 *    2: 文档的积分排序
			 * SortField:
			 *     gid: 排序的字段名称
			 *     type: 排序字段的类型
			 *     boolean: 是否反向
			 *     
			 * */ 
			Sort sort=new Sort(new SortField("gid",SortField.INT,true));
			TopDocs topDocs=indexSearcher.search(query,null,currentPage*size,sort);
			System.out.println("用户期待的数:" + currentPage*size);
			System.out.println("索引库实际拥有结果数为:" + topDocs.totalHits);
			// 存储的是document在lucenen中的逻辑编号
			ScoreDoc[] docs=topDocs.scoreDocs;  //[0]=0 [1]=1
			System.out.println("真实拿出来了文档编号数" + docs.length);
			for(int i=(currentPage-1)*size;i<docs.length;i++){
				System.out.println("文档的编号:" + docs[i].doc);
				System.out.println("此文档的得分:" + docs[i].score);
				Document doc=indexSearcher.doc(docs[i].doc);
				// 获取高亮后的数据
				String temp=highlighterUtil.setHightData(query,doc.get("gname"),10);
				String temp2=highlighterUtil.setHightData(query,doc.get("gremark"),40);
				// 把高亮之后的数据重新赋值个document 字段
				doc.getField("gname").setValue(temp);
				doc.getField("gremark").setValue(temp2);
				goodsList.add(DocumentUtil.DocumentToGoods(doc));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return goodsList;
	}	
}
