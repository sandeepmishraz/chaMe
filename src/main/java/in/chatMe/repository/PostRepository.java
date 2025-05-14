package in.chatMe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.chatMe.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByTimestampDesc();
    List<Post> findByUserEmail(String userEmail);
}
