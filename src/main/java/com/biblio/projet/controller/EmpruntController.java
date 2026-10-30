package com.biblio.projet.controller;

import com.biblio.projet.dto.EmpruntRequest;
import com.biblio.projet.dto.LivreStat;
import com.biblio.projet.model.Emprunt;
import com.biblio.projet.service.EmpruntService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/emprunts")
public class EmpruntController {

    private final EmpruntService service;

    public EmpruntController(EmpruntService service) {
        this.service = service;
    }

    @GetMapping
    public List<Emprunt> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Emprunt getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST /api/emprunts  {"membreId":1,"exemplaireId":1}
    @PostMapping
    public ResponseEntity<Emprunt> emprunter(@Valid @RequestBody EmpruntRequest request) {
        Emprunt emprunt = service.emprunter(request.membreId(), request.exemplaireId());
        return ResponseEntity.status(HttpStatus.CREATED).body(emprunt);
    }

    // PUT /api/emprunts/{id}/retour
    @PutMapping("/{id}/retour")
    public Emprunt retourner(@PathVariable Long id) {
        return service.retourner(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.supprimer(id);
    }

    // GET /api/emprunts/en-retard
    @GetMapping("/en-retard")
    public List<Emprunt> enRetard() {
        return service.findEnRetard();
    }

    // GET /api/emprunts/top-livres?limite=5
    @GetMapping("/top-livres")
    public List<LivreStat> topLivres(@RequestParam(defaultValue = "5") int limite) {
        return service.topLivres(limite);
    }
}
