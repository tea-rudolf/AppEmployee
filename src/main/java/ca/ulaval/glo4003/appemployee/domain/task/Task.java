package ca.ulaval.glo4003.appemployee.domain.task;

public class Task {
	private String uId;
	private String name;
	private String comment;
	
	public Task() {
		
	}

	public Task(String uId) {
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

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

}
