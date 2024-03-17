package com.example.demo.Vote;

import com.example.demo.Rating.RatingRequest;

public interface IVoteService {
    void UpVote(Long PostId,Long UserId) throws Exception;
    void DownVote(Long PostId,Long UserId) throws Exception;

    void RemoveUpVote(Long PostId,Long UserId) throws Exception;
    void RemoveDownVote(Long PostId,Long UserId) throws Exception;

    VoteResponseDto isVoted(Long postId, Long userId);
}
