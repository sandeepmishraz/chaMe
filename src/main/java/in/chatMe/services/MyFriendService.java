package in.chatMe.services;

import in.chatMe.entity.MyFriend;
import in.chatMe.repository.MyFriendRepo;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyFriendService {

	@Autowired
	private MyFriendRepo myFriendRepo;

	public void addFriend(String currentUserEmail, String friendEmail) {
		if (!myFriendRepo.existsByCurrentUserEmailAndFriendEmail(currentUserEmail, friendEmail)) {
			MyFriend friendship = new MyFriend(currentUserEmail, friendEmail);
			myFriendRepo.save(friendship);
		}
	}

	public List<MyFriend> getMyFriends(String userEmail) {
		return myFriendRepo.findByCurrentUserEmail(userEmail);
	}

	@Transactional
	public void unfriend(String currentUserEmail, String friendEmail) {
		myFriendRepo.deleteByCurrentUserEmailAndFriendEmail(currentUserEmail, friendEmail);
		myFriendRepo.deleteByCurrentUserEmailAndFriendEmail(friendEmail, currentUserEmail); // If bidirectional
	}
	
	public List<String> getMyFriendEmails(String currentUserEmail) 
	{
        return myFriendRepo.findByCurrentUserEmail(currentUserEmail)
                .stream()
                .map(MyFriend::getFriendEmail)
                .collect(Collectors.toList());
    }
	
	public List<String> getFriendEmails(String currentUserEmail) 
	{
        return myFriendRepo.findFriendEmailsByCurrentUserEmail(currentUserEmail);
    }
}




