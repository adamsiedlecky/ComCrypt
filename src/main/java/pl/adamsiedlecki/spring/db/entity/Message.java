package pl.adamsiedlecki.spring.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String userProvidedId;

    private String content;

    private String author;

    private LocalDateTime creationTime;

    public Message(String content, String author, String userProvidedId) {
        this.content = content;
        this.author = author;
        this.userProvidedId = userProvidedId;
        this.creationTime = LocalDateTime.now();
    }

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}
