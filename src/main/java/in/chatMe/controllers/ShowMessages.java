package in.chatMe.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import in.chatMe.entity.Account;
import in.chatMe.entity.Message;
import in.chatMe.repository.MessageRepo;
import in.chatMe.services.MessageService;
import jakarta.servlet.http.HttpSession;

@RestController
public class ShowMessages {
	@Autowired
	private MessageRepo messageRepo;

	@GetMapping("/api/messages/{friendEmail}")
	public List<Message> getMessagesWithFriend(@PathVariable String friendEmail, HttpSession session) {
		String currentUser = (String) session.getAttribute("UserEmail");
		return messageRepo.findBySenderAndReceiver(currentUser, friendEmail);
	}

}
