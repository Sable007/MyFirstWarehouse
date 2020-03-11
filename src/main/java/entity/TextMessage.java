package entity;

import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("xml")
public class TextMessage extends FatherMessage {
	//�ı���Ϣ����
	@XStreamAlias("Content")
	private String content;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public TextMessage(Map<String, String> requestMap,String content){
		super(requestMap);
		//������ϢmsgType����Ϊtext����
		this.content=content;
		this.setMsgType("text");
	}

	@Override
	public String toString() {
		return "TextMessage [content=" + content 
				+ ", getToUserName()=" + getToUserName() + ", getFromUserName()=" + getFromUserName()
				+ ", getCreateTime()=" + getCreateTime() + ", getMsgType()=" + getMsgType() 
				+  "]";
	}
	
}
