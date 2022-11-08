package com.example.announcement;

import com.example.announcement.controller.util.VerifyTokenResult;
import com.example.announcement.repository.AnnouncementRepository;
import com.example.announcement.repository.entity.Announcement;
import com.example.announcement.service.AnnouncementService;
import com.example.announcement.service.dto.CreateAnnouncementServiceDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@Slf4j
@EnableConfigurationProperties
@ActiveProfiles(profiles = "dev")
class AnnouncementApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	AnnouncementRepository announcementRepository;

	@Test
	void testAnnouncementRepository()
	{
		Announcement announcement = Announcement.builder()
				.title("hello")
				.content("yolo")
				.writerId(Long.valueOf(3))
				.groupId(Long.valueOf(10))
				.build();

		Mono<Announcement> createdAnnouncement = announcementRepository.save(announcement)
				.map(announcement1 -> {
					log.info(announcement1.toString());
					return announcement1;
				});

		StepVerifier.create(createdAnnouncement).expectNext().verifyComplete();
	}

	@Autowired
	AnnouncementService announcementService;

	@Test
	void testAnnouncementService()
	{
		VerifyTokenResult verifyTokenResult = new VerifyTokenResult(6, "USER", true);

		CreateAnnouncementServiceDto c = CreateAnnouncementServiceDto.builder()
				.title("hello world!")
				.content("yolo 11223")
				.groupId(Long.valueOf(100))
				.writerId(Long.valueOf(6))
				.build();

		Mono<Announcement> a = announcementService.createAnnouncement(c, verifyTokenResult).log();

		StepVerifier.create(a)
				.expectError()
				.verify();
	}

	@Test
	void testAnnouncementGetAnnouncements()
	{
		Pageable pageable = PageRequest.of(0, 10);
		Flux<Announcement> a = announcementService.getAnnouncementByGroupId(Long.valueOf(11) , pageable).log();

		StepVerifier.create(a)
				.expectNextCount(0)
				.verifyComplete();

	}

	@Test
	void testAnnouncementDetails()
	{
		Mono<Announcement> a = announcementService.getAnnouncementById(Long.valueOf(10)).log();

		StepVerifier.create(a)
				.expectNextCount(0)
				.verifyComplete();
	}


}
