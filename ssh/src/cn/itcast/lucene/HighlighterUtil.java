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

	// ����һ�������ķ���: �˷Ž���4������ query, Ҫ�������ֶ�, ����������, ���ݵĴ�С,���ظ�����ֵ
	public String setHightData(Query query, String value, int size) {
		String result = null;
		scorer = new QueryScorer(query);
		// ����������
		Highlighter highlighter = new Highlighter(formatter, scorer);
		// ���������ַ���
		highlighter.setTextFragmenter(new SimpleFragmenter(size));
		try {
			result = highlighter.getBestFragment(configuraction.getAnalyzer(),null,value);
			// ���������û�и������ַ���,�򷵻�null
			if (result == null) {
				// ��ԭ�������ֽ�ȡ�����ֶκ�ֵ��result
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
