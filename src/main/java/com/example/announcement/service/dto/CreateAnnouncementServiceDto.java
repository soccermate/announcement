package com.example.announcement.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class CreateAnnouncementServiceDto {

    private final String title;

    private final String content;

    private final Long groupId;

    private final Long writerId;

}
