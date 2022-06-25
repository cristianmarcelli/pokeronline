package it.prova.pokeronline.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.UtenteService;

@RestController
@RequestMapping("/api/playmanagement")
public class PlayManagementController {

	@Autowired
	private UtenteService utenteService;

	@GetMapping("/compraCredito/{credito}")
	public Integer compraCredito(@PathVariable(value = "credito", required = true) Integer credito) {

		if (credito < 1) {
			throw new RuntimeException("Il credito da aggiungere non deve essere minore di 1");
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

}
