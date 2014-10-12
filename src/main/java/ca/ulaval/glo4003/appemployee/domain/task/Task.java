package ca.ulaval.glo4003.appemployee.domain.task;

public class Task {
	private Integer uId;
	private String name;
	private String comment;
	
	public Task(Integer uId) {
		this.uId = uId;
	}
	
	public void setUid(Integer uId) {
		this.uId = uId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

}
