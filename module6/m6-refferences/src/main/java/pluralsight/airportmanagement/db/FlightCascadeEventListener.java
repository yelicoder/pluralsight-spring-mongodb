package pluralsight.airportmanagement.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import pluralsight.airportmanagement.domain.Airport;
import pluralsight.airportmanagement.domain.FlightInformation;

@Component
public class FlightCascadeEventListener extends AbstractMongoEventListener<Object> {

    @Autowired
    private MongoTemplate template;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {

        Object doc = event.getSource();

        if ((doc instanceof FlightInformation) && (((FlightInformation) doc).getDeparture() != null)) {

            Airport departure = ((FlightInformation) doc ).getDeparture();
            template.save(departure);

        }

        if ((doc instanceof FlightInformation) && (((FlightInformation) doc).getDestination() != null)) {

            Airport destination = ((FlightInformation) doc ).getDestination();
            template.save(destination);

        }
    }
}
