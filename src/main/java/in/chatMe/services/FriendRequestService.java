package in.chatMe.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.chatMe.entity.FriendRequest;
import in.chatMe.repository.FriendRequestRepository;

@Service
public class FriendRequestService 
{

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    public boolean sendRequest(String sender, String receiver) 
    {
        if (friendRequestRepository.existsBySenderEmailAndReceiverEmailAndStatus(sender, receiver, FriendRequest.RequestStatus.PENDING))
        {
            return false; // Already requested
        }

        FriendRequest request = new FriendRequest();
        request.setSenderEmail(sender);
        request.setReceiverEmail(receiver);
        request.setStatus(FriendRequest.RequestStatus.PENDING);
        request.setSentAt(LocalDateTime.now());
        friendRequestRepository.save(request);
        return true;
    }

    public List<FriendRequest> getPendingRequests(String receiver) 
    {
        return friendRequestRepository.findByReceiverEmailAndStatus(receiver, FriendRequest.RequestStatus.PENDING);
    }

    public void respondToRequest(Long requestId, boolean accept, MyFriendService friendService)
    {
        FriendRequest request = friendRequestRepository.findById(requestId).orElse(null);
        if (request == null) return;

        if (accept) {
            request.setStatus(FriendRequest.RequestStatus.ACCEPTED);
            friendService.addFriend(request.getSenderEmail(), request.getReceiverEmail());
            friendService.addFriend(request.getReceiverEmail(), request.getSenderEmail()); // bidirectional
        } else {
            request.setStatus(FriendRequest.RequestStatus.REJECTED);
        }

        friendRequestRepository.save(request);
    }
}
