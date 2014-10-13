package ca.ulaval.glo4003.appemployee.web.viewmodels;

public class TaskViewModel {
	private Integer uId;
	private String name;
	private String comment;

	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
