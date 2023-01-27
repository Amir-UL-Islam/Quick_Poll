package org.amir.pollat.controller;

import org.amir.pollat.entity.Vote;
import org.amir.pollat.repository.VoteRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;

@RestController
@RequestMapping("/api/")
public class VoteController {
    @Inject
    private VoteRepository voteRepository;
    @PostMapping("/polls/{pollId}/votes") // Id represents the pollId
    public ResponseEntity<?> createVote(@PathVariable Long pollId, @RequestBody Vote vote) {
        voteRepository.save(vote);
//        Set the headers for the newly created resource
        HttpHeaders httpHeaders = new HttpHeaders();
        URI newVoteUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(vote.getId())
                        .toUri();
        httpHeaders.setLocation(newVoteUri);
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.ACCEPTED);
    }
}
