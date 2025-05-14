package in.chatMe.controllers;

import in.chatMe.entity.Message;
import in.chatMe.services.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/chat/{friendEmail}")
    public String openChat(@PathVariable String friendEmail, HttpSession session, Model model) {
        String currentUserEmail = (String) session.getAttribute("UserEmail"); // ⬅️ check session here

        if (currentUserEmail == null) {
            return "redirect:/loginPage"; // session expired or not set
        }

        List<Message> messages = messageService.getConversation(currentUserEmail, friendEmail);

        model.addAttribute("messages", messages);
        model.addAttribute("friendEmail", friendEmail);
        model.addAttribute("currentUserEmail", currentUserEmail); // ✅ this must be set

        return "message"; // replace with your actual HTML view
    }

    
   


    @PostMapping("/sendMessage")
    public String sendMessage(@RequestParam String receiver,
                              @RequestParam String content,
                              @RequestParam(required = false) MultipartFile file,
                              HttpSession session) throws IOException {
        String sender = (String) session.getAttribute("UserEmail");

        if (sender == null) {
            return "login";
        }

        Message msg = new Message();
        msg.setSender(sender);
        msg.setReceiver(receiver);
        msg.setContent(content);
        msg.setDate(LocalDateTime.now());
        messageService.saveMessage(msg);

        return "redirect:/chat/" + receiver;
    }  
 // Add this method to MessageController
    @PostMapping("/deleteMessage/{id}")
    public String deleteMessage(@PathVariable Long id, HttpSession session) 
    {
        String currentUserEmail = (String) session.getAttribute("UserEmail");

        if (currentUserEmail == null) {
            return "redirect:/loginPage";
        }

        Message message = messageService.getMessageById(id);
        if (message != null && message.getSender().equals(currentUserEmail)) {
            messageService.deleteMessage(id);  // Call the service to delete the message
        }

        return "redirect:/chat/" + message.getReceiver();
    }

}








