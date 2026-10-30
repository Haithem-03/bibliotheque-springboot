package com.biblio.projet.service;

import com.biblio.projet.exception.ResourceNotFoundException;
import com.biblio.projet.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service générique : CRUD commun à toutes les entités.
 * Les services métier héritent de cette classe et surchargent beforeSave() si besoin.
 */
public abstract class CrudService<T extends BaseEntity> {

    protected final JpaRepository<T, Long> repository;
    private final String nomEntite;

    protected CrudService(JpaRepository<T, Long> repository, String nomEntite) {
        this.repository = repository;
        this.nomEntite = nomEntite;
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public T findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(nomEntite + " introuvable avec l'id " + id));
    }

    @Transactional
    public T create(T entity) {
        entity.setId(null);
        beforeSave(entity);
        return repository.save(entity);
    }

    @Transactional
    public T update(Long id, T entity) {
        findById(id); // lève 404 si absent
        entity.setId(id);
        beforeSave(entity);
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    /** Point d'extension : vérifications et valeurs par défaut avant sauvegarde. */
    protected void beforeSave(T entity) {
    }
}
