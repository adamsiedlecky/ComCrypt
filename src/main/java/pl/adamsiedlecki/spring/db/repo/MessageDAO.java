package pl.adamsiedlecki.spring.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.adamsiedlecki.spring.db.entity.Message;

import java.util.List;

public interface MessageDAO extends JpaRepository<Message, Long> {

    List<Message> findAllByAuthor(String author);

    @Query("SELECT m FROM Message m WHERE m.userProvidedId=?1")
    Message findByUserProvidedId(String id);
}
