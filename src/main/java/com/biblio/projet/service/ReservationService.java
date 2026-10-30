package com.biblio.projet.service;

import com.biblio.projet.exception.ResourceNotFoundException;
import com.biblio.projet.model.Reservation;
import com.biblio.projet.repository.LivreRepository;
import com.biblio.projet.repository.MembreRepository;
import com.biblio.projet.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReservationService extends CrudService<Reservation> {

    private final MembreRepository membreRepository;
    private final LivreRepository livreRepository;

    public ReservationService(ReservationRepository repository,
                              MembreRepository membreRepository,
                              LivreRepository livreRepository) {
        super(repository, "Réservation");
        this.membreRepository = membreRepository;
        this.livreRepository = livreRepository;
    }

    @Override
    protected void beforeSave(Reservation reservation) {
        Long membreId = reservation.getMembre().getId();
        Long livreId = reservation.getLivre().getId();
        reservation.setMembre(membreRepository.findById(membreId)
                .orElseThrow(() -> new ResourceNotFoundException("Membre introuvable avec l'id " + membreId)));
        reservation.setLivre(livreRepository.findById(livreId)
                .orElseThrow(() -> new ResourceNotFoundException("Livre introuvable avec l'id " + livreId)));
        if (reservation.getDateReservation() == null) {
            reservation.setDateReservation(LocalDate.now());
        }
    }
}
