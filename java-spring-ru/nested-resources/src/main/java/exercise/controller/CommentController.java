package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequestMapping("/posts")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/{postId}/comments")
    public Iterable<Comment> getCommentsByPostId(@PathVariable("postId") long postId) {
        var post = findPostOrThrow(postId);
        return commentRepository.findAllByPostId(post.getId());
    }

    private Post findPostOrThrow(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with " + id + " not found"));
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public Comment getCommentByIdAndPostId(@PathVariable long postId, @PathVariable long commentId) {
        var post = findPostOrThrow(postId);
        return findCommentByIdsOrThrow(commentId, post.getId());
    }

    private Comment findCommentByIdsOrThrow(long commentId, long postId) {
        return commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentId + " not found"));
    }

    @PostMapping("/{postId}/comments")
    public Comment createComment(@PathVariable long postId, @RequestBody Comment comment) {
        var post = findPostOrThrow(postId);
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    public Comment updateComment(@PathVariable long postId,
                                @PathVariable long commentId,
                                @RequestBody Comment comment) {
        var post = findPostOrThrow(postId);

        var tempComment = findCommentByIdsOrThrow(commentId, post.getId());
        tempComment.setContent(comment.getContent());
        return commentRepository.save(tempComment);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public String deleteComment(@PathVariable long postId, @PathVariable long commentId) {
        var post = findPostOrThrow(postId);

        var comment = findCommentByIdsOrThrow(commentId, post.getId());
        commentRepository.delete(comment);
        
        return "OK";
    }
}
