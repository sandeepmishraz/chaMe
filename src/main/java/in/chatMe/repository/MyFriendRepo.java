package in.chatMe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.chatMe.entity.MyFriend;

@Repository
public interface MyFriendRepo extends JpaRepository<MyFriend, Long>
{
	List<MyFriend> findByCurrentUserEmail(String currentUserEmail); // ✅ Correct field name
	
    boolean existsByCurrentUserEmailAndFriendEmail(String currentUserEmail, String friendEmail); // ✅ FIXED
    
    void deleteByCurrentUserEmailAndFriendEmail(String currentUserEmail, String friendEmail);
    
    @Query("SELECT f.friendEmail FROM MyFriend f WHERE f.currentUserEmail = :currentUserEmail")
    List<String> findFriendEmailsByCurrentUserEmail(String currentUserEmail);

}
