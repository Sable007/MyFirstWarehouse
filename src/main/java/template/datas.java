package template;

public class datas {
	private vandc first;
	private vandc keyword1;
	private vandc keyword2;
	private vandc remark;
	public vandc getFirst() {
		return first;
	}
	public void setFirst(vandc first) {
		this.first = first;
	}
	public vandc getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(vandc keyword1) {
		this.keyword1 = keyword1;
	}
	public vandc getKeyword2() {
		return keyword2;
	}
	public void setKeyword2(vandc keyword2) {
		this.keyword2 = keyword2;
	}
	public vandc getRemark() {
		return remark;
	}
	public void setRemark(vandc remark) {
		this.remark = remark;
	}
	public datas(vandc first, vandc keyword1, vandc keyword2, vandc remark) {
		super();
		this.first = first;
		this.keyword1 = keyword1;
		this.keyword2 = keyword2;
		this.remark = remark;
	}
	
	
}
