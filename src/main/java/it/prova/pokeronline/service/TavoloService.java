package it.prova.pokeronline.service;

import java.util.List;

import it.prova.pokeronline.model.Tavolo;

public interface TavoloService {
	
	List<Tavolo> listAllElements(boolean eager);

	Tavolo caricaSingoloElemento(Long id);

	Tavolo caricaSingoloElementoConutenti(Long id);

	Tavolo aggiorna(Tavolo tavoloInstance);

	Tavolo inserisciNuovo(Tavolo tavoloInstance);

	void rimuoviPerId(Long idTavolo);

	List<Tavolo> findByExample(Tavolo example);

	List<Tavolo> findByDenominazione(String denominazione);

}
