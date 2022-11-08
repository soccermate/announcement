package com.example.announcement.repository;

import com.example.announcement.repository.entity.Announcement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface AnnouncementRepository extends ReactiveCrudRepository<Announcement, Long>
{
    Flux<Announcement> findByGroupId(Long groupId, Pageable pageable);

}
