package it.prova.pokeronline.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.model.StatoUtente;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;
import it.prova.pokeronline.repository.utente.UtenteRepository;

@Service
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteRepository repository;

	@Autowired
	private TavoloRepository tavoloRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public List<Utente> listAllUtenti() {
		return (List<Utente>) repository.findAll();
	}

	@Transactional(readOnly = true)
	public Utente caricaSingoloUtente(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public Utente caricaSingoloUtenteConRuoli(Long id) {
		return repository.findByIdConRuoli(id).orElse(null);
	}

	@Transactional
	public Utente aggiorna(Utente utenteInstance) {
		// deve aggiornare solo nome, cognome, username, ruoli
		Utente utenteReloaded = repository.findById(utenteInstance.getId()).orElse(null);
		if (utenteReloaded == null)
			throw new RuntimeException("Elemento non trovato");
		utenteReloaded.setNome(utenteInstance.getNome());
		utenteReloaded.setCognome(utenteInstance.getCognome());
		utenteReloaded.setUsername(utenteInstance.getUsername());
		utenteReloaded.setEsperienzaAccumulata(utenteInstance.getEsperienzaAccumulata());
		utenteReloaded.setCreditoAccumulato(utenteInstance.getCreditoAccumulato());
		utenteReloaded.setRuoli(utenteInstance.getRuoli());
		return repository.save(utenteReloaded);
	}

	@Transactional
	public Utente inserisciNuovo(Utente utenteInstance) {
		utenteInstance.setStato(StatoUtente.CREATO);
		utenteInstance.setPassword(passwordEncoder.encode(utenteInstance.getPassword()));
		utenteInstance.setDataRegistrazione(new Date());
		return repository.save(utenteInstance);
	}

	@Transactional
	public void rimuovi(Utente utenteInstance) {
		utenteInstance.setStato(StatoUtente.DISABILITATO);
	}

	@Transactional(readOnly = true)
	public List<Utente> findByExample(Utente example) {
		return repository.findByExample(example);
	}

	@Transactional(readOnly = true)
	public Utente eseguiAccesso(String username, String password) {
		return repository.findByUsernameAndPasswordAndStato(username, password, StatoUtente.ATTIVO);
	}

	@Override
	public Utente findByUsernameAndPassword(String username, String password) {
		return repository.findByUsernameAndPassword(username, password);
	}

	@Transactional
	public void changeUserAbilitation(Long utenteInstanceId) {
		Utente utenteInstance = caricaSingoloUtente(utenteInstanceId);
		if (utenteInstance == null)
			throw new RuntimeException("Elemento non trovato.");

		if (utenteInstance.getStato() == null || utenteInstance.getStato().equals(StatoUtente.CREATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
		else if (utenteInstance.getStato().equals(StatoUtente.ATTIVO))
			utenteInstance.setStato(StatoUtente.DISABILITATO);
		else if (utenteInstance.getStato().equals(StatoUtente.DISABILITATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
	}

	@Transactional
	public Utente findByUsername(String username) {
		return repository.findByUsername(username).orElse(null);
	}

	@Override
	public Utente aggiornaCredito(String username, Integer credito) {

		Utente utenteInstance = repository.findByUsername(username).orElse(null);

		if (utenteInstance == null) {
			return utenteInstance;
		}

		utenteInstance.setCreditoAccumulato(credito + utenteInstance.getCreditoAccumulato());

		return utenteInstance;
	}

	@Override
	public void abbandonaPartita(Long idTavolo, Utente user) {

		Tavolo tavoloItem = tavoloRepository.findById(idTavolo).orElse(null);

		tavoloItem.getGiocatori().remove(user);

		Integer esperienzaGiocatore = user.getEsperienzaAccumulata();

		user.setEsperienzaAccumulata(esperienzaGiocatore++);

	}
	
	@Override
	public void giocaPartita(Long idTavolo, Utente giocatore) {

		Tavolo tavoloACuiGiocare = tavoloRepository.findById(idTavolo).orElse(null);
		int creditoGiocatore = giocatore.getCreditoAccumulato();

		double segno = Math.random();
		boolean risultato = false;

		if (segno > 0.5) {
			risultato = true;
		}

		int somma = (int) (Math.random() * 1000);
		double totale = segno * somma;

		if (risultato == true) {
			creditoGiocatore += totale;
		} else {
			creditoGiocatore -= totale;
		}

		giocatore.setCreditoAccumulato(creditoGiocatore);

		repository.save(giocatore);

	}

}
