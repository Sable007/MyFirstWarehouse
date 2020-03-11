package template;

import java.util.ArrayList;
import java.util.List;

public class TemplateMessage {
	private String touser;
	private String template_id;

	private String url;
	private miniprograms miniprogram;
	
	private datas data;
	
	
	
	public String getTouser() {
		return touser;
	}



	public void setTouser(String touser) {
		this.touser = touser;
	}



	public String getTemplate_id() {
		return template_id;
	}



	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public miniprograms getMiniprogram() {
		return miniprogram;
	}



	public void setMiniprogram(miniprograms miniprogram) {
		this.miniprogram = miniprogram;
	}



	public datas getData() {
		return data;
	}



	public void setData(datas data) {
		this.data = data;
	}



	public TemplateMessage(String touser, String template_id, String url, miniprograms miniprogram,
			datas data) {
		super();
		this.touser = touser;
		this.template_id = template_id;
		this.url = url;
		this.miniprogram = miniprogram;
		this.data = data;
	}
}
