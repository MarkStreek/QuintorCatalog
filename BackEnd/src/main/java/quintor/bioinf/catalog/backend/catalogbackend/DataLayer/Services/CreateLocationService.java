package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Location;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.LocationRepository;

/**
 * Service class that is responsible for adding a location to the database.
 * <p>
 * The service class is responsible for adding a location to the database.
 * It first creates a location and then checks if the location already exists.
 * If the location does not exist, it is saved to the database.
 * Otherwise, The already used location is not added again.
 */
@Service
public class CreateLocationService {

    private static final Logger log = LoggerFactory.getLogger(CreateLocationService.class);
    private final LocationRepository locationRepository;

    @Autowired
    public CreateLocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * Main method that adds a location to the database.
     * It first creates a location and then checks if the location already exists.
     * If the location does not exist, it is saved to the database.
     * Otherwise, The already used location is not added again.
     *
     * @param name Name of the location (i.e. "Server room")
     * @param city City of the location
     * @param locationAddress Address of the location
     */
    public Location addLocation(
            String name,
            String city,
            String locationAddress)
    {
        Location location = createLocation(name, city, locationAddress);
        if (!this.checkIfLocationExists(location)) {
            this.saveLocation(location);
            return location;
        } else {
            log.warn("Location already exists, not adding it again.");
            return locationRepository.findByAddress(location.getAddress());
        }
    }


    /**
     * Method that creates a location and checks the input for validity:
     *  - It checks if the city and locationAddress are not empty
     * <p>
     *  It then sets the name, city and address of the location
     *
     * @param name Name of the location
     * @param city City of the location
     * @param locationAddress Address of the location
     * @return Location
     */
    public Location createLocation(
            String name,
            String city,
            String locationAddress
    ) {
        Location location = new Location();
        if (city.isEmpty() || locationAddress.isEmpty()) {
            log.error("City or locationAddress is empty");
            throw new IllegalArgumentException();
        }
            location.setCity(city);
            location.setAddress(locationAddress);
            location.setName(name);
        return location;
    }

    /**
     * Simple method that tests if an equal location already exists in the database
     * The address is searched for, if it exists, the location is compared to the findingLocation
     * If the location is equal, the location already exists
     *
     * @param findingLocation Location to be checked
     * @return boolean
     */
    public boolean checkIfLocationExists(Location findingLocation) {
        if (locationRepository.findByAddress(findingLocation.getAddress()) != null) {
            Location location = locationRepository.findByAddress(findingLocation.getAddress());
            return findingLocation.equals(location);
        }
        return false;
    }

    /**
     * Method that saves a location to the database
     * It calls the locationRepository to save the location
     *
     * @param location Location to be saved
     */
    protected void saveLocation(Location location) {
        locationRepository.save(location);
    }
}
