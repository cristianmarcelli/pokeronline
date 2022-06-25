package it.prova.pokeronline.web.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.web.api.exception.TavoloNotFoundException;

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
	
	@PostMapping("/search")
	public List<TavoloDTO> search(@RequestBody TavoloDTO example) {
		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.findByExample(example.buildTavoloModel()), false);
	}

}
