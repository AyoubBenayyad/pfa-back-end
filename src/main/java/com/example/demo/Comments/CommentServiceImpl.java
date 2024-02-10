package com.example.demo.Comments;

import com.example.demo.Annonce.Annonce;
import com.example.demo.Annonce.AnnonceRepo;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Annonce annonce = annonceRepo.findAnnonceById(request.getIdCommentedPost());
        Optional<User> User = userRepository.findByEmail(email_commentingUser);
        User UserCommenting = User.orElse(null);

        Comment comment = new Comment();
        comment.setUserCommenting(UserCommenting);
        comment.setPostCommented(annonce);
        comment.setComment_Content(request.getComment());

        UserCommenting.getComments().add(comment);
        annonce.getComments().add(comment);

        annonceRepo.save(annonce);
        userRepository.save(UserCommenting);
        commentRepo.save(comment);
    }
}
