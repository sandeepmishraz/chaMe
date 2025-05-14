package in.chatMe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.chatMe.entity.FriendRequest;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    List<FriendRequest> findByReceiverEmailAndStatus(String receiverEmail, FriendRequest.RequestStatus status);

    boolean existsBySenderEmailAndReceiverEmailAndStatus(String senderEmail, String receiverEmail, FriendRequest.RequestStatus status);

    List<FriendRequest> findBySenderEmailOrReceiverEmail(String senderEmail, String receiverEmail);
}

