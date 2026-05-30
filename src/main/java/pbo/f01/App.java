package pbo.f01;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pbo.f01.repository.ParkingAreaRepository;
import pbo.f01.repository.VehicleRepository;
import pbo.f01.service.ParkingService;

import java.util.Scanner;

/**
 * Driver class utama — membaca perintah dari STDIN dan mendelegasikan ke ParkingService.
 *
 * Perintah yang didukung:
 *   area-add#<name>#<capacity>#<allowed_type>
 *   vehicle-add#<plate_number>#<owner>#<type>
 *   park#<plate_number>#<area_name>
 *   display-all
 */
public class App {

    public static void main(String[] args) {

        // 1. Inisialisasi JPA EntityManagerFactory
        //    Nama "ParkItPU" harus sama dengan yang ada di persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ParkItPU");
        EntityManager        em  = emf.createEntityManager();

        // 2. Buat repository dan service
        ParkingAreaRepository areaRepo    = new ParkingAreaRepository(em);
        VehicleRepository     vehicleRepo = new VehicleRepository(em);
        ParkingService        service     = new ParkingService(areaRepo, vehicleRepo);

        // 3. Baca perintah dari STDIN baris per baris
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            // Pisah token dengan delimiter '#'
            String[] parts = line.split("#");
            String command = parts[0].trim();

            switch (command) {

                case "area-add":
                    // area-add#<name>#<capacity>#<allowed_type>
                    if (parts.length == 4) {
                        String name        = parts[1].trim();
                        int    capacity    = Integer.parseInt(parts[2].trim());
                        String allowedType = parts[3].trim();
                        service.addArea(name, capacity, allowedType);
                    }
                    break;

                case "vehicle-add":
                    // vehicle-add#<plate_number>#<owner>#<type>
                    if (parts.length == 4) {
                        String plate = parts[1].trim();
                        String owner = parts[2].trim();
                        String type  = parts[3].trim();
                        service.addVehicle(plate, owner, type);
                    }
                    break;

                case "park":
                    // park#<plate_number>#<area_name>
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
                    // Perintah tidak dikenal — abaikan
                    break;
            }
            // 4. Tutup resource
        }

        // 4. Tutup resource
        scanner.close();
        em.close();
        emf.close();
        }
    }

