package pbo.f01.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import pbo.f01.entity.Vehicle;

/**
 * Repository untuk operasi CRUD Vehicle ke database via JPA.
 */
public class VehicleRepository {

    private final EntityManager em;

    public VehicleRepository(EntityManager em) {
        this.em = em;
    }

    /** Simpan kendaraan baru ke database */
    public void save(Vehicle vehicle) {
        em.getTransaction().begin();
        em.persist(vehicle);
        em.getTransaction().commit();
    }

    /** Cari kendaraan berdasarkan plate_number (PRIMARY KEY) */
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

    /** Update kendaraan (untuk assign parking area) */
    public Vehicle update(Vehicle vehicle) {
        em.getTransaction().begin();
        Vehicle merged = em.merge(vehicle);
        em.getTransaction().commit();
        return merged;
    }
}