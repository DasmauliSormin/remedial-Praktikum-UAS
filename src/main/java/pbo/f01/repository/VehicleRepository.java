package pbo.f01.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import pbo.f01.entity.Vehicle;

public class VehicleRepository {

    private final EntityManager em;

    public VehicleRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Vehicle vehicle) {
        em.getTransaction().begin();
        em.persist(vehicle);
        em.getTransaction().commit();
    }

    public Vehicle findByPlate(String plateNumber) {
        try {
            return em.createQuery(
                "SELECT v FROM Vehicle v WHERE v.plateNumber = :plate", Vehicle.class)
                .setParameter("plate", plateNumber)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Vehicle update(Vehicle vehicle) {
        em.getTransaction().begin();
        Vehicle merged = em.merge(vehicle);
        em.getTransaction().commit();
        return merged;
    }
}