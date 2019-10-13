package pl.adamsiedlecki.spring.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.spring.db.entity.Message;
import pl.adamsiedlecki.spring.db.repo.MessageDAO;

import java.util.List;

@Service
public class MessageService {

    private MessageDAO messageDAO;

    @Autowired
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public void save(Message message){
        messageDAO.save(message);
    }

    public Message getById(Long id){
        return messageDAO.getOne(id);
    }

    public List<Message> getByAuthor(String author){
        return messageDAO.findAllByAuthor(author);
    }

}
