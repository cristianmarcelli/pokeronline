package it.prova.pokeronline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;

@Service
public class TavoloServiceImpl implements TavoloService {

	@Autowired
	private TavoloRepository repository;

	@Override
	public List<Tavolo> listAllElements() {
		return (List<Tavolo>) repository.findAll();
	}

	@Override
	public List<Tavolo> listAllElementsEager() {
		return (List<Tavolo>) repository.findAllEager();
	}

	@Override
	public Tavolo caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Tavolo caricaSingoloElementoConUtenti(Long id) {
		return repository.findByIdEager(id);
	}

	@Override
	@Transactional
	public Tavolo aggiorna(Tavolo tavoloInstance) {
		return repository.save(tavoloInstance);
	}

	@Override
	@Transactional
	public Tavolo inserisciNuovo(Tavolo tavoloInstance) {
		return repository.save(tavoloInstance);
	}

	@Override
	@Transactional
	public void rimuoviPerId(Long idTavolo) {
		repository.deleteById(idTavolo);
	}

	@Override
	public List<Tavolo> findByExample(Tavolo example) {
		return repository.findByExample(example);
	}

	@Override
	public Tavolo findByDenominazione(String denominazione) {
		return repository.findByDenominazione(denominazione);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tavolo> cercaPerUtenteCreazione(Long id) {
		return repository.findAllByUtenteCreazione_Id(id);
	}

	@Override
	public List<Tavolo> caricaTavoliConUtentePresente(Utente utenteInstance) {
		return repository.findTavoliConUtentePresente(utenteInstance);
	}

}
