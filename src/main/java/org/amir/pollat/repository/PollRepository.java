package org.amir.pollat.repository;

import org.amir.pollat.model.entity.Poll;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PollRepository extends PagingAndSortingRepository<Poll, Long> {
}
