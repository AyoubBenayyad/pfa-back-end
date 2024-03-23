package com.example.demo.Comments;

import com.example.demo.Annonce.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Long> {

    @Query("SELECT c FROM Comment c WHERE c.postCommented.Id = :PostId ORDER BY c.postedAt DESC ")
    List<Comment> findCommentsByPostId(@Param("PostId") Long PostId);


}
