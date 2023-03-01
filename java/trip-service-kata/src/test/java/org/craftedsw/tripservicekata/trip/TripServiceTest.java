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
    public void givenAnUserAndANullLoggedUser_WhenGetTripsByUser_ShouldThrowUserNotLoggedInException() {
        givenAnUser();

        Assertions.assertThrows(UserNotLoggedInException.class, () -> whenGetTripsByUser());
    }

    @Test
    public void givenAnUserWithoutFriendsAndALoggedUser_WhenGetTripsByUser_ShouldReturnAnEmptyList() {
        givenAnUser();
        givenALoggedUser();

        whenGetTripsByUser();

        shouldReturnAnEmptyList();
    }

    @Test
    public void givenAnUserWithTripsAndFriendsAndALoggedUser_WhenGetTripsByUser_ShouldReturnATripList() {
        givenALoggedUser();
        givenAnUser(List.of(loggedUser));
        setUpTripDAO();

        whenGetTripsByUser();

        shouldReturnATripList();
    }

    private void givenAnUser() {
        givenAnUser(List.of());
    }

    private void givenAnUser(List<User> friends) {
        user = new User();
        friends.forEach( (friend) -> user.addFriend(friend));
    }

    private void setUpTripDAO() {
        expectedTrips = List.of(TRIP_1, TRIP_2);
        Mockito.when(tripDAO.findTripsByUser(user)).thenReturn(expectedTrips);
    }

    private void givenALoggedUser() {
        loggedUser = new User();
    }

    private void whenGetTripsByUser() {
        actualTrips = tripService.getTripsByUser(user, loggedUser);
    }

    private void shouldReturnAnEmptyList() {
        Assertions.assertIterableEquals(List.of(), actualTrips);
    }

    private void shouldReturnATripList() {
        Assertions.assertIterableEquals(expectedTrips, actualTrips);
    }
}
