package com.biblio.projet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Livre extends BaseEntity {

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    @NotBlank(message = "L'ISBN est obligatoire")
    @Size(min = 10, max = 17, message = "L'ISBN doit contenir entre 10 et 17 caractères")
    @Column(unique = true)
    private String isbn;

    @Min(value = 1000, message = "L'année de publication est invalide")
    @Max(value = 2100, message = "L'année de publication est invalide")
    private Integer anneePublication;

    @Size(max = 1000, message = "Le résumé ne doit pas dépasser 1000 caractères")
    @Column(length = 1000)
    private String resume;

    @NotNull(message = "La catégorie est obligatoire")
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    // Livre est propriétaire de la relation ManyToMany (table de jointure livre_auteur)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "livre_auteur",
            joinColumns = @JoinColumn(name = "livre_id"),
            inverseJoinColumns = @JoinColumn(name = "auteur_id"))
    private Set<Auteur> auteurs = new HashSet<>();

    @OneToMany(mappedBy = "livre")
    @JsonIgnore
    private List<Exemplaire> exemplaires = new ArrayList<>();

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAnneePublication() {
        return anneePublication;
    }

    public void setAnneePublication(Integer anneePublication) {
        this.anneePublication = anneePublication;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Set<Auteur> getAuteurs() {
        return auteurs;
    }

    public void setAuteurs(Set<Auteur> auteurs) {
        this.auteurs = auteurs;
    }

    public List<Exemplaire> getExemplaires() {
        return exemplaires;
    }

    public void setExemplaires(List<Exemplaire> exemplaires) {
        this.exemplaires = exemplaires;
    }
}
