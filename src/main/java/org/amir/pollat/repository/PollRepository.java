package org.amir.pollat.repository;

import org.amir.pollat.entity.Poll;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface PollRepository extends PagingAndSortingRepository<Poll, Long> {
    List<Poll>
}
