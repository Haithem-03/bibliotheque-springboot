package com.biblio.projet.controller;

import com.biblio.projet.model.Reservation;
import com.biblio.projet.service.ReservationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController extends CrudController<Reservation> {
    public ReservationController(ReservationService service) {
        super(service);
    }
}
