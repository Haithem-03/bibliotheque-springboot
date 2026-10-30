package com.biblio.projet.repository;

import com.biblio.projet.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivreRepository extends JpaRepository<Livre, Long> {

    // Requête personnalisée n°1 : livres d'une catégorie ayant au moins un exemplaire disponible
    @Query("""
            SELECT DISTINCT l FROM Livre l
            JOIN l.exemplaires e
            WHERE l.categorie.id = :categorieId AND e.disponible = true
            """)
    List<Livre> findDisponiblesParCategorie(@Param("categorieId") Long categorieId);
}
