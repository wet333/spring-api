package online.awet.springapi.features.resume.services;

import online.awet.springapi.features.resume.models.Message;
import online.awet.springapi.features.resume.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messagesRepository;

    MessageService(MessageRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    public List<Message> getAllMessages() {
        return messagesRepository.findAll();
    }

    public Message getMessageById(String id) {
        return messagesRepository.findById(id).orElse(null);
    }

    public Message createMessage(Message newMessage) {
        return messagesRepository.save(newMessage);
    }

    public Message updateMessage(String id, Message newMessage) {
        Message dbMessage = messagesRepository.findById(id).orElse(null);
        if (dbMessage != null) {
            dbMessage.setMessage(newMessage.getMessage());
            return messagesRepository.save(dbMessage);
        }
        return null;
    }

    public Message deleteMessage(String id) {
        Message dbMessage = messagesRepository.findById(id).orElse(null);
        if (dbMessage != null) {
            messagesRepository.deleteById(id);
            return dbMessage;
        } else {
            return null;
        }
    }
}