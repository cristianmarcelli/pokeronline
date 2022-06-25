package it.prova.pokeronline.service;

import java.util.List;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public interface TavoloService {
	
	List<Tavolo> listAllElements();
	
	public List<Tavolo> listAllElementsEager();

	Tavolo caricaSingoloElemento(Long id);

	Tavolo caricaSingoloElementoConUtenti(Long id);

	Tavolo aggiorna(Tavolo tavoloInstance);

	Tavolo inserisciNuovo(Tavolo tavoloInstance);

	void rimuoviPerId(Long idTavolo);

	List<Tavolo> findByExample(Tavolo example);

	Tavolo findByDenominazione(String denominazione);

	List<Tavolo> cercaPerUtenteCreazione(Long id);

	List<Tavolo> caricaTavoliConUtentePresente(Utente utenteInstance);
	
	List<Tavolo> findTavoliEsperienzaMinMaggioreUgualeEsperienzaAccumulata(Utente utenteInstance);

}
