package de.hsrm.mi.swtpro.pflamoehus.user.userService;

import java.util.List;

import de.hsrm.mi.swtpro.pflamoehus.user.User;

public interface UserService {

    List<User> allUsers();
    User searchUserWithEmail(String email);
    User searchUserWithId(long id);
    User editUser(User editedUser);
    void deleteUser(long id);
    User registerUser(User email);

}
