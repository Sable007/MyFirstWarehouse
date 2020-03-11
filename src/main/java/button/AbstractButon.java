package button;

public abstract class AbstractButon {
	private String name;

	public String getName() {
		return name;
	}

	public AbstractButon(String name) {
		super();
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
