package in.chatMe.services;

import in.chatMe.entity.Message;
import in.chatMe.repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepo messageRepository;

    public List<Message> getConversation(String user1, String user2)
    {
        return messageRepository.findBySenderAndReceiverOrSenderAndReceiverOrderByDateAsc(
            user1, user2, user2, user1);
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }
    
    public List<Message> getMessages()
    {
    	return messageRepository.findAll();
    }
    public Message getMessageById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }


}
