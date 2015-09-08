package gcm.infra;

public class GcmPersistenceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GcmPersistenceException(String mensagem) {
		super(mensagem);
	}
	
	public GcmPersistenceException(Exception e) {
		super(e);
	}
}
