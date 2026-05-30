package pbo.f01.service;

import pbo.f01.entity.ParkingArea;
import pbo.f01.entity.Vehicle;
import pbo.f01.repository.ParkingAreaRepository;
import pbo.f01.repository.VehicleRepository;

import java.util.Comparator;
import java.util.List;

/**
 * Service layer — berisi semua business logic aplikasi Park-IT.
 * Dipanggil oleh App (driver class) berdasarkan perintah dari STDIN.
 */
public class ParkingService {

    private final ParkingAreaRepository areaRepo;
    private final VehicleRepository vehicleRepo;

    public ParkingService(ParkingAreaRepository areaRepo, VehicleRepository vehicleRepo) {
        this.areaRepo = areaRepo;
        this.vehicleRepo = vehicleRepo;
    }

    // =========================================================
    // TUGAS 3A: area-add
    // Format: area-add#<name>#<capacity>#<allowed_type>
    // =========================================================
    public void addArea(String name, int capacity, String allowedType) {

        // Cek duplikat
        if (areaRepo.findByName(name) != null) {
            return;
        }

        ParkingArea area = new ParkingArea(name, capacity, allowedType);
        areaRepo.save(area);
    }

    // =========================================================
    // TUGAS 3B: vehicle-add
    // Format: vehicle-add#<plate_number>#<owner>#<type>
    // =========================================================
    public void addVehicle(String plateNumber, String owner, String type) {

        // Cek duplikat
        if (vehicleRepo.findByPlate(plateNumber) != null) {
            return;
        }

        Vehicle v = new Vehicle(plateNumber, owner, type);
        vehicleRepo.save(v);
    }

    // =========================================================
    // TUGAS 4: park
    // Format: park#<plate_number>#<area_name>
    // =========================================================
    public void parkVehicle(String plateNumber, String areaName) {

        Vehicle vehicle = vehicleRepo.findByPlate(plateNumber);
        ParkingArea area = areaRepo.findByName(areaName);

        // Kendaraan tidak ada
        if (vehicle == null) {
            return;
        }

        // Area tidak ada
        if (area == null) {
            return;
        }

        // Tipe kendaraan tidak sesuai
        if (!area.accepts(vehicle.getType())) {
            return;
        }

        // Area penuh
        if (area.isFull()) {
            return;
        }

        // Park kendaraan
        vehicle.setParkingArea(area);
        vehicleRepo.update(vehicle);

        // Tambahkan kendaraan ke area
        area.getVehicles().add(vehicle);
    }

    // =========================================================
    // TUGAS 5: display-all
    // =========================================================
    public void displayAll() {

        List<ParkingArea> areas = areaRepo.findAllOrderByName();

        for (ParkingArea area : areas) {

            List<Vehicle> vehicles = area.getVehicles();

            // Sort kendaraan berdasarkan plat
            vehicles.sort(Comparator.comparing(Vehicle::getPlateNumber));

            int occupied = vehicles.size();

            // Tampilkan area
            System.out.println(
                    area.getName() + " "
                    + area.getAllowedType() + " "
                    + area.getCapacity() + "|"
                    + occupied
            );

            // Tampilkan kendaraan
            for (Vehicle v : vehicles) {

                System.out.println(
                        v.getPlateNumber() + " "
                        + v.getOwner() + " "
                        + v.getType()
                );
            }
        }
    }
}