package ca.ulaval.glo4003.appemployee.web.viewmodels;

public class MessageViewModel {
	private String name;
	private String message;
	
	public MessageViewModel(String name, String message) {
		this.setName(name);
		this.setMessage(message);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
