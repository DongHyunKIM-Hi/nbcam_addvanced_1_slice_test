package org.example.nbcam_addvanced_1.domain.user.repository;


import java.util.Optional;
import org.example.nbcam_addvanced_1.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.age = :age WHERE u.username = :username")
    void updateAge(@Param("username") String username, @Param("age") int age);

    @Modifying
    @Query("DELETE FROM User u WHERE u.username = :username")
    void deleteByUsername(@Param("username") String username);
}
