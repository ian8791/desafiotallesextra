package com.biblioteca.dao;

import com.biblioteca.model.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.io.IOException;
import java.util.List;

public class BookRepository {
    public Book save(Book b) throws IOException {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(b);
            em.getTransaction().commit();
            return b;
        } finally {
            em.close();
        }
    }

    public Book findById(Integer id) throws IOException {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Book.class, id);
        } finally {
            em.close();
        }
    }

    public List<Book> findAll() throws IOException {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Book> q = em.createQuery("SELECT b FROM Book b", Book.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Book update(Book b) throws IOException {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Book merged = em.merge(b);
            em.getTransaction().commit();
            return merged;
        } finally {
            em.close();
        }
    }

    public void delete(Integer id) throws IOException {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Book b = em.find(Book.class, id);
            if (b != null) em.remove(b);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
