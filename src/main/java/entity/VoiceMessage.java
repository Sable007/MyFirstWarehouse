package entity;

import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("xml")
public class VoiceMessage extends FatherMessage {
	
	private Voice voice;

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

	public VoiceMessage(Map<String, String> requestMap, Voice voice) {
		super(requestMap);
		this.setMsgType("voice");
		this.voice = voice;
	}
	
	
	


}
