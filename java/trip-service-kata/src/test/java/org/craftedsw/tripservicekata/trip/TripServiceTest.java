package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class TripServiceTest {

    private static final Trip TRIP_1 = new Trip();
    private static final Trip TRIP_2 = new Trip();

    private User user;
    private User loggedUser;

    private ITripDAO tripDAO;

    private TripService tripService;
    private List<Trip> actualTrips;
    private List<Trip> expectedTrips;

    @BeforeEach
    public void setUp() {
        tripDAO = Mockito.mock(ITripDAO.class);
        tripService = new TripService(tripDAO);
    }

    @Test
    public void givenAUserAndALoggedUserWhenGetTripsByUserShouldThrowExceptionIfLoggedUserIsNull() {
        givenAnUser();

        Assertions.assertThrows(
                UserNotLoggedInException.class,
                this::whenGetTripsByUser);
    }

    @Test
    public void givenAnUserNotFriendOfLoggedUserWhenGetTripsByUserShouldReturnAnEmptyList() {
        givenALoggedUser();
        givenAnUser();

        whenGetTripsByUser();

        shouldReturnAnEmptyList();
    }

    @Test
    public void givenAnUserFriendOfLoggedUserWithoutTripsWhenGetTripsByUserShouldReturnAnEmptyList() {
        givenALoggedUser();
        givenEmptyExpectedTrips();
        givenAnUser(List.of(loggedUser), expectedTrips);
        setUpTripDAO();

        whenGetTripsByUser();

        shouldReturnAnEmptyList();
    }

    @Test
    public void givenAnUserFriendOfLoggedUserWithTripsWhenGetTripsByUserShouldReturnUserTrips() {
        givenALoggedUser();
        givenExpectedTrips(List.of(TRIP_1, TRIP_2));
        givenAnUser(List.of(loggedUser), expectedTrips);
        setUpTripDAO();

        whenGetTripsByUser();

        shouldReturnExpectedTrips();
    }


    private void givenAnUser() {
        givenAnUser(List.of(), List.of());
    }

    private void givenAnUser(List<User> friends, List<Trip> trips) {
        user = new User();
        friends.forEach((friend) -> user.addFriend(friend));
        trips.forEach((trip) -> user.addTrip(trip));
    }

    private void givenEmptyExpectedTrips() {
        expectedTrips = List.of();
    }

    private void givenExpectedTrips(List<Trip> trips) {
        expectedTrips = trips;
    }

    private void givenALoggedUser() {
        loggedUser = new User();
    }

    private void setUpTripDAO() {
        Mockito.when(tripDAO.findTripsByUser(user)).thenReturn(expectedTrips);
    }

    private void whenGetTripsByUser() {
        actualTrips = tripService.getTripsByUser(user, loggedUser);
    }

    private void shouldReturnAnEmptyList() {
        Assertions.assertIterableEquals(List.of(), actualTrips);
    }

    private void shouldReturnExpectedTrips() {
        Assertions.assertIterableEquals(expectedTrips, actualTrips);
    }

}
