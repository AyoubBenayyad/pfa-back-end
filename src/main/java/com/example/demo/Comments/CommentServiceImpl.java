package com.example.demo.Comments;

import com.example.demo.Annonce.Annonce;
import com.example.demo.Annonce.AnnonceRepo;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CommentServiceImpl implements IcommentService{

    @Autowired
    AnnonceRepo annonceRepo;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepo commentRepo;

    @Override
    public void addComment(CommentRequest request, String email_commentingUser) {
        Optional<Annonce> annonceOPT = annonceRepo.findById(request.getIdCommentedPost());
        Optional<User> UserOPT = userRepository.findByEmail(email_commentingUser);

        if(annonceOPT.isEmpty() || UserOPT.isEmpty()){
            throw  new NoSuchElementException(" annonce or user dont exist");
        }
        Annonce annonce=annonceOPT.get();
        User user=UserOPT.get();
        Comment comment = new Comment();
        comment.setUserCommenting(user);
        comment.setPostCommented(annonce);
        comment.setComment_Content(request.getComment());
        comment.setPostedAt(new Date());

        user.getComments().add(comment);
        annonce.getComments().add(comment);

        annonceRepo.save(annonce);
        userRepository.save(user);
        commentRepo.save(comment);
    }

    @Override
    public List<CommentResponse> getPostComments(Long postId) throws Exception {
        List<Comment> commentsByPostId = commentRepo.findCommentsByPostId(postId);
        List<CommentResponse> comments=new ArrayList<>();

        for(Comment c :commentsByPostId ){
            comments.add(CommentResponse.builder()
                    .commentId(c.Id)
                    .commentText(c.Comment_Content)
                            .commentDate(convertDateFormat(c.getPostedAt()))
                            .commentUsername(c.userCommenting.getFirstname()+" "+c.userCommenting.getLastname())
                            .commentImg(c.userCommenting.getImageUrl())
                            .userId(c.userCommenting.getId())
                    .build());
        }

        return comments;
    }

    @Override
    public void deleteComment(Long commentId,Long DeleterId) throws Exception {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment with id " + commentId + " does not exist"));

        Long PostOwnerId=comment.getPostCommented().getUserPosting().getId();

        Long CommentPosterId= comment.getUserCommenting().getId();
        if(CommentPosterId == DeleterId || PostOwnerId == DeleterId ){
            commentRepo.delete(comment);
        }else{
            throw new Exception(" you dont have the right to delete this comment");
        }


    }

    public static String convertDateFormat(Date input) throws Exception {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");

        String formattedString = outputFormat.format(input);
        return formattedString;
    }


}
