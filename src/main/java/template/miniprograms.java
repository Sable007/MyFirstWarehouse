package template;

public class miniprograms {
	private String appid;
	private String pagepath;
	
	
	public miniprograms(String appid, String pagepath) {
		super();
		this.appid = appid;
		this.pagepath = pagepath;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPagepath() {
		return pagepath;
	}
	public void setPagepath(String pagepath) {
		this.pagepath = pagepath;
	}
}
