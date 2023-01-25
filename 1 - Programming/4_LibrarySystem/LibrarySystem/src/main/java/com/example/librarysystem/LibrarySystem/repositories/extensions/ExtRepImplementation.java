package com.example.librarysystem.LibrarySystem.repositories.extensions;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

public class ExtRepImplementation<T, ID extends Serializable> 
    extends SimpleJpaRepository<T, ID> implements ExtendedRepository<T, ID>  {

    private EntityManager entityManager;

    public ExtRepImplementation(JpaEntityInformation<T, ?>
    entityInformation, EntityManager entityManager){
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public T findById(Integer id){
        // Builder and Query based on input class
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cQuery = builder.createQuery(getDomainClass());
        Root<T> root = cQuery.from(getDomainClass());
        // Query to get entry by id
        cQuery
            .select(root)
            .where(builder
                .like(root.<String>get("id"), id.toString()));
        TypedQuery<T> tQuery = entityManager.createQuery(cQuery);
        // Executes query and returns found entry
        return tQuery.getResultList().get(0);
    }

    @Override
    @Transactional
    public List<T> findByAttributeContainsText(String attributeName, String text) {        
        // Builder and Query based on input class
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cQuery = builder.createQuery(getDomainClass());
        Root<T> root = cQuery.from(getDomainClass());
        // Query to compare attribute with input text
        cQuery
            .select(root)
                .where(builder
                    .like(root.<String>get(attributeName), "%" + text + "%"));
        TypedQuery<T> tQuery = entityManager.createQuery(cQuery);
        // Executes query and returns found entries
        return tQuery.getResultList();
    }
    
    @Override
    @Transactional
    public List<T> findByAtLeast(String attributeName, Integer minimum) {
        // Builder and Query based on input class
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getDomainClass());
        Root<T> root = query.from(getDomainClass());
        
        // Query to compare attribute with input value
        query.select(root).where(builder.ge(root.<Integer>get(attributeName), minimum));
        TypedQuery<T> tQuery = entityManager.createQuery(query);

        // Executes query and returns found entries
        return tQuery.getResultList();
    }
    
}
