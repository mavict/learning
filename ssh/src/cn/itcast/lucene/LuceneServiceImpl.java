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
 * ��������Ĳ���Ͳ�ѯ����
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
	
	// ����Ʒ��Ϣ��ӵ���������
	public void saveGoods(Goods goods){
		// ����һ��indexWriter
		IndexWriter indexWriter=null;
		try {
			indexWriter=luceneUtil.getIndexWriter();
			// ��document��ӵ���������
			indexWriter.addDocument(DocumentUtil.GoodsToDocument(goods));
			// �ύ
			indexWriter.commit();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	// ����Ʒ��Ϣ��ӵ���������
	public void updateGoods(Goods goods){
		// ����һ��indexWriter
		IndexWriter indexWriter=null;
		try {
			indexWriter=luceneUtil.getIndexWriter();
			/*
			 * ��ɾ���ĵ�,��׷���ĵ�
			 * Term: ɾ���ĵ�������,һ����˵���Ƕ��Ǹ�������ɾ��
			 * doc:׷�ӵ����ĵ�
			 * */
			indexWriter.updateDocument(new Term("gid",goods.getGid().toString()),DocumentUtil.GoodsToDocument(goods));
			// �ύ
			indexWriter.commit();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public void delete(JoinPoint joinPoint){
		this.deleteGoods(Integer.parseInt(joinPoint.getArgs()[0].toString()));
	}
	
	// ����Ʒ��Ϣ��ӵ���������
	public void deleteGoods(int gid){
		// ����һ��indexWriter
		IndexWriter indexWriter=null;
		try {
			indexWriter=luceneUtil.getIndexWriter();
			indexWriter.deleteDocuments(new Term("gid",gid+""));
			// �ύ
			indexWriter.commit();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public List<Goods> query(String gname,int currentPage){
		int size=100;  // 2*5--->10��
		List<Goods> goodsList=new ArrayList<Goods>();
		// ������ѯ������
		IndexSearcher indexSearcher=null;
		try {
			Query query=IKQueryParser.parseMultiField(new String[]{"gname","gremark"}, gname);
			indexSearcher=luceneUtil.getIndexSearcher();
			/*
			 * �����ڲ�ѯ֮ǰ����������
			 * 
			 * Lucene�������ַ�ʽ: 
			 *    1: �����ֶ�����,SortField
			 *    2: �ĵ��Ļ�������
			 * SortField:
			 *     gid: ������ֶ�����
			 *     type: �����ֶε�����
			 *     boolean: �Ƿ���
			 *     
			 * */ 
			Sort sort=new Sort(new SortField("gid",SortField.INT,true));
			TopDocs topDocs=indexSearcher.search(query,null,currentPage*size,sort);
			System.out.println("�û��ڴ�����:" + currentPage*size);
			System.out.println("������ʵ��ӵ�н����Ϊ:" + topDocs.totalHits);
			// �洢����document��lucenen�е��߼����
			ScoreDoc[] docs=topDocs.scoreDocs;  //[0]=0 [1]=1
			System.out.println("��ʵ�ó������ĵ������" + docs.length);
			for(int i=(currentPage-1)*size;i<docs.length;i++){
				System.out.println("�ĵ��ı��:" + docs[i].doc);
				System.out.println("���ĵ��ĵ÷�:" + docs[i].score);
				Document doc=indexSearcher.doc(docs[i].doc);
				// ��ȡ�����������
				String temp=highlighterUtil.setHightData(query,doc.get("gname"),10);
				String temp2=highlighterUtil.setHightData(query,doc.get("gremark"),40);
				// �Ѹ���֮����������¸�ֵ��document �ֶ�
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
