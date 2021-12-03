package bsu.mmf.kaminski.user_info_in_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import bsu.mmf.kaminski.user_info_in_db.entities.User;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user")
    List<User> getUsers();

    @Insert
    void insertUser(User user);

    @Insert
    void insertUsers(User... users);

    @Query("SELECT * FROM user WHERE first_name LIKE :search || '%' OR last_name LIKE :search || '%'")
    List<User> findCountOfUsers(String search);
}