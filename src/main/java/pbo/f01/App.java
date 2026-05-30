package pbo.f01;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pbo.f01.repository.ParkingAreaRepository;
import pbo.f01.repository.VehicleRepository;
import pbo.f01.service.ParkingService;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ParkItPU");
        EntityManager        em  = emf.createEntityManager();

        ParkingAreaRepository areaRepo    = new ParkingAreaRepository(em);
        VehicleRepository     vehicleRepo = new VehicleRepository(em);
        ParkingService        service     = new ParkingService(areaRepo, vehicleRepo);

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts   = line.split("#");
            String   command = parts[0].trim();

            switch (command) {
                case "area-add":
                    if (parts.length == 4) {
                        String name        = parts[1].trim();
                        int    capacity    = Integer.parseInt(parts[2].trim());
                        String allowedType = parts[3].trim();
                        service.addArea(name, capacity, allowedType);
                    }
                    break;

                case "vehicle-add":
                    if (parts.length == 4) {
                        String plate = parts[1].trim();
                        String owner = parts[2].trim();
                        String type  = parts[3].trim();
                        service.addVehicle(plate, owner, type);
                    }
                    break;

                case "park":
                    if (parts.length == 3) {
                        String plate    = parts[1].trim();
                        String areaName = parts[2].trim();
                        service.parkVehicle(plate, areaName);
                    }
                    break;

                case "display-all":
                    service.displayAll();
                    break;

                default:
                    break;
            }
        }

        scanner.close();
        em.close();
        emf.close();
    }
}