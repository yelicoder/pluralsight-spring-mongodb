package pluralsight.airportmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import pluralsight.airportmanagement.domain.FlightInformation;

@SpringBootTest
class AirportManagementApplicationTests {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public void testEmptyDatabase(){
        mongoTemplate.dropCollection(FlightInformation.class);
    }


}
