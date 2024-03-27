package com.example.demo.Rating;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRatingRepo extends JpaRepository<UserRating,Long> {

    User findUserRatingById(Long id);

    @Query("SELECT ur FROM UserRating ur " +
            "WHERE ur.ratingUser = ?1 AND ur.ratedUser = ?2")
    UserRating findUserRatingByRatingAndRated(User userRating, User userRated);

    @Query("SELECT ur FROM UserRating ur " +
            "WHERE ur.ratingUser.id = ?1 AND ur.ratedUser.id = ?2")
    UserRating findRatingByOnlineUse(Long onlineUserId,Long ratedUserId);
}
