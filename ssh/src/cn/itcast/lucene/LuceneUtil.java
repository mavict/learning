package cn.itcast.lucene;

import java.io.IOException;
import java.util.Set;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.search.IndexSearcher;

/*
 * 把indexWriter:设置全局唯一的,项目只负责使用, 由LuceneUtil负责创建和销毁
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
		// 判断是否有IndexSearcher如果没有则创建一个
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
		// 获取IndexWriter的时候说明要操作索引库,此时关闭前面的indexSearch
		// indexSearcher
		closeIndexSearch(indexSearcher);
		// 把全局变量设置为null
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
