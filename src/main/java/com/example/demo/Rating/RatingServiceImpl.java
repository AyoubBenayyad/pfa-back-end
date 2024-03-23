package com.example.demo.Rating;


import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class RatingServiceImpl implements IRatingService{

    private UserRepository userRepository;
    private UserRatingRepo userRatingRepo;

    public RatingServiceImpl(UserRepository userRepository,UserRatingRepo userRatingRepo) {
        this.userRepository = userRepository;
        this.userRatingRepo = userRatingRepo;
    }

    @Override
    public void rateUser(RatingRequest request, Long Id_ratingUser) throws Exception {
        if(request.id_userRated ==Id_ratingUser ){
            throw new Exception("user cant rate himself");
        }
        Optional<User> rated = userRepository.findById(request.getId_userRated());
        Optional<User> rating = userRepository.findById(Id_ratingUser);

        User userRated = rated.orElse(null);
        User userRating = rating.orElse(null);

        StarRating starsRating=null;
        switch (request.getRate()) {
            case 1:
                starsRating = StarRating.ONE_STAR;
                break;
            case 2:
                starsRating = StarRating.TWO_STARS;
                break;
            case 3:
                starsRating = StarRating.THREE_STARS;
                break;
            case 4:
                starsRating = StarRating.FOUR_STARS;
                break;
            case 5:
                starsRating = StarRating.FIVE_STARS;
                break;
            default:
                throw new InvalidInputException("wrong rating");
        }

        UserRating userRating1 = new UserRating(userRating,userRated,starsRating);

        float numberOfStars = 0;
        switch (starsRating) {
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
        float oldNumberOfStars = 0;
        if(updateUserRating!=null){
            switch (updateUserRating.getRating()) {
                case ONE_STAR:
                    oldNumberOfStars = 1;
                    break;
                case TWO_STARS:
                    oldNumberOfStars = 2;
                    break;
                case THREE_STARS:
                    oldNumberOfStars = 3;
                    break;
                case FOUR_STARS:
                    oldNumberOfStars = 4;
                    break;
                case FIVE_STARS:
                    oldNumberOfStars = 5;
                    break;
                default:
                    throw new InvalidInputException("wrong rating");
            }
            userRated.setRate(numberOfStars,oldNumberOfStars,true);
            updateUserRating.setRating(starsRating);
            userRatingRepo.save(updateUserRating);
        }else{
            userRated.setRate(numberOfStars,oldNumberOfStars,false);
            userRated.getRatedUsers().add(userRating1);
            userRating.getRatedUsers().add(userRating1);

            userRepository.save(userRating);
            userRatingRepo.save(userRating1);
        }


        userRepository.save(userRated);


    }

    @Override
    public int getUserRating(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if( user.isEmpty()){
            throw new NoSuchElementException("user doesnt exist");
        }

        return (int) Math.floor(user.get().getRate());

    }
}
