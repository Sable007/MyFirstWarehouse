package button;

import java.util.ArrayList;
import java.util.List;

public class ScancodePushButtton extends AbstractButon{
	private String type="scancode_push";
	private String key;
	private List<AbstractButon> sub_button=new ArrayList<>();
	public ScancodePushButtton(String name, String key) {
		super(name);
		this.key = key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<AbstractButon> getSub_button() {
		return sub_button;
	}
	public void setSub_button(List<AbstractButon> sub_button) {
		this.sub_button = sub_button;
	}
	
	
}
