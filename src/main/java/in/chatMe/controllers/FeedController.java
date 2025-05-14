package in.chatMe.controllers;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import in.chatMe.entity.Post;
import in.chatMe.repository.PostRepository;
import in.chatMe.services.PostService;
import jakarta.servlet.http.HttpSession;

@Controller
public class FeedController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;
    
    @PostMapping("/postFeed")
    public String postFeed(@RequestParam("content") String content,
                           @RequestParam("image") MultipartFile image,
                           HttpSession session) throws IOException 
    {
        String email = (String) session.getAttribute("UserEmail");
        if (email == null) return "redirect:/loginPage";

        Post post = new Post();
        post.setUserEmail(email);
        post.setContent(content);
        if (!image.isEmpty()) 
        {
            post.setImage(image.getBytes());
        }

        postService.savePost(post);
        return "redirect:/feed";
    }

    

    @GetMapping("/feed")
    public String getFeed(Model model) {
        List<Post> posts = postService.getAllPosts();

        List<Post> encodedPosts = posts.stream().map(post -> {
            if (post.getImage() != null) {
                post.setContentBase64(Base64.getEncoder().encodeToString(post.getImage()));
            }
            return post;
        }).collect(Collectors.toList());

        model.addAttribute("posts", encodedPosts);
        return "feed";
    }
    
    @PostMapping("/deletePost/{id}")
    public String deletePost(@PathVariable Long id, HttpSession session) {
        String currentUser = (String) session.getAttribute("UserEmail");
        Post post = postRepository.findById(id).orElse(null);
        if (post != null && post.getUserEmail().equals(currentUser)) {
            postRepository.delete(post);
        }
        return "redirect:/myPosts";
    }

    @GetMapping("/myPosts")
    public String myPosts(Model model, HttpSession session) {
        String userEmail = (String) session.getAttribute("UserEmail");
        List<Post> userPosts = postRepository.findByUserEmail(userEmail);

        // Convert image bytes to base64 for each post
        userPosts.forEach(post -> {
            if (post.getImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(post.getImage());
                post.setContentBase64(base64Image);
            }
        });

        model.addAttribute("posts", userPosts);
        return "my-posts";
    }



}
