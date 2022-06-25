package it.prova.pokeronline.web.api.exception;

public class UtenteCreazioneNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UtenteCreazioneNotFoundException(String message) {
		super(message);
	}

}
