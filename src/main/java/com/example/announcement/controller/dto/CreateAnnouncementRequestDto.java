package com.example.announcement.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateAnnouncementRequestDto {

    @NotBlank(message =  "title should not be blank!")
    @Size(min = 1, max = 60, message = "title should be greater than 0 and smaller than 60")
    private final String title;

    @NotBlank(message = "content should not be blank!")
    @Size(min = 1, message = "content should be greater than 0")
    private final String content;

}
