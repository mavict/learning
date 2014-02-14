package cn.itcast.lucene;

import java.io.IOException;
import java.util.Set;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.search.IndexSearcher;

/*
 * ��indexWriter:����ȫ��Ψһ��,��Ŀֻ����ʹ��, ��LuceneUtil���𴴽�������
 * 
 * */

public class LuceneUtil {

	private IndexWriter indexWriter = null;
	private IndexSearcher indexSearcher=null;
	
	private Configuraction configuraction=null;
	
	public void setConfiguraction(Configuraction configuraction) {
		this.configuraction = configuraction;
	}
	
	public IndexSearcher getIndexSearcher() {
		// �ж��Ƿ���IndexSearcher���û���򴴽�һ��
		if(indexSearcher==null){
			synchronized(LuceneUtil.class){
				if(indexSearcher==null){
				try {
					indexSearcher=new IndexSearcher(configuraction.getDirectory());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					new RuntimeException(e);
				}
				}
			}
		}
		return indexSearcher;
	}

	public IndexWriter getIndexWriter() {
		// ��ȡIndexWriter��ʱ��˵��Ҫ����������,��ʱ�ر�ǰ���indexSearch
		// indexSearcher
		closeIndexSearch(indexSearcher);
		// ��ȫ�ֱ�������Ϊnull
		indexSearcher=null;
		return indexWriter;
	}

	private void closeIndexWriter(IndexWriter indexWriter){
		if(indexWriter!=null){
			try {
				indexWriter.optimize();
				indexWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	private void closeIndexSearch(IndexSearcher indexSearcher){
		if(indexSearcher!=null){
			try {
				indexSearcher.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	public void init(){
		System.out.println("---luceneUtil init");
		try {
			indexWriter = new IndexWriter(configuraction.getDirectory(),
					configuraction.getAnalyzer(), MaxFieldLength.LIMITED);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void destory(){
		System.out.println("---luceneUtil destory-----");
		closeIndexWriter(indexWriter);
	}
}
