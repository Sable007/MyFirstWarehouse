package entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("item")
public class Article {
	//ͼ����Ϣ����
	@XStreamAlias("Title")
	private String title;
	//ͼ����Ϣ����
	@XStreamAlias("Description")
	private String description;
	//ͼƬ���ӣ�֧��JPG��PNG��ʽ���Ϻõ�Ч��Ϊ��ͼ360*200��Сͼ200*200
	@XStreamAlias("PicUrl")
	private String picUrl;
	//���ͼ����Ϣ��ת����
	@XStreamAlias("Url")
	private String url;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Article(String title, String description, String picUrl, String url) {
		super();
		this.title = title;
		this.description = description;
		this.picUrl = picUrl;
		this.url = url;
	}
	
}
