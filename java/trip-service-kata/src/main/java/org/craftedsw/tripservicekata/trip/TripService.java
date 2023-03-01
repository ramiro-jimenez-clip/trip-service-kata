package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;

public class TripService {

    private ITripDAO tripDAO;

    public TripService(ITripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }

    public List<Trip> getTripsByUser(User user, User loggedUser) throws UserNotLoggedInException {
        validateLoggedUser(loggedUser);
        return user.isFriend(loggedUser) ?
                tripDAO.findTripsByUser(user) :
                new ArrayList<Trip>();
    }

    private void validateLoggedUser(User loggedUser) {
        if (loggedUser == null) {
            throw new UserNotLoggedInException();
        }
    }

}
