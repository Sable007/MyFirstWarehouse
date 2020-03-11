package entity;

public class JsapiTicket {
	private String ticket;
	private long expiresTime;
	
	public JsapiTicket(String ticket, String expiresIn) {
		super();
		this.ticket = ticket;
		this.expiresTime=System.currentTimeMillis()+Integer.parseInt(expiresIn)*1000;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
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
	}
	
}
