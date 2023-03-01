package org.craftedsw.tripservicekata.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void givenAFriendUserWhenIsFriendShouldReturnTrue() {
        User user1 = new User();
        User user2 = new User();

        user1.addFriend(user2);

        Assertions.assertTrue(user1.isFriend(user2));
    }

    @Test
    public void givenANotFriendUserWhenIsFriendShouldReturnTrue() {
        User user1 = new User();
        User user2 = new User();

        Assertions.assertFalse(user1.isFriend(user2));
    }
}
