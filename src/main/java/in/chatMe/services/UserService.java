package in.chatMe.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.chatMe.entity.Account;
import in.chatMe.entity.MyFriend;
import in.chatMe.repository.MyFriendRepo;
import in.chatMe.repository.UserRepo;

@Service
public class UserService 
{
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private MyFriendRepo friendRepo;
	
	 public boolean isEmailAlreadyRegistered(String email) {
	        return userRepo.findByEmail(email) != null;
	    }

	    public void registerUser(Account account) {
	        // Check if email already exists
	        if (isEmailAlreadyRegistered(account.getEmail())) {
	            throw new RuntimeException("Email already exists");
	        }

	        // If email is unique, save the user
	        userRepo.save(account);
	    }

	public Account loginUser(String email, String password) 
	{
		Account validaAccount = userRepo.findByEmail(email);
		
		if(validaAccount != null && validaAccount.getPassword().equals(password))
		{
			return validaAccount;
		}
		return null;
	}
	public Account findUser(String email) 
	{
		return userRepo.findByEmail(email);
	}
	public List<Account> getAllUsers()
	{
		return userRepo.findAll();
	}
	public List<Account> getFriendsOf(String userEmail) {
	    List<MyFriend> relations = friendRepo.findByCurrentUserEmail(userEmail);
	    List<Account> friends = new ArrayList<>();
	    for (MyFriend rel : relations) {
	        Account friend = userRepo.findByEmail(rel.getFriendEmail());
	        if (friend != null) {
	            friends.add(friend);
	        }
	    }
	    return friends;
	}
	
	public List<Account> getAllUsersExcept(String currentUserEmail) {
        return userRepo.findByEmailNot(currentUserEmail);
    }

}
