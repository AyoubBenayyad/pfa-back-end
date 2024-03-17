package com.example.demo.Vote;

import com.example.demo.Annonce.Annonce;
import com.example.demo.Annonce.AnnonceRepo;
import com.example.demo.Exceptions.DuplicateResource;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteService implements IVoteService{

    private final UserRepository userRepository;
    private final AnnonceRepo annonceRepository;
    private final VoteRepository voteRepository;
    @Override
    public void UpVote(Long PostId, Long UserId) throws Exception {

        Optional<User> userOpt = userRepository.findById(UserId);
        Optional<Annonce> annonceOpt = annonceRepository.findById(PostId);

        if (userOpt.isEmpty() || annonceOpt.isEmpty() ) {
            throw new Exception("user doesnt exist");
        }
        User user= userOpt.get();
        Annonce annonce = annonceOpt.get();

        if(voteRepository.hasUserUpvotedPost(user.getId(),annonce.getId())){
            throw new DuplicateResource("user already UpVotedPost");
        }
        if(voteRepository.hasUserDownvotedPost(user.getId(),annonce.getId())){
            Vote DownVote = voteRepository.findDownVoteByUserIdAndPost(user.getId(), annonce.getId());
            DownVote.setType(Type_Vote.Up_Vote);
            voteRepository.save(DownVote);
            annonce.setMark(annonce.getMark()+2);

        }
        else{
            Vote vote= new Vote(user,annonce,Type_Vote.Up_Vote);

            annonce.setMark(annonce.getMark()+1);

            voteRepository.save(vote);
        }


    }

    @Override
    public void DownVote(Long PostId, Long UserId) throws Exception {

        Optional<User> userOpt = userRepository.findById(UserId);
        Optional<Annonce> annonceOpt = annonceRepository.findById(PostId);

        if (userOpt.isEmpty() || annonceOpt.isEmpty()) {
            throw new Exception("user doesnt exist");
        }
        User user= userOpt.get();
        Annonce annonce = annonceOpt.get();

        if(voteRepository.hasUserDownvotedPost(user.getId(),annonce.getId())){
            throw new DuplicateResource("user already DownVotedPost");
        }
        if(voteRepository.hasUserUpvotedPost(user.getId(),annonce.getId())){
            Vote UpVote = voteRepository.findUpVoteByUserIdAndPost(user.getId(), annonce.getId());
            UpVote.setType(Type_Vote.Down_Vote);

            voteRepository.save(UpVote);
            annonce.setMark(annonce.getMark()-2);

        }
        else{
            Vote vote= new Vote(user,annonce,Type_Vote.Down_Vote);

            annonce.setMark(annonce.getMark()-1);

            voteRepository.save(vote);
        }

    }

    @Override
    public void RemoveUpVote(Long PostId, Long UserId) throws Exception {
        Optional<User> userOpt = userRepository.findById(UserId);
        Optional<Annonce> annonceOpt = annonceRepository.findById(PostId);

        if (userOpt.isEmpty() || annonceOpt.isEmpty()) {
            throw new NoSuchElementException("user doesnt exist");
        }
        User user= userOpt.get();
        Annonce annonce = annonceOpt.get();
        if(voteRepository.hasUserUpvotedPost(user.getId(),annonce.getId())){

            Vote UpVote = voteRepository.findUpVoteByUserIdAndPost(user.getId(), annonce.getId());
            voteRepository.delete(UpVote);
            annonce.setMark(annonce.getMark()-1);


        }else {
            throw new NoSuchElementException("UpVote doesnt exit");
        }

    }

    @Override
    public void RemoveDownVote(Long PostId, Long UserId) throws Exception {
        Optional<User> userOpt = userRepository.findById(UserId);
        Optional<Annonce> annonceOpt = annonceRepository.findById(PostId);

        if (userOpt.isEmpty() || annonceOpt.isEmpty()) {
            throw new NoSuchElementException("user doesnt exist");
        }
        User user= userOpt.get();
        Annonce annonce = annonceOpt.get();
        if(voteRepository.hasUserDownvotedPost(user.getId(),annonce.getId())){
            Vote DownVote = voteRepository.findDownVoteByUserIdAndPost(user.getId(), annonce.getId());
            voteRepository.delete(DownVote);
            annonce.setMark(annonce.getMark()+1);
        }else {
            throw new NoSuchElementException("DownVote doesnt exit");
        }
    }

    @Override
    public VoteResponseDto isVoted(Long postId, Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Annonce> annonceOpt = annonceRepository.findById(postId);

        if (userOpt.isEmpty() || annonceOpt.isEmpty()) {
            throw new NoSuchElementException("user doesnt exist");
        }
        User user= userOpt.get();
        Annonce annonce = annonceOpt.get();

        if(!voteRepository.hasUservotedPost(userId,postId)){
            return VoteResponseDto.builder().Type("NoVote").mark(annonce.getMark()).build();
        }
        if(voteRepository.hasUserUpvotedPost(userId,postId)){
            return VoteResponseDto.builder().Type("UpVote").mark(annonce.getMark()).build();


        }
        if(voteRepository.hasUserDownvotedPost(userId,postId)){
            return VoteResponseDto.builder().Type("DownVote").mark(annonce.getMark()).build();

        }

        return VoteResponseDto.builder().Type("NoVote").mark(annonce.getMark()).build();
    }
}
