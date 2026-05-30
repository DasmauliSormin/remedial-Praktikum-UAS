package pbo.f01.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import pbo.f01.entity.ParkingArea;

import java.util.List;

public class ParkingAreaRepository {

    private final EntityManager em;

    public ParkingAreaRepository(EntityManager em) {
        this.em = em;
    }

    public void save(ParkingArea area) {
        em.getTransaction().begin();
        em.persist(area);
        em.getTransaction().commit();
    }

    public ParkingArea findByName(String name) {
        try {
            return em.createQuery(
                "SELECT a FROM ParkingArea a WHERE a.name = :name", ParkingArea.class)
                .setParameter("name", name)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<ParkingArea> findAllOrderByName() {
        return em.createQuery(
            "SELECT a FROM ParkingArea a ORDER BY a.name ASC", ParkingArea.class)
            .getResultList();
    }

    public ParkingArea update(ParkingArea area) {
        em.getTransaction().begin();
        ParkingArea merged = em.merge(area);
        em.getTransaction().commit();
        return merged;
    }
}