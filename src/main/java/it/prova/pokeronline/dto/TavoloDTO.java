package it.prova.pokeronline.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.prova.pokeronline.model.Tavolo;

public class TavoloDTO {

	private Long id;

	@NotNull(message = "{esperienzaMin.notnull}")
	private Integer esperienzaMin;

	@NotNull(message = "{cifraMinima.notnull}")
	private Integer cifraMinima;

	@NotBlank(message = "{denominazione.notblank}")
	private String denominazione;

	private Date dateCreated;

	private Set<UtenteDTO> giocatori = new HashSet<UtenteDTO>(0);

	@NotNull(message = "{utenteCreazione.notnull}")
	private UtenteDTO utenteCreazione;

	public TavoloDTO() {
	}

	public TavoloDTO(Long id) {
		super();
		this.id = id;
	}

	public TavoloDTO(Long id, @NotNull(message = "{esperienzaMin.notnull}") Integer esperienzaMin,
			@NotNull(message = "{cifraMinima.notnull}") Integer cifraMinima,
			@NotBlank(message = "{denominazione.notblank}") String denominazione, Date dateCreated,
			Set<UtenteDTO> giocatori, @NotNull(message = "{dateCreated.notnull}") UtenteDTO utenteCreazione) {
		super();
		this.id = id;
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
		this.giocatori = giocatori;
		this.utenteCreazione = utenteCreazione;
	}

	public TavoloDTO(Long id, @NotNull(message = "{esperienzaMin.notnull}") Integer esperienzaMin,
			@NotNull(message = "{cifraMinima.notnull}") Integer cifraMinima,
			@NotBlank(message = "{denominazione.notblank}") String denominazione,
			@NotNull(message = "{dateCreated.notnull}") Date dateCreated) {
		super();
		this.id = id;
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
	}

	public TavoloDTO(@NotNull(message = "{esperienzaMin.notnull}") Integer esperienzaMin,
			@NotNull(message = "{cifraMinima.notnull}") Integer cifraMinima,
			@NotBlank(message = "{denominazione.notblank}") String denominazione,
			@NotNull(message = "{dateCreated.notnull}") Date dateCreated) {
		super();
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
	}

	public TavoloDTO(Long id, @NotNull(message = "{esperienzaMin.notnull}") Integer esperienzaMin,
			@NotNull(message = "{cifraMinima.notnull}") Integer cifraMinima,
			@NotBlank(message = "{denominazione.notblank}") String denominazione, Date dateCreated,
			@NotNull(message = "{utenteCreazione.notnull}") UtenteDTO utenteCreazione) {
		super();
		this.id = id;
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
		this.utenteCreazione = utenteCreazione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getEsperienzaMin() {
		return esperienzaMin;
	}

	public void setEsperienzaMin(Integer esperienzaMin) {
		this.esperienzaMin = esperienzaMin;
	}

	public Integer getCifraMinima() {
		return cifraMinima;
	}

	public void setCifraMinima(Integer cifraMinima) {
		this.cifraMinima = cifraMinima;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Set<UtenteDTO> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(Set<UtenteDTO> giocatori) {
		this.giocatori = giocatori;
	}

	public UtenteDTO getUtenteCreazione() {
		return utenteCreazione;
	}

	public void setUtenteCreazione(UtenteDTO utenteCreazione) {
		this.utenteCreazione = utenteCreazione;
	}

	public Tavolo buildTavoloModel() {
		return new Tavolo(this.id, this.esperienzaMin, this.cifraMinima, this.denominazione, this.dateCreated);
	}

	public static TavoloDTO buildTavoloDTOFromModel(Tavolo tavoloModel, boolean includeGiocatori) {

		TavoloDTO result = new TavoloDTO(tavoloModel.getId(), tavoloModel.getEsperienzaMin(),
				tavoloModel.getCifraMinima(), tavoloModel.getDenominazione(), tavoloModel.getDateCreated());

		if (tavoloModel.getUtenteCreazione() != null && tavoloModel.getUtenteCreazione().getId() != null
				&& tavoloModel.getUtenteCreazione().getId() > 0) {
			result.setUtenteCreazione(UtenteDTO.buildUtenteDTOFromModel(tavoloModel.getUtenteCreazione()));
		}

		if (tavoloModel.getGiocatori() != null && !tavoloModel.getGiocatori().isEmpty()) {
			result.setGiocatori(UtenteDTO.buildUtenteDTOSetFromModelSet(tavoloModel.getGiocatori()));
		}

		return result;
	}

	public static List<TavoloDTO> createTavoloDTOListFromModelList(List<Tavolo> modelListInput,
			boolean includeGiocatori) {

		return modelListInput.stream().map(tavoloEntity -> {
			TavoloDTO result = TavoloDTO.buildTavoloDTOFromModel(tavoloEntity, includeGiocatori);

			if (includeGiocatori)
				result.setGiocatori(UtenteDTO.buildUtenteDTOSetFromModelSet(tavoloEntity.getGiocatori()));
			return result;
		}).collect(Collectors.toList());
	}

}
