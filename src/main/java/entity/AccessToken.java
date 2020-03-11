package entity;

public class AccessToken {
	private String accessToken;
	private long expiresTime;
	
	public AccessToken(String accessToken, String expiresIn) {
		super();
		this.accessToken = accessToken;
		this.expiresTime=System.currentTimeMillis()+Integer.parseInt(expiresIn)*1000;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public long getExpiresTime() {
		return expiresTime;
	}
	public void setExpiresTime(long expiresTime) {
		this.expiresTime = expiresTime;
	}
	//ÅÐ¶ÏTokenÊÇ·ñ¹ýÆÚ
	public boolean isExpired(){
		return System.currentTimeMillis()>expiresTime;
	//return true;
	}
	
}
