package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("xml")
public class NewsMessage extends FatherMessage {
	//ͼ����Ϣ���������û������ı���ͼƬ����Ƶ��ͼ�ġ�����λ����������Ϣʱ��
	//������ֻ�ܻظ�1��ͼ����Ϣ�����ೡ�����ɻظ�8��ͼ����Ϣ
	@XStreamAlias("ArticleCount")
	private String articleCount;
	//ͼ����Ϣ��Ϣ��ע�⣬���ͼ�����������ƣ���ֻ�������ڵ�����
	@XStreamAlias("Articles")
	private List<Article> articles =new ArrayList<>();
	
	public String getArticleCount() {
		return articleCount;
	}
	public void setArticleCount(String articleCount) {
		this.articleCount = articleCount;
	}
	public List<Article> getArticles() {
		return articles;
	}
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	public NewsMessage(Map<String, String> requestMap, List<Article> articles) {
		super(requestMap);
		this.setMsgType("news");
		this.articleCount = articles.size()+"";
		this.articles = articles;
	}
	
}
