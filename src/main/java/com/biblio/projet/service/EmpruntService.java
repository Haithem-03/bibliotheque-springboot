package com.biblio.projet.service;

import com.biblio.projet.dto.LivreStat;
import com.biblio.projet.exception.ConflictException;
import com.biblio.projet.exception.ResourceNotFoundException;
import com.biblio.projet.model.Emprunt;
import com.biblio.projet.model.Exemplaire;
import com.biblio.projet.model.Membre;
import com.biblio.projet.repository.EmpruntRepository;
import com.biblio.projet.repository.ExemplaireRepository;
import com.biblio.projet.repository.MembreRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmpruntService {

    static final int MAX_EMPRUNTS_ACTIFS = 5;
    static final int DUREE_EMPRUNT_JOURS = 14;

    private final EmpruntRepository empruntRepository;
    private final MembreRepository membreRepository;
    private final ExemplaireRepository exemplaireRepository;

    public EmpruntService(EmpruntRepository empruntRepository,
                          MembreRepository membreRepository,
                          ExemplaireRepository exemplaireRepository) {
        this.empruntRepository = empruntRepository;
        this.membreRepository = membreRepository;
        this.exemplaireRepository = exemplaireRepository;
    }

    @Transactional(readOnly = true)
    public List<Emprunt> findAll() {
        return empruntRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Emprunt findById(Long id) {
        return empruntRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emprunt introuvable avec l'id " + id));
    }

    @Transactional
    public Emprunt emprunter(Long membreId, Long exemplaireId) {
        Membre membre = membreRepository.findById(membreId)
                .orElseThrow(() -> new ResourceNotFoundException("Membre introuvable avec l'id " + membreId));
        Exemplaire exemplaire = exemplaireRepository.findById(exemplaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Exemplaire introuvable avec l'id " + exemplaireId));

        // Règle métier 1 : un exemplaire indisponible ne peut pas être emprunté
        if (!exemplaire.isDisponible()) {
            throw new ConflictException("L'exemplaire " + exemplaire.getCodeBarres() + " n'est pas disponible");
        }
        // Règle métier 2 : limite d'emprunts actifs par membre
        if (empruntRepository.countByMembreIdAndDateRetourEffectiveIsNull(membreId) >= MAX_EMPRUNTS_ACTIFS) {
            throw new ConflictException("Le membre a déjà atteint la limite de " + MAX_EMPRUNTS_ACTIFS + " emprunts en cours");
        }

        Emprunt emprunt = new Emprunt();
        emprunt.setMembre(membre);
        emprunt.setExemplaire(exemplaire);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(LocalDate.now().plusDays(DUREE_EMPRUNT_JOURS));

        exemplaire.setDisponible(false);
        exemplaireRepository.save(exemplaire);
        return empruntRepository.save(emprunt);
    }

    @Transactional
    public Emprunt retourner(Long empruntId) {
        Emprunt emprunt = findById(empruntId);
        if (emprunt.getDateRetourEffective() != null) {
            throw new ConflictException("Cet emprunt a déjà été retourné");
        }
        emprunt.setDateRetourEffective(LocalDate.now());
        Exemplaire exemplaire = emprunt.getExemplaire();
        exemplaire.setDisponible(true);
        exemplaireRepository.save(exemplaire);
        return empruntRepository.save(emprunt);
    }

    @Transactional
    public void supprimer(Long id) {
        Emprunt emprunt = findById(id);
        // Si l'emprunt est en cours, on libère l'exemplaire avant de supprimer
        if (emprunt.getDateRetourEffective() == null) {
            emprunt.getExemplaire().setDisponible(true);
            exemplaireRepository.save(emprunt.getExemplaire());
        }
        empruntRepository.delete(emprunt);
    }

    @Transactional(readOnly = true)
    public List<Emprunt> findEnRetard() {
        return empruntRepository.findByDateRetourEffectiveIsNullAndDateRetourPrevueBefore(LocalDate.now());
    }

    @Transactional(readOnly = true)
    public List<LivreStat> topLivres(int limite) {
        return empruntRepository.findLivresLesPlusEmpruntes(PageRequest.of(0, limite));
    }
}
