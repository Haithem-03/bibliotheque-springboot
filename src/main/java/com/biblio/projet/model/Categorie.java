package com.biblio.projet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Categorie extends BaseEntity {

    @NotBlank(message = "Le libellé est obligatoire")
    @Column(unique = true)
    private String libelle;

    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    private String description;

    @OneToMany(mappedBy = "categorie")
    @JsonIgnore
    private List<Livre> livres = new ArrayList<>();

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Livre> getLivres() {
        return livres;
    }

    public void setLivres(List<Livre> livres) {
        this.livres = livres;
    }
}
