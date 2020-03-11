package entity;

import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class FatherMessage {
	//������΢�ź�
	@XStreamAlias("ToUserName")
	private String toUserName;
	//���ͷ��ʺţ�һ��OpenID��
	@XStreamAlias("FromUserName")
	private String fromUserName;
	//��Ϣ����ʱ�� �����ͣ�
	@XStreamAlias("CreateTime")
	private String createTime;
	//��Ϣ����
	@XStreamAlias("MsgType")
	private String msgType;
	
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	public FatherMessage(Map<String, String> requestMap){
		this.toUserName=requestMap.get("FromUserName");
		this.fromUserName=requestMap.get("ToUserName");
		this.createTime=System.currentTimeMillis()/1000+"";
		
	}

}
