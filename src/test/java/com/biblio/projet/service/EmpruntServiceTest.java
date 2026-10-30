package com.biblio.projet.service;

import com.biblio.projet.exception.ConflictException;
import com.biblio.projet.model.Emprunt;
import com.biblio.projet.model.Exemplaire;
import com.biblio.projet.model.Membre;
import com.biblio.projet.repository.EmpruntRepository;
import com.biblio.projet.repository.ExemplaireRepository;
import com.biblio.projet.repository.MembreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmpruntServiceTest {

    @Mock EmpruntRepository empruntRepository;
    @Mock MembreRepository membreRepository;
    @Mock ExemplaireRepository exemplaireRepository;
    @InjectMocks EmpruntService service;

    @Test
    void emprunter_exemplaireIndisponible_leveConflit() {
        Exemplaire ex = new Exemplaire();
        ex.setCodeBarres("X1");
        ex.setDisponible(false);
        when(membreRepository.findById(1L)).thenReturn(Optional.of(new Membre()));
        when(exemplaireRepository.findById(2L)).thenReturn(Optional.of(ex));

        assertThrows(ConflictException.class, () -> service.emprunter(1L, 2L));
        verify(empruntRepository, never()).save(any());
    }

    @Test
    void emprunter_exemplaireDisponible_creeEmpruntEtRendIndisponible() {
        Exemplaire ex = new Exemplaire();
        ex.setDisponible(true);
        when(membreRepository.findById(1L)).thenReturn(Optional.of(new Membre()));
        when(exemplaireRepository.findById(2L)).thenReturn(Optional.of(ex));
        when(empruntRepository.countByMembreIdAndDateRetourEffectiveIsNull(1L)).thenReturn(0L);
        when(empruntRepository.save(any(Emprunt.class))).thenAnswer(i -> i.getArgument(0));

        Emprunt e = service.emprunter(1L, 2L);

        assertNotNull(e.getDateRetourPrevue());
        assertFalse(ex.isDisponible());
    }

    @Test
    void emprunter_limiteAtteinte_leveConflit() {
        Exemplaire ex = new Exemplaire();
        ex.setDisponible(true);
        when(membreRepository.findById(1L)).thenReturn(Optional.of(new Membre()));
        when(exemplaireRepository.findById(2L)).thenReturn(Optional.of(ex));
        when(empruntRepository.countByMembreIdAndDateRetourEffectiveIsNull(1L)).thenReturn(5L);

        assertThrows(ConflictException.class, () -> service.emprunter(1L, 2L));
    }
}
