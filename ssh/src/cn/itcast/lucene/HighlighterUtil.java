package cn.itcast.lucene;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

public class HighlighterUtil {

	private static Formatter formatter = null;

	private static Scorer scorer = null;
	
	private Configuraction configuraction=null;
	
	private String startTag=null;
	
	private String endTag=null;
	
	public void setEndTag(String endTag) {
		this.endTag = endTag;
	}
	
	public void setStartTag(String startTag) {
		this.startTag = startTag;
	}

	public void setConfiguraction(Configuraction configuraction) {
		this.configuraction = configuraction;
	}

	public void init(){
		formatter=new SimpleHTMLFormatter(startTag,endTag);
	}

	// 设置一个高亮的方法: 此放接受4个参数 query, 要高亮的字段, 高亮的数据, 数据的大小,返回高亮后值
	public String setHightData(Query query, String value, int size) {
		String result = null;
		scorer = new QueryScorer(query);
		// 高亮工具类
		Highlighter highlighter = new Highlighter(formatter, scorer);
		// 高亮保留字符数
		highlighter.setTextFragmenter(new SimpleFragmenter(size));
		try {
			result = highlighter.getBestFragment(configuraction.getAnalyzer(),null,value);
			// 如果数据中没有高亮的字符串,则返回null
			if (result == null) {
				// 把原来的文字截取部分字段后赋值给result
				if (value.length() > size)
					result = value.substring(0, size);
				else
					result = value;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
