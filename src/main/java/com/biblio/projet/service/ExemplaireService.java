package com.biblio.projet.service;

import com.biblio.projet.exception.ResourceNotFoundException;
import com.biblio.projet.model.Exemplaire;
import com.biblio.projet.repository.ExemplaireRepository;
import com.biblio.projet.repository.LivreRepository;
import org.springframework.stereotype.Service;

@Service
public class ExemplaireService extends CrudService<Exemplaire> {

    private final LivreRepository livreRepository;

    public ExemplaireService(ExemplaireRepository repository, LivreRepository livreRepository) {
        super(repository, "Exemplaire");
        this.livreRepository = livreRepository;
    }

    @Override
    protected void beforeSave(Exemplaire exemplaire) {
        Long livreId = exemplaire.getLivre().getId();
        exemplaire.setLivre(livreRepository.findById(livreId)
                .orElseThrow(() -> new ResourceNotFoundException("Livre introuvable avec l'id " + livreId)));
    }
}
