package in.chatMe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import in.chatMe.entity.MyFriend;
import in.chatMe.services.MyFriendService;
import in.chatMe.services.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class FriendController {

    @Autowired
    private MyFriendService myFriendService;

    @Autowired
    private UserService userService;
    
    @PostMapping("/addFriend")
    public String addFriend(@RequestParam("friendEmail") String friendEmail, HttpSession session) {
        String currentUserEmail = (String) session.getAttribute("UserEmail");

        if (currentUserEmail == null || friendEmail == null || friendEmail.isEmpty()) {
            return "redirect:/loginPage"; // or error page
        }

        myFriendService.addFriend(currentUserEmail, friendEmail);

        return "redirect:/myFriends"; // After adding, redirect to friend list
    }

    @GetMapping("/myFriends")
    public String showFriendsPage(HttpSession session, Model model) {
        String currentUserEmail = (String) session.getAttribute("UserEmail");
        if (currentUserEmail == null) 
        {
            return "redirect:/loginPage";
        }

        List<MyFriend> friends = myFriendService.getMyFriends(currentUserEmail);
        model.addAttribute("friends", friends);
        return "myFriends"; // maps to myFriends.html
    } 
    
    @PostMapping("/unfriend")
    public String unfriend(@RequestParam("friendEmail") String friendEmail, HttpSession session) {
        String currentUserEmail = (String) session.getAttribute("UserEmail");
        if (currentUserEmail == null || friendEmail == null || friendEmail.isEmpty()) {
            return "redirect:/loginPage";
        }

        //myFriendService.unfriend(currentUserEmail, friendEmail);
        myFriendService.unfriend(currentUserEmail, friendEmail);
        session.setAttribute("unfriendSuccess", true); // Add this
        return "redirect:/myFriends";
    }
    @PostMapping("/clearUnfriendToast")
    public void clearUnfriendToast(HttpSession session) {
        session.removeAttribute("unfriendSuccess");
    }

}










