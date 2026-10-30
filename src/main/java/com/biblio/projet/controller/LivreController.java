package com.biblio.projet.controller;

import com.biblio.projet.model.Livre;
import com.biblio.projet.service.LivreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/livres")
public class LivreController extends CrudController<Livre> {

    private final LivreService livreService;

    public LivreController(LivreService service) {
        super(service);
        this.livreService = service;
    }

    // GET /api/livres/disponibles?categorieId=1
    @GetMapping("/disponibles")
    public List<Livre> disponibles(@RequestParam Long categorieId) {
        return livreService.findDisponiblesParCategorie(categorieId);
    }
}
