package it.prova.pokeronline.web.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.web.api.exception.IdNotNullForInsertException;
import it.prova.pokeronline.web.api.exception.TavoloConGiocatoriAssegnatiException;
import it.prova.pokeronline.web.api.exception.TavoloNotFoundException;
import it.prova.pokeronline.web.api.exception.UtenteCreazioneNotFoundException;

@RestController
@RequestMapping("/api/gestionetavolo")
public class GestioneTavoloController {

	@Autowired
	private TavoloService tavoloService;

	@Autowired
	private UtenteService utenteService;

	@GetMapping
	public List<TavoloDTO> getAll() {

		Utente user = utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		if (user.getRuoli().stream().map(ruolo -> ruolo.getCodice()).collect(Collectors.toList())
				.contains(Ruolo.ROLE_SPECIAL_PLAYER)) {
			return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.cercaPerUtenteCreazione(user.getId()),
					true);
		}

		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.listAllElementsEager(), true);
	}

	@GetMapping("/{id}")
	public TavoloDTO findById(@PathVariable(value = "id", required = true) long id) {

		Tavolo tavoloInstance = tavoloService.caricaSingoloElementoConUtenti(id);

		if (tavoloInstance == null)
			throw new TavoloNotFoundException("Tavolo not found con id: " + id);

		return TavoloDTO.buildTavoloDTOFromModel(tavoloInstance, true);
	}

	// findByExample
	@PostMapping("/search")
	public List<TavoloDTO> search(@RequestBody TavoloDTO example) {

		Utente user = utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		if (user.getRuoli().stream().map(ruolo -> ruolo.getCodice()).collect(Collectors.toList())
				.contains(Ruolo.ROLE_SPECIAL_PLAYER)) {
			return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.cercaPerUtenteCreazione(user.getId()),
					true);
		}

		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.findByExample(example.buildTavoloModel()),
				false);
	}

	// insert
//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public TavoloDTO createNew(@Valid @RequestBody TavoloDTO tavoloInput) {
//
//		if (tavoloInput.getId() != null)
//			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");
//
//		tavoloInput.setUtenteCreazione(UtenteDTO
//				.buildUtenteDTOFromModel(utenteService.caricaSingoloUtente(tavoloInput.getUtenteCreazione().getId())));
//
//		Tavolo tavoloInserito = tavoloService.inserisciNuovo(tavoloInput.buildTavoloModel());
//		return TavoloDTO.buildTavoloDTOFromModel(tavoloInserito, true);
//	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TavoloDTO createNew(@Valid @RequestBody TavoloDTO tavoloInput) {

		if (tavoloInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");

		Tavolo tavoloDaInserire = tavoloInput.buildTavoloModel();
		tavoloDaInserire.setUtenteCreazione(
				utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
		Tavolo tavoloInsert = tavoloService.inserisciNuovo(tavoloDaInserire);
		return TavoloDTO.buildTavoloDTOFromModel(tavoloInsert, true);

	}

	// delete
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable(required = true) Long id) {
		Tavolo tavoloInstance = tavoloService.caricaSingoloElementoConUtenti(id);

		if (tavoloInstance == null)
			throw new TavoloNotFoundException("Tavolo not found con id: " + id);

		if (tavoloInstance.getGiocatori() == null || !tavoloInstance.getGiocatori().isEmpty()) {
			throw new TavoloConGiocatoriAssegnatiException("Impossibile eliminare il tavolo: ha giocatori assegnati");
		}

		tavoloService.rimuoviPerId(tavoloInstance.getId());
	}

	// update
	@PutMapping("/{id}")
	public TavoloDTO update(@Valid @RequestBody TavoloDTO tavoloInput, @PathVariable(required = true) Long id) {

		Tavolo tavolo = tavoloService.caricaSingoloElementoConUtenti(id);

		if (tavolo == null)
			throw new TavoloNotFoundException("Tavolo not found con id: " + id);

		if (tavolo.getGiocatori() == null || !tavolo.getGiocatori().isEmpty()) {
			throw new TavoloConGiocatoriAssegnatiException("Impossibile modificare il Tavolo: ha giocatori assegnati.");
		}

		if (tavoloInput.getUtenteCreazione() == null || tavoloInput.getUtenteCreazione().getId() == null
				|| tavoloInput.getUtenteCreazione().getId() < 1) {
			throw new UtenteCreazioneNotFoundException("Utente creazione non trovato.");
		}

		tavoloInput.setUtenteCreazione(UtenteDTO
				.buildUtenteDTOFromModel(utenteService.caricaSingoloUtente(tavoloInput.getUtenteCreazione().getId())));

		tavoloInput.setId(id);
		tavoloInput.setDateCreated(tavolo.getDateCreated());
		
		Tavolo tavoloAggiornato = tavoloService.aggiorna(tavoloInput.buildTavoloModel());
		
		return TavoloDTO.buildTavoloDTOFromModel(tavoloAggiornato, true);
	}

}
