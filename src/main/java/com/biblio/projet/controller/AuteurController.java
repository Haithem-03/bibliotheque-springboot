package com.biblio.projet.controller;

import com.biblio.projet.model.Auteur;
import com.biblio.projet.service.AuteurService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auteurs")
public class AuteurController extends CrudController<Auteur> {
    public AuteurController(AuteurService service) {
        super(service);
    }
}
