package com.example.announcement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name="announcement")
public class Announcement {

    @Id
    @Column("announcement_id")
    private Long announcementId;

    @Column("title")
    private String title;

    @Column("content")
    private String content;

    @Column("group_id")
    private Long groupId;

    @Column("created_time")
    private LocalDateTime createdTime;

    @Column("writer_id")
    private Long writerId;

}
