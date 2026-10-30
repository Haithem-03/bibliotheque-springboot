package com.biblio.projet.service;

import com.biblio.projet.model.Auteur;
import com.biblio.projet.repository.AuteurRepository;
import org.springframework.stereotype.Service;

@Service
public class AuteurService extends CrudService<Auteur> {
    public AuteurService(AuteurRepository repository) {
        super(repository, "Auteur");
    }
}
