package tech.bts.cardgame.repository;

import org.springframework.stereotype.Repository;
import tech.bts.cardgame.model.User;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private Map<String, User> userMap;

    public UserRepository() {
        userMap = new HashMap<>();
    }

    public void create(User user) {
        userMap.put(user.getName(), user);
    }
}
