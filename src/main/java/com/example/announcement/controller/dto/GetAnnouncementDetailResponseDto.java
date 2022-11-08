package com.example.announcement.controller.dto;

import com.example.announcement.controller.dto.getAnnouncementResponseDto.GetAnnouncementsResponseDto;
import com.example.announcement.repository.entity.Announcement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class GetAnnouncementDetailResponseDto {

    private final Long announcement_id;

    private final String announcement_title;

    private final LocalDateTime created_time;

    private final Long writer_id;

    private final Long group_id;

    private final String content;

    public GetAnnouncementDetailResponseDto(Announcement announcement)
    {
        this.announcement_id = announcement.getAnnouncementId();
        this.announcement_title = announcement.getTitle();
        this.created_time = announcement.getCreatedTime();
        this.writer_id = announcement.getWriterId();
        this.group_id = announcement.getGroupId();
        this.content = announcement.getContent();
    }
}
