package com.biblio.projet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Exemplaire extends BaseEntity {

    @NotBlank(message = "Le code-barres est obligatoire")
    @Column(unique = true)
    private String codeBarres;

    @NotNull(message = "L'état est obligatoire")
    @Enumerated(EnumType.STRING)
    private EtatExemplaire etat;

    private boolean disponible = true;

    @NotNull(message = "Le livre est obligatoire")
    @ManyToOne
    @JoinColumn(name = "livre_id")
    private Livre livre;

    public String getCodeBarres() {
        return codeBarres;
    }

    public void setCodeBarres(String codeBarres) {
        this.codeBarres = codeBarres;
    }

    public EtatExemplaire getEtat() {
        return etat;
    }

    public void setEtat(EtatExemplaire etat) {
        this.etat = etat;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }
}
