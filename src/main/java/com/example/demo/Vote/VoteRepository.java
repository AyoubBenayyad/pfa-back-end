package com.example.demo.Vote;

import com.example.demo.Annonce.Photos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteRepository extends JpaRepository<Vote,Long> {
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END " +
            "FROM Vote v WHERE v.user.id = :userId AND v.post.Id = :postId AND v.type = 'Up_Vote'")
    boolean hasUserUpvotedPost(@Param("userId") Long userId, @Param("postId") Long postId);

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END " +
            "FROM Vote v WHERE v.user.id = :userId AND v.post.Id = :postId")
    boolean hasUservotedPost(@Param("userId") Long userId, @Param("postId") Long postId);
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END " +
            "FROM Vote v WHERE v.user.id = :userId AND v.post.Id = :postId AND v.type = 'Down_Vote'")
    boolean hasUserDownvotedPost(@Param("userId") Long userId, @Param("postId") Long postId);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId AND v.post.Id = :postId AND v.type = 'Up_Vote'")
    Vote findUpVoteByUserIdAndPost(@Param("userId") Long userId,@Param("postId") Long postId);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId AND v.post.Id = :postId AND v.type = 'Down_Vote'")
    Vote findDownVoteByUserIdAndPost(@Param("userId") Long userId,@Param("postId") Long postId);



}
