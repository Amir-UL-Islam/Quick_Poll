package org.amir.pollat.controller;

import org.amir.pollat.entity.Poll;
import org.amir.pollat.exception.ResourceNotFoundException;
import org.amir.pollat.repository.PollRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.awt.print.Pageable;
import java.net.URI;
import java.util.Optional;

@RestController
public class PollController {
    @Inject
    private PollRepository pollRepository;

//    Verifying Poll by ID
    public Poll verifyPoll(Long pollId) throws ResourceNotFoundException{
        Optional<Poll> poll = pollRepository.findById(pollId);
        if(!poll.isPresent()) {
//            throw new IllegalAccessException("Poll by the ID: " + pollId + " dose not Exist in DB");
            throw new ResourceNotFoundException("Poll by the ID: " + pollId + " dose not Exist in DB");
        }
        return poll.get();
    }

    // Check if the poll exists
    @GetMapping("/polls")
    public ResponseEntity<Page<Poll>> getAllPolls(Pageable pageable) {
        Page<Poll> allPolls = (Page<Poll>) pollRepository.findAll((Sort) pageable);
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

    // Creating Poll Resource
    @PostMapping("/polls")
    public ResponseEntity<?> createPoll(@Valid @RequestBody  Poll poll) {
//        Poll poll = new Poll();
// Don't Need this line as The @RequestBody annotation maps the HttpRequest body to a transfer or domain object, enabling automatic deserialization of the inbound HttpRequest body onto a Java object.
//        poll.setQuestion("What is your favorite color?");
//        pollRepository.save(poll);
        pollRepository.save(poll);
//       If the client wants to share the newly created Poll to a social networking site, the previous implementation will not suffice. So we offer this.
        URI newPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(poll.getId()) // buildAndExpand method would build a UriComponents instance and replace any path variables with the given values.
                .toUri();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(newPollUri);

        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
//        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    // Check if the poll exists
    @GetMapping("/polls/{pollId}")
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) throws ResourceNotFoundException { //placeholder {pollId} along with the @PathVarible annotation allows Spring to examine the request URI path and extract the pollId parameter value.
        return new ResponseEntity<>(verifyPoll(pollId), HttpStatus.OK);
    }

    // Update a Poll
    @PutMapping("/polls/{pollId}")
    public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {

        verifyPoll(pollId);
        // Save the entity
        Poll newPoll = pollRepository.save(poll);
        return new ResponseEntity<>(newPoll, HttpStatus.IM_USED);
    }


    // Delete a Poll
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/polls/{pollId}" , method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
//        The method deleteById will throw an EmptyResultDataAccessException if the supplied id does not exist, whereas the method delete will silently return if the supplied entity hasn't been persisted yet, or for whatever reason it cannot be found by the EntityManager.

//        pollRepository.deleteById(pollId);
        pollRepository.delete(verifyPoll(pollId));
        return new ResponseEntity<>(HttpStatus.GONE);
    }
}
