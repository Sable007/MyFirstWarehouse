package entity;

import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("xml")
public class ImageMessage extends FatherMessage {
	@XStreamAlias("Image")
	private Image image;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public ImageMessage(Map<String, String> requestMap, Image image) {
		super(requestMap);
		this.setMsgType("image");
		this.image = image;
	}

	
}
