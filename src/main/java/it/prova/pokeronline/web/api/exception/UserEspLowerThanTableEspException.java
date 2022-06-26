package it.prova.pokeronline.web.api.exception;

public class UserEspLowerThanTableEspException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserEspLowerThanTableEspException(String message) {
		super(message);
	}

}
