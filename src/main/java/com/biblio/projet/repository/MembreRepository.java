package com.biblio.projet.repository;

import com.biblio.projet.model.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MembreRepository extends JpaRepository<Membre, Long> {

    // Requête personnalisée n°4 : membres ayant plus de N emprunts en cours
    @Query("""
            SELECT m FROM Membre m
            WHERE (SELECT COUNT(e) FROM Emprunt e
                   WHERE e.membre = m AND e.dateRetourEffective IS NULL) > :seuil
            """)
    List<Membre> findMembresAvecEmpruntsActifsSuperieurA(@Param("seuil") long seuil);
}
