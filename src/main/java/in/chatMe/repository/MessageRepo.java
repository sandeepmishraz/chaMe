package in.chatMe.repository;

import in.chatMe.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long>
{
    List<Message> findBySenderAndReceiverOrSenderAndReceiverOrderByDateAsc(
    String sender1, String receiver1, String sender2, String receiver2);
    @Query("SELECT m FROM Message m WHERE (m.sender = :user1 AND m.receiver = :user2) OR (m.sender = :user2 AND m.receiver = :user1) ORDER BY m.date")
    List<Message> findBySenderAndReceiver(@Param("user1") String user1, @Param("user2") String user2);

}
