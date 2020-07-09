package edu.iis.mto.bdd.trains.cucumber.steps;

import java.util.List;

import edu.iis.mto.bdd.trains.services.*;
import org.joda.time.LocalTime;

import cucumber.api.PendingException;
import cucumber.api.Transform;
import cucumber.api.java.pl.Gdy;
import cucumber.api.java.pl.Wtedy;
import cucumber.api.java.pl.Zakładając;

import static org.junit.Assert.assertEquals;

public class OptimalItinerarySteps {

    private ItineraryService itineraryService;
    List<LocalTime> trainsTimes;
    InterinaryService interinaryService;

    @Zakładając("^pociągi linii \"(.*)\" z \"(.*)\" odjeżdżają ze stacji \"(.*)\" do \"(.*)\" o$")
    public void givenArrivingTrains(String line, String lineStart, String departure, String destination,
    @Transform(JodaLocalTimeConverter.class) List<LocalTime> departureTimes) {
        TimetableService timetableService = new InMemoryTimetableService();
        itineraryService = new ItineraryServiceImplementation(new InMemoryTimetableService());
        interinaryService = new InterinaryServiceImpl(timetableService);
    }

    @Gdy("^chcę podróżować z \"([^\"]*)\" do \"([^\"]*)\" o (.*)$")
    public void whenIWantToTravel(String departure, String destination,
    @Transform(JodaLocalTimeConverter.class) LocalTime startTime) {
        trainsTimes = itineraryService.findNextDepartures(departure, destination, startTime);
    }

    @Wtedy("^powinienem uzyskać informację o pociągach o:$")
    public void shouldBeInformedAbout(@Transform(JodaLocalTimeConverter.class) List<LocalTime> expectedTrainTimes) {
        assertEquals(expectedTrainTimes, trainsTimes);
    }
}
