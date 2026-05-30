package pbo.f01.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import pbo.f01.entity.ParkingArea;

import java.util.List;

/**
 * Repository untuk operasi CRUD ParkingArea ke database via JPA.
 */
public class ParkingAreaRepository {

    private final EntityManager em;

    public ParkingAreaRepository(EntityManager em) {
        this.em = em;
    }

    /** Simpan area parkir baru ke database */
    public void save(ParkingArea area) {
        em.getTransaction().begin();
        em.persist(area);
        em.getTransaction().commit();
    }

    /** Cari area berdasarkan nama (PRIMARY KEY) */
    public ParkingArea findByName(String name) {
        try {
            TypedQuery<ParkingArea> q = em.createQuery(
                "SELECT a FROM ParkingArea a WHERE a.name = :name", ParkingArea.class);
            q.setParameter("name", name);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /** Ambil semua area parkir, urut ascending by name */
    public List<ParkingArea> findAllOrderByName() {
        return em.createQuery(
            "SELECT a FROM ParkingArea a ORDER BY a.name ASC", ParkingArea.class)
            .getResultList();
    }

    /** Update area (untuk menyimpan perubahan relasi, dsb) */
    public ParkingArea update(ParkingArea area) {
        em.getTransaction().begin();
        ParkingArea merged = em.merge(area);
        em.getTransaction().commit();
        return merged;
    }
}