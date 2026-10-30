package com.biblio.projet.service;

import com.biblio.projet.exception.ResourceNotFoundException;
import com.biblio.projet.model.Auteur;
import com.biblio.projet.model.Livre;
import com.biblio.projet.repository.AuteurRepository;
import com.biblio.projet.repository.CategorieRepository;
import com.biblio.projet.repository.LivreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LivreService extends CrudService<Livre> {

    private final LivreRepository livreRepository;
    private final CategorieRepository categorieRepository;
    private final AuteurRepository auteurRepository;

    public LivreService(LivreRepository livreRepository,
                        CategorieRepository categorieRepository,
                        AuteurRepository auteurRepository) {
        super(livreRepository, "Livre");
        this.livreRepository = livreRepository;
        this.categorieRepository = categorieRepository;
        this.auteurRepository = auteurRepository;
    }

    @Transactional(readOnly = true)
    public List<Livre> findDisponiblesParCategorie(Long categorieId) {
        if (!categorieRepository.existsById(categorieId)) {
            throw new ResourceNotFoundException("Catégorie introuvable avec l'id " + categorieId);
        }
        return livreRepository.findDisponiblesParCategorie(categorieId);
    }

    // Remplace les références {"id": x} reçues en JSON par les vraies entités (et vérifie qu'elles existent)
    @Override
    protected void beforeSave(Livre livre) {
        Long categorieId = livre.getCategorie().getId();
        livre.setCategorie(categorieRepository.findById(categorieId)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable avec l'id " + categorieId)));

        Set<Auteur> auteurs = new HashSet<>();
        for (Auteur a : livre.getAuteurs()) {
            auteurs.add(auteurRepository.findById(a.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Auteur introuvable avec l'id " + a.getId())));
        }
        livre.setAuteurs(auteurs);
    }
}
