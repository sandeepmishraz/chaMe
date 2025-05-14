package in.chatMe.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderEmail;
    private String receiverEmail;

    @Enumerated(EnumType.STRING)
    private RequestStatus status; // PENDING, ACCEPTED, REJECTED

    private LocalDateTime sentAt;


	public FriendRequest(Long id, String senderEmail, String receiverEmail, RequestStatus status,
			LocalDateTime sentAt) {
		super();
		this.id = id;
		this.senderEmail = senderEmail;
		this.receiverEmail = receiverEmail;
		this.status = status;
		this.sentAt = sentAt;
	}

	public FriendRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

    public enum RequestStatus {
        PENDING, ACCEPTED, REJECTED
    }

    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public LocalDateTime getSentAt() {
		return sentAt;
	}

	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}
}
