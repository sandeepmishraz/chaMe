package in.chatMe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.chatMe.entity.FriendRequest;
import in.chatMe.services.FriendRequestService;
import in.chatMe.services.MyFriendService;
import jakarta.servlet.http.HttpSession;

@Controller
public class FriendRequestController {

    @Autowired
    private FriendRequestService friendRequestService;

    @Autowired
    private MyFriendService myFriendService;

    @PostMapping("/sendRequest")
    public String sendRequest(@RequestParam("receiverEmail") String receiverEmail, HttpSession session)
    {
        String senderEmail = (String) session.getAttribute("UserEmail");
        if (senderEmail != null && !senderEmail.equals(receiverEmail)) {
            friendRequestService.sendRequest(senderEmail, receiverEmail);
        }
        return "redirect:/allUsers";
    }

    @GetMapping("/friendRequests")
    public String viewRequests(HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute("UserEmail");
        List<FriendRequest> requests = friendRequestService.getPendingRequests(userEmail);
        model.addAttribute("requests", requests);
        return "friendRequests"; // View to show requests
    }

    @PostMapping("/respondRequest")
    public String respondRequest(@RequestParam("requestId") Long requestId,
                                 @RequestParam("action") String action) {
        boolean accept = "accept".equalsIgnoreCase(action);
        friendRequestService.respondToRequest(requestId, accept, myFriendService);
        return "redirect:/friendRequests";
    }
}

