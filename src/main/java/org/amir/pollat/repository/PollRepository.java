package org.amir.pollat.repository;

import org.amir.pollat.entity.Poll;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PollRepository extends PagingAndSortingRepository<Poll, Long> {
}
