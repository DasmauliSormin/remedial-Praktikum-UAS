package pbo.f01.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity: Area Parkir
 * Tabel: parking_area
 */
@Entity
@Table(name = "parking_area")
public class ParkingArea {

    @Id
    @Column(name = "name", nullable = false, unique = true)
    private String name;          // PRIMARY KEY — nama area unik

    @Column(name = "capacity", nullable = false)
    private int capacity;         // kapasitas maksimal

    @Column(name = "allowed_type", nullable = false)
    private String allowedType;   // "car" atau "motorcycle"

    /**
     * Relasi One-to-Many ke Vehicle.
     * mappedBy = nama field di Vehicle yang menyimpan ParkingArea.
     * cascade = jika area dihapus, kendaraan di dalamnya ikut dihapus.
     */
    @OneToMany(mappedBy = "parkingArea", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("plateNumber ASC")   // kendaraan diurutkan ascending by plate
    private List<Vehicle> vehicles = new ArrayList<>();

    // --- Constructors ---
    public ParkingArea() {}

    public ParkingArea(String name, int capacity, String allowedType) {
        this.name        = name;
        this.capacity    = capacity;
        this.allowedType = allowedType;
    }

    // --- Business Logic ---
    public boolean isFull() {
        return vehicles.size() >= capacity;
    }

    public boolean accepts(String vehicleType) {
        return this.allowedType.equalsIgnoreCase(vehicleType);
    }

    // --- Getters & Setters ---
    public String getName()                    { return name; }
    public void   setName(String name)         { this.name = name; }

    public int    getCapacity()                { return capacity; }
    public void   setCapacity(int capacity)    { this.capacity = capacity; }

    public String getAllowedType()             { return allowedType; }
    public void   setAllowedType(String t)     { this.allowedType = t; }

    public List<Vehicle> getVehicles()         { return vehicles; }
    public void setVehicles(List<Vehicle> v)   { this.vehicles = v; }
}