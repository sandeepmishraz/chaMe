package in.chatMe.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import in.chatMe.entity.Account;
import in.chatMe.entity.Message;
import in.chatMe.entity.MyFriend;
import in.chatMe.services.MessageService;
import in.chatMe.services.MyFriendService;
import in.chatMe.services.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {
	@Autowired
	private UserService userService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private MyFriendService myFriendService;

	@GetMapping("/")
	public String signUpPage(Model model) {
		model.addAttribute("account", new Account());
		return "signup";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute Account account, Model model) 
	{
	    try 
	    {
	        userService.registerUser(account);
	        return "redirect:/loginPage"; // Redirect to login if registration is successful
	    } 
	    catch (RuntimeException e) 
	    {
	        // Handle the case where email already exists
	        model.addAttribute("error", "Email already exists. Please choose a different email.");
	        return "signup"; // Return to the signup page with an error message
	    }
	}


	@GetMapping("/loginPage")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/profile")
	public ModelAndView userProfile(HttpSession session, Model model) {
		return new ModelAndView("profile");
	}
//	@GetMapping("/allUsers")
//    public ModelAndView allUsers(HttpSession session, Model model) 
//	{
//            List<Account> account = userService.getAllUsers();
//    		return new ModelAndView("allusers", "account", account);
//    }

	@GetMapping("/allUsers")
	public String showAllUsers(HttpSession session, Model model) {
	    String currentUserEmail = (String) session.getAttribute("UserEmail");
	    if (currentUserEmail == null) {
	        return "redirect:/loginPage";
	    }

	    List<String> friendEmails = myFriendService.getFriendEmails(currentUserEmail);

	    // Exclude current user and friends from the list
	    List<Account> allUsers = userService.getAllUsers()
	            .stream()
	            .filter(user -> !user.getEmail().equals(currentUserEmail)) // don't show self
	            .filter(user -> !friendEmails.contains(user.getEmail()))   // don't show friends
	            .collect(Collectors.toList());

	    model.addAttribute("account", allUsers);
	    return "allUsers";
	}



	@PostMapping("/login_user")
	public String loginUser(@ModelAttribute Account ac, HttpSession session) {
		Account validAc = userService.loginUser(ac.getEmail(), ac.getPassword());
		if (validAc != null) {
			session.setAttribute("UserName", validAc.getFirstName());
			session.setAttribute("UserEmail", validAc.getEmail());
			session.setAttribute("UserId", validAc.getId());
			return "redirect:/feed";
		}
		return "redirect:/loginPage";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // Clear all session data
		return "redirect:/"; // Redirect to login or home page
	}

}
