package it.prova.pokeronline;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.RuoloService;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;

@SpringBootApplication
public class PokeronlineApplication implements CommandLineRunner {

	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;
	@Autowired
	private TavoloService tavoloService;

	public static void main(String[] args) {
		SpringApplication.run(PokeronlineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", Ruolo.ROLE_ADMIN));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Special Player", Ruolo.ROLE_SPECIAL_PLAYER) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Special Player", Ruolo.ROLE_SPECIAL_PLAYER));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Classic Player", Ruolo.ROLE_PLAYER) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Classic Player", Ruolo.ROLE_PLAYER));
		}

		// a differenza degli altri progetti cerco solo per username perche' se vado
		// anche per password ogni volta ne inserisce uno nuovo, inoltre l'encode della
		// password non lo
		// faccio qui perche gia lo fa il service di utente, durante inserisciNuovo
		if (utenteServiceInstance.findByUsername("admin") == null) {
			Utente admin = new Utente("admin", "admin", "Mario", "Rossi", new Date());
			admin.setEsperienzaAccumulata(3);
			admin.setCreditoAccumulato(3);
			admin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN));
			utenteServiceInstance.inserisciNuovo(admin);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(admin.getId());
		}

		if (utenteServiceInstance.findByUsername("user") == null) {
			Utente SpecialPlayer = new Utente("user", "user", "Antonio", "Verdi", new Date(), 1, 2);
			SpecialPlayer.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Special Player", Ruolo.ROLE_SPECIAL_PLAYER));
			utenteServiceInstance.inserisciNuovo(SpecialPlayer);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(SpecialPlayer.getId());
		}

		if (utenteServiceInstance.findByUsername("user1") == null) {
			Utente ClassicPlayer = new Utente("user1", "user1", "Antonioo", "Verdii", new Date());
			ClassicPlayer.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic Player", Ruolo.ROLE_PLAYER));
			utenteServiceInstance.inserisciNuovo(ClassicPlayer);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(ClassicPlayer.getId());
		}

		if (utenteServiceInstance.findByUsername("user2") == null) {
			Utente ClassicPlayer2 = new Utente("user2", "user2", "Saverio", "Carelli", new Date());
			ClassicPlayer2.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic Player", Ruolo.ROLE_PLAYER));
			utenteServiceInstance.inserisciNuovo(ClassicPlayer2);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(ClassicPlayer2.getId());
		}

		// TAVOLO1##########################################
		Set<Utente> utentiAlTavolo = new HashSet<Utente>();
		utentiAlTavolo.add(utenteServiceInstance.findByUsername("user"));
		utentiAlTavolo.add(utenteServiceInstance.findByUsername("user1"));

		Utente utenteCreazione1 = utenteServiceInstance.findByUsername("admin");
		String denominazione1 = "H501";
		Tavolo tavolo1 = tavoloService.findByDenominazione(denominazione1);

		if (tavolo1 == null) {
			tavolo1 = new Tavolo(1L, 0, 0, denominazione1, new SimpleDateFormat("dd/MM/yyyy").parse("18/12/2010"),
					utentiAlTavolo, utenteCreazione1);
			tavoloService.inserisciNuovo(tavolo1);
		}

		// TAVOLO2##########################################
		Set<Utente> utentiAlTavolo2 = new HashSet<Utente>();
//		utentiAlTavolo2.add(utenteServiceInstance.findByUsername("user1"));
		utentiAlTavolo2.add(utenteServiceInstance.findByUsername("user2"));

		Utente utenteCreazione2 = utenteServiceInstance.findByUsername("user");
		String denominazione2 = "KK900";
		Tavolo tavolo2 = tavoloService.findByDenominazione(denominazione2);

		if (tavolo2 == null) {
			tavolo2 = new Tavolo(2L, 0, 0, denominazione2, new SimpleDateFormat("dd/MM/yyyy").parse("20/05/2021"),
					utentiAlTavolo2, utenteCreazione2);
			tavoloService.inserisciNuovo(tavolo2);
		}
		
		// TAVOLO3##########################################
		String denominazione3 = "FFFFFF";
		Tavolo tavolo3 = tavoloService.findByDenominazione(denominazione3);

		if (tavolo3 == null) {
			tavolo3 = new Tavolo(3L, 0, 0, denominazione3, new SimpleDateFormat("dd/MM/yyyy").parse("18/01/2022"), utenteCreazione2);
			tavoloService.inserisciNuovo(tavolo3);
		}

	}

}
