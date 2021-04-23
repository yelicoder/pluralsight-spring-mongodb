package pluralsight.airportmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import pluralsight.airportmanagement.domain.Aircraft;
import pluralsight.airportmanagement.domain.FlightInformation;
import pluralsight.airportmanagement.domain.FlightType;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AirportManagementApplicationTests {

    @Autowired
    MongoTemplate mongoTemplate;

    private void startUp() {
        mongoTemplate.dropCollection(FlightInformation.class);
        seedDatabase();
    }

    private void tearDown() {
        mongoTemplate.dropCollection(FlightInformation.class);
    }

    @Test
    public void testEmptyDatabase(){
        mongoTemplate.dropCollection(FlightInformation.class);
    }

    @Test
    public void testSeedDatabase() {
        startUp();

        int count = mongoTemplate.findAll(FlightInformation.class).size();
        assertTrue(count==6);
    }

    @Test
    public void testCountAll() {
        startUp();

        //Query countAll = Query.query(new Criteria());
        Query countAll = new Query();
        long count = mongoTemplate.count(countAll, FlightInformation.class);

        assertTrue(count == 6);
    }

    @Test void testUpdate() {

        startUp();

        Query departingFromRome = Query.query(
                Criteria.where("departure").is("Rome")
        );

        long numberOfDepartingFlightFromRome =
                mongoTemplate.count(departingFromRome, FlightInformation.class);

        Update setDelayed = Update.update("isDelayed", true);

        this.mongoTemplate.updateMulti(
                departingFromRome,
                setDelayed,
                FlightInformation.class);

        Query departingFlightFromRomeDelayed = Query.query(
                Criteria.where("departure").is("Rome")
                .andOperator(
                        Criteria.where("isDelayed").is(true)
                )
        );

        long numberOfDepartingFlightFromRomeDelayed =
                mongoTemplate.count(departingFlightFromRomeDelayed, FlightInformation.class);

        assertTrue(numberOfDepartingFlightFromRome == numberOfDepartingFlightFromRomeDelayed);

    }

    @Test
    public void testRemove() {
        startUp();

        Query lessThanTwoHours = Query.query(
                Criteria.where("durationMin").lt(120)
        );

        long numberOfFlightsLessThanTwoHours =
                mongoTemplate.count(lessThanTwoHours, FlightInformation.class);

        mongoTemplate.findAllAndRemove(lessThanTwoHours, FlightInformation.class);

        long numberOfFlightsAfterRemove = mongoTemplate.count(new Query(), FlightInformation.class);

        assertTrue( (6-numberOfFlightsLessThanTwoHours) == numberOfFlightsAfterRemove);

    }



    private void seedDatabase() {
        FlightInformation flightOne = new FlightInformation();
        flightOne.setDelayed(false);
        flightOne.setDepartureCity("Rome");
        flightOne.setDestinationCity("Paris");
        flightOne.setDepartureDate(LocalDate.of(2019, 3, 12));
        flightOne.setType(FlightType.International);
        flightOne.setDurationMin(80);
        flightOne.setAircraft(new Aircraft("737", 180));
        flightOne.setDescription("Flight from Rome to Paris");

        FlightInformation flightTwo = new FlightInformation();
        flightTwo.setDelayed(false);
        flightTwo.setDepartureCity("New York");
        flightTwo.setDestinationCity("Copenhagen");
        flightTwo.setDepartureDate(LocalDate.of(2019, 5, 11));
        flightTwo.setType(FlightType.International);
        flightTwo.setDurationMin(600);
        flightTwo.setAircraft(new Aircraft("747", 300));
        flightTwo.setDescription("Flight from NY to Copenhagen via Rome");

        FlightInformation flightThree = new FlightInformation();
        flightThree.setDelayed(true);
        flightThree.setDepartureCity("Bruxelles");
        flightThree.setDestinationCity("Bucharest");
        flightThree.setDepartureDate(LocalDate.of(2019, 6, 12));
        flightThree.setType(FlightType.International);
        flightThree.setDurationMin(150);
        flightThree.setAircraft(new Aircraft("A320", 170));

        FlightInformation flightFour = new FlightInformation();
        flightFour.setDelayed(true);
        flightFour.setDepartureCity("Madrid");
        flightFour.setDestinationCity("Barcelona");
        flightFour.setDepartureDate(LocalDate.of(2019, 6, 12));
        flightFour.setType(FlightType.Internal);
        flightFour.setDurationMin(120);
        flightFour.setAircraft(new Aircraft("A319", 150));

        FlightInformation flightFive = new FlightInformation();
        flightFive.setDelayed(false);
        flightFive.setDepartureCity("Las Vegas");
        flightFive.setDestinationCity("Washington");
        flightFive.setDepartureDate(LocalDate.of(2019, 6, 10));
        flightFive.setType(FlightType.Internal);
        flightFive.setDurationMin(400);
        flightFive.setAircraft(new Aircraft("A319", 150));
        flightTwo.setDescription("Flight from LA to Washington via Paris");

        FlightInformation flightSix = new FlightInformation();
        flightSix.setDelayed(false);
        flightSix.setDepartureCity("Bucharest");
        flightSix.setDestinationCity("Rome");
        flightSix.setDepartureDate(LocalDate.of(2019, 6, 13));
        flightSix.setType(FlightType.International);
        flightSix.setDurationMin(110);
        flightSix.setAircraft(new Aircraft("A321 Neo", 200));

        // Seed
        List<FlightInformation> flights = Arrays.asList(
                flightOne,
                flightTwo,
                flightThree,
                flightFour,
                flightFive,
                flightSix
        );
        mongoTemplate.insertAll(flights);
    }

}
