package com.biblio.projet.controller;

import com.biblio.projet.model.Categorie;
import com.biblio.projet.service.CategorieService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategorieController extends CrudController<Categorie> {
    public CategorieController(CategorieService service) {
        super(service);
    }
}
