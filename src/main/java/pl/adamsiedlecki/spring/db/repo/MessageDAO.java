package pl.adamsiedlecki.spring.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.adamsiedlecki.spring.db.entity.Message;

import java.util.List;

public interface MessageDAO extends JpaRepository<Message, Long> {

    List<Message> findAllByAuthor(String author);
}
