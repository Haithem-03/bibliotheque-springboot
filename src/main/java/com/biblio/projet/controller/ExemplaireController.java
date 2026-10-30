package com.biblio.projet.controller;

import com.biblio.projet.model.Exemplaire;
import com.biblio.projet.service.ExemplaireService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exemplaires")
public class ExemplaireController extends CrudController<Exemplaire> {
    public ExemplaireController(ExemplaireService service) {
        super(service);
    }
}
