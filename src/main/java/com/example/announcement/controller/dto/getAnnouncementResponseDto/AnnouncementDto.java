package com.example.announcement.controller.dto.getAnnouncementResponseDto;

import com.example.announcement.repository.entity.Announcement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class AnnouncementDto {

    private final Long announcement_id;

    private final String announcement_title;

    private final LocalDateTime created_time;

    private final Long writer_id;

    private final Long group_id;

    public AnnouncementDto(Announcement announcement)
    {
        this.announcement_id = announcement.getAnnouncementId();
        this.announcement_title = announcement.getTitle();
        this.created_time = announcement.getCreatedTime();
        this.writer_id = announcement.getWriterId();
        this.group_id = announcement.getGroupId();
    }
}
