package it.prova.pokeronline.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.pokeronline.model.Tavolo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TavoloDTO {

	private Long id;

	@NotNull(message = "{esperienzaMin.notnull}")
	private Integer esperienzaMin;

	@NotNull(message = "{cifraMinima.notnull}")
	private Integer cifraMinima;

	@NotBlank(message = "{denominazione.notblank}")
	private String denominazione;

	private Date dateCreated;

	@JsonIgnoreProperties(value = { "tavolo" })
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

	public Tavolo buildTavoloModel() {
		return new Tavolo(this.id, this.esperienzaMin, this.cifraMinima, this.denominazione, this.dateCreated,
				this.utenteCreazione.buildUtenteModel(false));
	}

}