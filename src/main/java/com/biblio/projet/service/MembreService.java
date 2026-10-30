package com.biblio.projet.service;

import com.biblio.projet.model.Membre;
import com.biblio.projet.repository.MembreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MembreService extends CrudService<Membre> {

    private final MembreRepository membreRepository;

    public MembreService(MembreRepository repository) {
        super(repository, "Membre");
        this.membreRepository = repository;
    }

    @Transactional(readOnly = true)
    public List<Membre> findGrosEmprunteurs(long seuil) {
        return membreRepository.findMembresAvecEmpruntsActifsSuperieurA(seuil);
    }

    @Override
    protected void beforeSave(Membre membre) {
        if (membre.getDateInscription() == null) {
            membre.setDateInscription(LocalDate.now());
        }
    }
}
