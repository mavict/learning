package cn.itcast.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Configuraction {

	private Analyzer analyzer=null;
	private Directory directory=null;
	
	private String indexPath=null;
	
	public Configuraction(String indexPath){
		System.out.println("---configuraction()---");
		this.indexPath=indexPath;
	}
	
	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public Directory getDirectory() {
		return directory;
	}

	public void init(){
		System.out.println("---int()---");
		try {
			// 不同的分词器,分词的效果不同 Version.LUCENE_30: Lucene的版本号
			analyzer=new IKAnalyzer(true);
			// 目录的位置可以任意指定,最好和项目同目录
			directory=FSDirectory.open(new File(indexPath));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
