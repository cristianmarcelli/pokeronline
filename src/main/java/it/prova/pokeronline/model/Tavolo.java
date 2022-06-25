package it.prova.pokeronline.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tavolo")
public class Tavolo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "esperienzamin")
	private Integer esperienzaMin;

	@Column(name = "ciframinima")
	private Integer cifraMinima;

	@Column(name = "denominazione")
	private String denominazione;

	@Column(name = "datecreated")
	private Date dateCreated;

	@OneToMany
	private Set<Utente> giocatori = new HashSet<Utente>(0);

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utentecreazione_id", referencedColumnName = "id", nullable = false)
	private Utente utenteCreazione;

	public Tavolo() {
	}

	public Tavolo(Integer esperienzaMin, String denominazione) {
		super();
		this.esperienzaMin = esperienzaMin;
		this.denominazione = denominazione;
	}

	public Tavolo(Long id, Integer esperienzaMin, Integer cifraMinima, String denominazione, Date dateCreated,
			Set<Utente> giocatori, Utente utenteCreazione) {
		super();
		this.id = id;
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
		this.giocatori = giocatori;
		this.utenteCreazione = utenteCreazione;
	}

	public Tavolo(Integer esperienzaMin, Integer cifraMinima, String denominazione, Date dateCreated,
			Set<Utente> giocatori, Utente utenteCreazione) {
		super();
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
		this.giocatori = giocatori;
		this.utenteCreazione = utenteCreazione;
	}

	public Tavolo(Integer esperienzaMin, Integer cifraMinima, String denominazione, Date dateCreated,
			Utente utenteCreazione) {
		super();
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
		this.utenteCreazione = utenteCreazione;
	}

	public Tavolo(Long id, Integer esperienzaMin, Integer cifraMinima, String denominazione, Date dateCreated) {
		super();
		this.id = id;
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
	}

	public Tavolo(Integer esperienzaMin, Integer cifraMinima, String denominazione, Date dateCreated) {
		super();
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
	}

	public Tavolo(Long id, Integer esperienzaMin, Integer cifraMinima, String denominazione, Date dateCreated,
			Utente utenteCreazione) {
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

	public Set<Utente> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(Set<Utente> giocatori) {
		this.giocatori = giocatori;
	}

	public Utente getUtenteCreazione() {
		return utenteCreazione;
	}

	public void setUtenteCreazione(Utente utenteCreazione) {
		this.utenteCreazione = utenteCreazione;
	}

}
