package it.prova.pokeronline.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.web.api.exception.CreditoMinoreDiUnoException;
import it.prova.pokeronline.web.api.exception.TavoloNotFoundException;
import it.prova.pokeronline.web.api.exception.UserCreditLowerThanTableMinCreditException;
import it.prova.pokeronline.web.api.exception.UserEspLowerThanTableEspException;

@RestController
@RequestMapping("/api/playmanagement")
public class PlayManagementController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private TavoloService tavoloService;

	// compra credito
	@GetMapping("/compraCredito/{credito}")
	public Integer compraCredito(@PathVariable(value = "credito", required = true) Integer credito) {

		if (credito < 1) {
			throw new CreditoMinoreDiUnoException("Credito minore di uno");
		}

		Utente giocatore = utenteService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		giocatore.setCreditoAccumulato(giocatore.getCreditoAccumulato() + credito);
		if (giocatore.getId() < 1 || giocatore.getId() == null || giocatore == null) {
			throw new RuntimeException();
		}

		utenteService.aggiorna(giocatore);
		return giocatore.getCreditoAccumulato();
	}

	// dammi il last game
	@GetMapping("/dammiIlLastGame")
	public List<TavoloDTO> dammiIlLastGame() {

		return TavoloDTO.createTavoloDTOListFromModelList(
				tavoloService.caricaTavoliConUtentePresente(
						utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())),
				true);
	}

	// ricerca partita con esperienza minima accumulata
	@GetMapping("/ricerca")
	public List<TavoloDTO> ricerca() {

		return TavoloDTO.createTavoloDTOListFromModelList(
				tavoloService.findTavoliEsperienzaMinMaggioreUgualeEsperienzaAccumulata(
						utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())),
				true);

	}

	// abbandona partita
	@GetMapping("/abbandona/{idTavolo}")
	public void abbandonaPartita(@PathVariable(value = "idTavolo", required = true) Long idTavolo) {

		Utente user = utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		Tavolo tavoloItem = tavoloService.caricaSingoloElemento(idTavolo);

		if (tavoloItem == null) {
			throw new TavoloNotFoundException("Tavolo non trovato");
		}

		utenteService.abbandonaPartita(tavoloItem.getId(), user);

	}

	// gioca partita
	@GetMapping("/giocapartita/{idTavolo}")
	public void giocaPartita(@PathVariable(value = "idTavolo", required = true) Long idTavolo) {

		Utente giocatore = utenteService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		Tavolo tavoloACuiGiocare = tavoloService.caricaSingoloElemento(idTavolo);
		
		if (tavoloACuiGiocare == null) {
			throw new TavoloNotFoundException("Tavolo non trovato");
		}
		
		if (giocatore.getEsperienzaAccumulata() < tavoloACuiGiocare.getEsperienzaMin()) {
			throw new UserEspLowerThanTableEspException("Impossibile unirsi alla partita: esperienza non sufficiente.");
		}
				
		if (giocatore.getCreditoAccumulato() < tavoloACuiGiocare.getCifraMinima()) {
			throw new UserCreditLowerThanTableMinCreditException("Impossibile unirsi alla partita: credito non sufficiente.");
		}
		
		utenteService.giocaPartita(idTavolo, giocatore);

	}

}
