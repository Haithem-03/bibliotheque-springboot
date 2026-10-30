package com.biblio.projet.controller;

import com.biblio.projet.model.Membre;
import com.biblio.projet.service.MembreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/membres")
public class MembreController extends CrudController<Membre> {

    private final MembreService membreService;

    public MembreController(MembreService service) {
        super(service);
        this.membreService = service;
    }

    // GET /api/membres/gros-emprunteurs?seuil=3
    @GetMapping("/gros-emprunteurs")
    public List<Membre> grosEmprunteurs(@RequestParam(defaultValue = "3") long seuil) {
        return membreService.findGrosEmprunteurs(seuil);
    }
}
