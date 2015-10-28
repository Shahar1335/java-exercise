package ExecutionAids;

public class InvalidEncryptionKeyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg; //This is the exception's message.
	private String lvl; //This is the exception's level.

	public InvalidEncryptionKeyException(String msg, String lvl) {
		this.msg = msg;
		this.lvl = lvl;
	}
	
	public String getMessage() {
		return this.msg;
	}
	
	public String getLevel() {
		return this.lvl;
	}
	
}
