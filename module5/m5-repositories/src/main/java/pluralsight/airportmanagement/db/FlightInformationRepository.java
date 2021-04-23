package pluralsight.airportmanagement.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pluralsight.airportmanagement.domain.FlightInformation;

import java.util.List;

@Repository
public interface FlightInformationRepository
        extends MongoRepository<FlightInformation, String> {

    List<FlightInformation> findByDepartureCityAndDestinationCity(String departure, String destination);

    @Query("{'aircraft.nbSeats' : {$gte: ?0}}")
    List<FlightInformation> findByMinAircraftNbSeats(int minNbSeats);
}
