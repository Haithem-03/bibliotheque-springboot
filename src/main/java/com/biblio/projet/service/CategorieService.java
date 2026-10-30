package com.biblio.projet.service;

import com.biblio.projet.model.Categorie;
import com.biblio.projet.repository.CategorieRepository;
import org.springframework.stereotype.Service;

@Service
public class CategorieService extends CrudService<Categorie> {
    public CategorieService(CategorieRepository repository) {
        super(repository, "Catégorie");
    }
}
