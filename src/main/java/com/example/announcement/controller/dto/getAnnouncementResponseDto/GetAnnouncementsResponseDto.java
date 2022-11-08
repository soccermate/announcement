package com.example.announcement.controller.dto.getAnnouncementResponseDto;

import com.example.announcement.repository.entity.Announcement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
public class GetAnnouncementsResponseDto {

    private final List<AnnouncementDto> announcements;

    public GetAnnouncementsResponseDto(List<Announcement> announcements)
    {
        this.announcements = new ArrayList<>();

        for(Announcement announcement: announcements)
        {
            this.announcements.add(new AnnouncementDto(announcement));
        }
    }
}
