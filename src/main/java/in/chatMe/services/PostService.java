package in.chatMe.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.chatMe.entity.Post;
import in.chatMe.repository.PostRepository;

@Service
public class PostService 
{
    @Autowired
    private PostRepository postRepository;

    public void savePost(Post post) 
    {
        post.setTimestamp(LocalDateTime.now());
        postRepository.save(post);
    }

    public List<Post> getAllPosts() 
    {
        return postRepository.findAllByOrderByTimestampDesc();
    }
}
