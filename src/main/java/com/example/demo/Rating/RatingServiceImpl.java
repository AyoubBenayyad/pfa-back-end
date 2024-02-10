package com.example.demo.Rating;


import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingServiceImpl implements IRatingService{

    private UserRepository userRepository;
    private UserRatingRepo userRatingRepo;

    public RatingServiceImpl(UserRepository userRepository,UserRatingRepo userRatingRepo) {
        this.userRepository = userRepository;
        this.userRatingRepo = userRatingRepo;
    }

    @Override
    public void rateUser(RatingRequest request, String email_ratingUser) {
        Optional<User> rated = userRepository.findById(request.getId_userRated());
        Optional<User> rating = userRepository.findByEmail(email_ratingUser);

        User userRated = rated.orElse(null);
        User userRating = rating.orElse(null);

        UserRating userRating1 = new UserRating(userRating,userRated,request.getRate());

        float numberOfStars = 0;
        switch (request.getRate()) {
            case ONE_STAR:
                numberOfStars = 1;
                break;
            case TWO_STARS:
                numberOfStars = 2;
                break;
            case THREE_STARS:
                numberOfStars = 3;
                break;
            case FOUR_STARS:
                numberOfStars = 4;
                break;
            case FIVE_STARS:
                numberOfStars = 5;
                break;
            default:
                throw new InvalidInputException("wrong rating");
        }

        UserRating updateUserRating =
                userRatingRepo.findUserRatingByRatingAndRated(userRating,userRated);

        if(updateUserRating!=null){
            userRated.setRate(numberOfStars,true);
            updateUserRating.setRating(request.getRate());
            userRatingRepo.save(updateUserRating);
        }else{
            userRated.setRate(numberOfStars,false);
            userRated.getRatedUsers().add(userRating1);
            userRating.getRatedUsers().add(userRating1);

            userRepository.save(userRating);
            userRatingRepo.save(userRating1);
        }


        userRepository.save(userRated);


    }
}
