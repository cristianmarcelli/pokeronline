package it.prova.pokeronline.web.api.exception;

public class UserCreditLowerThanTableMinCreditException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserCreditLowerThanTableMinCreditException(String message) {
		super(message);
	}

}
