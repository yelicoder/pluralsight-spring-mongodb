package pluralsight.airportmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import pluralsight.airportmanagement.domain.Aircraft;
import pluralsight.airportmanagement.domain.FlightInformation;
import pluralsight.airportmanagement.domain.FlightPrinter;
import pluralsight.airportmanagement.domain.FlightType;
import pluralsight.airportmanagement.queries.FlightInformationQueries;

import java.time.LocalDate;
import java.util.List;

@Component
public class ApplicationRunner implements CommandLineRunner {

    @Autowired
    private MongoTemplate mongoTemplate;

    private FlightInformationQueries flightInformationQueries;

    public ApplicationRunner(FlightInformationQueries flightInformationQueries) {
        this.flightInformationQueries = flightInformationQueries;
    }

    @Override
    public void run(String... args) {
        // Initial loading data
        FlightInformation flight = new FlightInformation();
        flight.setDepartureCity("Rome");
        flight.setDestinationCity("Paris");
        flight.setType(FlightType.International);
        flight.setDescription("Flight from Rome to Paris");
        flight.setDelayed(false);
        flight.setDurationMin(80);
        flight.setDepartureDate(LocalDate.now());
        flight.setAircraft(new Aircraft("737", 180));
        this.mongoTemplate.save(flight);

        flight = new FlightInformation();
        flight.setDepartureCity("New York");
        flight.setDestinationCity("Copenhagen");
        flight.setType(FlightType.International);
        flight.setDescription("Flight from LA to Washington via Rome");
        flight.setDelayed(false);
        flight.setDurationMin(600);
        flight.setDepartureDate(LocalDate.now());
        flight.setAircraft(new Aircraft("747", 180));
        this.mongoTemplate.save(flight);

        flight = new FlightInformation();
        flight.setDepartureCity("Bruxelles");
        flight.setDestinationCity("Bucharest");
        flight.setType(FlightType.International);
        flight.setDelayed(true);
        flight.setDurationMin(150);
        flight.setDepartureDate(LocalDate.of(2019,6,12));
        flight.setAircraft(new Aircraft("A320", 170));
        this.mongoTemplate.save(flight);

        flight = new FlightInformation();
        flight.setDepartureCity("Bucharest");
        flight.setDestinationCity("Rome");
        flight.setType(FlightType.International);
        flight.setDelayed(false);
        flight.setDurationMin(110);
        flight.setDepartureDate(LocalDate.of(2019,6,13));
        flight.setAircraft(new Aircraft("A321 NEO", 200));
        this.mongoTemplate.save(flight);

        flight = new FlightInformation();
        flight.setDepartureCity("LAS VEGAS");
        flight.setDestinationCity("Washington");
        flight.setType(FlightType.Internal);
        flight.setDelayed(false);
        flight.setDurationMin(110);
        flight.setDepartureDate(LocalDate.of(2019,6,13));
        flight.setAircraft(new Aircraft("A319", 150));
        this.mongoTemplate.save(flight);


        System.out.println("-----\nQUERY: All flights ordered by departure");
        List<FlightInformation> allFlightsOrdered = this.flightInformationQueries
                .findAll("departure", 0, 10);
        FlightPrinter.print(allFlightsOrdered);

        System.out.println("-----\nQUERY: Free text search: Rome");
        List<FlightInformation> flightsByFreeText = this.flightInformationQueries
                .findByFreeText("Rome");
        FlightPrinter.print(flightsByFreeText);
    }
}
