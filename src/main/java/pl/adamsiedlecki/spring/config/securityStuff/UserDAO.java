package pl.adamsiedlecki.spring.config.securityStuff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<CommCryptUser, Long> {

    @Query("SELECT user FROM CommCryptUser user WHERE username=?1 ")
    Optional<CommCryptUser> getByUsername(String username);

}
