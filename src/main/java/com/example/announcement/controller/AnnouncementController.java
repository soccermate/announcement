package com.example.announcement.controller;

import com.example.announcement.controller.dto.CreateAnnouncementRequestDto;
import com.example.announcement.controller.dto.GetAnnouncementDetailResponseDto;
import com.example.announcement.controller.dto.getAnnouncementResponseDto.GetAnnouncementsResponseDto;
import com.example.announcement.controller.util.ObjectConverter;
import com.example.announcement.controller.util.VerifyTokenResult;
import com.example.announcement.service.AnnouncementService;
import com.example.announcement.service.dto.CreateAnnouncementServiceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static com.example.announcement.config.GlobalStaticVariables.AUTH_CREDENTIALS;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("soccer-group")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping("{groupId}/announcements")
    Mono<ResponseEntity<Void>> createResponseEntity(
            @RequestHeader(AUTH_CREDENTIALS) String authStr,
            @PathVariable Long groupId,
            @Valid @RequestBody CreateAnnouncementRequestDto createAnnouncementRequestDto
            )
    {
        VerifyTokenResult verifyTokenResult = ObjectConverter.convertAuthCredentials(authStr);

        CreateAnnouncementServiceDto createAnnouncementServiceDto = CreateAnnouncementServiceDto.builder()
                .title(createAnnouncementRequestDto.getTitle())
                .writerId(verifyTokenResult.getUser_id())
                .groupId(groupId)
                .content(createAnnouncementRequestDto.getContent())
                .build();

        return announcementService.createAnnouncement(createAnnouncementServiceDto, verifyTokenResult)
                .flatMap(announcement -> Mono.empty());
    }

    @GetMapping("{groupId}/announcements")
    Mono<ResponseEntity<GetAnnouncementsResponseDto>> getAnnouncements(
            @RequestHeader(AUTH_CREDENTIALS) String authStr,
            @PathVariable Long groupId,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size
    )
    {
        VerifyTokenResult verifyTokenResult = ObjectConverter.convertAuthCredentials(authStr);

        Pageable pageable = PageRequest.of(page, size);

        return announcementService.getAnnouncementByGroupId(groupId, pageable)
                .collectList()
                .map(announcements -> {
                    return ResponseEntity.ok(new GetAnnouncementsResponseDto(announcements));
                });
    }

    @GetMapping("{groupId}/announcements/{announcementId}")
    Mono<ResponseEntity<GetAnnouncementDetailResponseDto>> getAnnouncementDetail(
            @RequestHeader(AUTH_CREDENTIALS) String authStr,
            @PathVariable Long groupId,
            @PathVariable Long announcementId
    )
    {
        VerifyTokenResult verifyTokenResult = ObjectConverter.convertAuthCredentials(authStr);

        return announcementService.getAnnouncementById(announcementId)
                .map(announcement -> {
                    return ResponseEntity.ok(new GetAnnouncementDetailResponseDto(announcement));
                });
    }




}
