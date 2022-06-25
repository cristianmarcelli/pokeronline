package it.prova.pokeronline.repository.tavolo;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public interface TavoloRepository extends CrudRepository<Tavolo, Long>, CustomTavoloRepository {
	
	@Query("select distinct t from Tavolo t left join fetch t.giocatori ")
	List<Tavolo> findAllEager();

	@Query("from Tavolo t left join fetch t.giocatori where t.id=?1")
	Tavolo findByIdEager(Long idTavolo);
	
	Tavolo findByDenominazione(String denominazione);
	
	@Query("select distinct t from Tavolo t where t.utenteCreazione = ?1")
	List<Tavolo> findAllByUtenteCreazione(Long idUtenteCreazione);

	@EntityGraph(attributePaths = { "giocatori", "utentecreazione" })
	List<Tavolo> findAllByUtenteCreazione_Id(Long id);

	@Query("from Tavolo t join fetch t.giocatori g where g=?1 ")
	List<Tavolo> findTavoliConUtentePresente(Utente utenteInstance);
	
	@Query("from Tavolo t where t.esperienzaMin <= ?1")
	List<Tavolo> findTavoliEsperienzaMinMaggioreUgualeEsperienzaAccumulata(Integer esperienzaAccumulata);	
	

}