package com.biblio.projet.repository;

import com.biblio.projet.dto.LivreStat;
import com.biblio.projet.model.Emprunt;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

    // Requête personnalisée n°2 (méthode dérivée) : emprunts en retard
    List<Emprunt> findByDateRetourEffectiveIsNullAndDateRetourPrevueBefore(LocalDate date);

    // Utilisé par la règle métier "maximum d'emprunts actifs par membre"
    long countByMembreIdAndDateRetourEffectiveIsNull(Long membreId);

    // Requête personnalisée n°3 : livres les plus empruntés (agrégation)
    @Query("""
            SELECT new com.biblio.projet.dto.LivreStat(l.id, l.titre, COUNT(e))
            FROM Emprunt e
            JOIN e.exemplaire ex
            JOIN ex.livre l
            GROUP BY l.id, l.titre
            ORDER BY COUNT(e) DESC
            """)
    List<LivreStat> findLivresLesPlusEmpruntes(Pageable pageable);
}
