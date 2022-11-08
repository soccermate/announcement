package com.example.announcement.service;

import com.example.announcement.controller.util.VerifyTokenResult;
import com.example.announcement.exceptions.ResourceNotFoundException;
import com.example.announcement.exceptions.TitleDuplicateException;
import com.example.announcement.exceptions.UnAuthorizedException;
import com.example.announcement.repository.AnnouncementRepository;
import com.example.announcement.repository.entity.Announcement;
import com.example.announcement.service.dto.CreateAnnouncementServiceDto;
import com.example.announcement.util.SoccerGroupFeignClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    private final SoccerGroupFeignClient soccerGroupFeignClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Transactional("r2dbcTransactionManager")
    public Mono<Announcement> createAnnouncement(CreateAnnouncementServiceDto createAnnouncementServiceDto, VerifyTokenResult verifyTokenResult)
    {
        return soccerGroupFeignClient.getUser(objectMapper.writeValueAsString(verifyTokenResult), createAnnouncementServiceDto.getGroupId())
                .switchIfEmpty(Mono.defer(()->{
                    throw new ResourceNotFoundException("the soccer-group with id " + Long.valueOf(createAnnouncementServiceDto.getGroupId()) + " not found");
                }))
                .flatMap(soccerGroupDetailResponseDto -> {
                    if(!soccerGroupDetailResponseDto.getOwner_id().equals(createAnnouncementServiceDto.getWriterId()))
                    {
                        throw new UnAuthorizedException("not authorized to write announcement.");
                    }

                    Announcement announcement = Announcement.builder()
                            .title(createAnnouncementServiceDto.getTitle())
                            .groupId(createAnnouncementServiceDto.getGroupId())
                            .content(createAnnouncementServiceDto.getContent())
                            .writerId(createAnnouncementServiceDto.getWriterId())
                            .createdTime(LocalDateTime.now()).build();

                    return announcementRepository.save(announcement);
                })
                .onErrorResume(throwable -> {
                    if(throwable instanceof DataIntegrityViolationException)
                    {
                        log.debug(throwable.toString());
                        return Mono.error(new TitleDuplicateException("title should be unique!"));
                    }
                    else if(throwable instanceof FeignException.FeignClientException.NotFound)
                    {
                        log.debug(throwable.toString());
                        return Mono.error(
                                new ResourceNotFoundException("the soccer-group with id " + Long.valueOf(createAnnouncementServiceDto.getGroupId()) + " not found")
                        );
                    }
                    else{
                        log.error(throwable.toString());
                        return Mono.error(throwable);
                    }
                });
    }

    public Flux<Announcement> getAnnouncementByGroupId(Long groupId, Pageable pageable)
    {
        log.debug("getAnnouncementByGroupId called with groupId " + groupId);

        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber()
                , pageable.getPageSize()
                , Sort.by("createdTime").descending() );

        return announcementRepository.findByGroupId(groupId, sortedPageable);
    }


    public Mono<Announcement> getAnnouncementById(Long announcementId)
    {
        log.debug("getAnnouncementById called with announcementId " + announcementId);

        return announcementRepository.findById(announcementId);
    }


}
