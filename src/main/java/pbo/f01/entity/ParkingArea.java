package pbo.f01.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking_area")
public class ParkingArea {

    @Id
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "allowed_type", nullable = false)
    private String allowedType;

    @OneToMany(mappedBy = "parkingArea", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vehicle> vehicles = new ArrayList<>();

    public ParkingArea() {}

    public ParkingArea(String name, int capacity, String allowedType) {
        this.name        = name;
        this.capacity    = capacity;
        this.allowedType = allowedType;
    }

    public boolean isFull() {
        return vehicles.size() >= capacity;
    }

    public boolean accepts(String vehicleType) {
        return this.allowedType.equalsIgnoreCase(vehicleType);
    }

    public String getName()                    { return name; }
    public void   setName(String name)         { this.name = name; }
    public int    getCapacity()                { return capacity; }
    public void   setCapacity(int capacity)    { this.capacity = capacity; }
    public String getAllowedType()             { return allowedType; }
    public void   setAllowedType(String t)     { this.allowedType = t; }
    public List<Vehicle> getVehicles()         { return vehicles; }
    public void   setVehicles(List<Vehicle> v) { this.vehicles = v; }
}