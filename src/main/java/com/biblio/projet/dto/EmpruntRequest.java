package com.biblio.projet.dto;

import jakarta.validation.constraints.NotNull;

public record EmpruntRequest(
        @NotNull(message = "membreId est obligatoire") Long membreId,
        @NotNull(message = "exemplaireId est obligatoire") Long exemplaireId) {
}
