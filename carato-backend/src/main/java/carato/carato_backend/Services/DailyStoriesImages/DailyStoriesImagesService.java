package carato.carato_backend.Services.DailyStoriesImages;

import carato.carato_backend.Configurations.Images.FileUploadUtil;
import carato.carato_backend.Models.DailyStoriesImages;
import carato.carato_backend.Repositories.DailyStoriesImagesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.quartz.Scheduler;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.SimpleScheduleBuilder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DailyStoriesImagesService {

    private final DailyStoriesImagesRepository dailyStoriesImagesRepository;

    private final Scheduler scheduler;
    private final FileUploadUtil fileUploadUtil;

    public List<DailyStoriesImages> create(List<MultipartFile> files) {

        List<DailyStoriesImages> dailyStoriesImagesList = new ArrayList<>();

        for (MultipartFile file : files) {

            DailyStoriesImages dailyStoriesImages = DailyStoriesImages.builder()
                    .createdTime(LocalDateTime.now())
                    .imageUrl(fileUploadUtil.handleMediaUpload(UUID.randomUUID().toString(), file))
                    .build();

            dailyStoriesImages = dailyStoriesImagesRepository.save(dailyStoriesImages);
            dailyStoriesImagesList.add(dailyStoriesImages);

            scheduleDeletion(dailyStoriesImages);
        }

        return dailyStoriesImagesList;
    }

    public List<DailyStoriesImages> getAll() {

        return dailyStoriesImagesRepository.findAll(Sort.by("id").ascending());
    }

    public DailyStoriesImages getById(Long id) {

        return dailyStoriesImagesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Media Not Found"));
    }

    private void scheduleDeletion(DailyStoriesImages dailyStoriesImages) {

        try {

            JobDataMap jobDataMap = new JobDataMap();

            jobDataMap.put("imageId", dailyStoriesImages.getId());

            JobDetail jobDetail = JobBuilder.newJob(DeleteDailyStoriesImageJob.class)
                    .withIdentity(UUID.randomUUID().toString(), "delete-daily-stories-images")
                    .usingJobData(jobDataMap)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(UUID.randomUUID().toString(), "delete-daily-stories-images-trigger")
                    .startAt(Date.from(dailyStoriesImages.getCreatedTime().plusHours(24).atZone(ZoneId.systemDefault()).toInstant()))
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule())
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
        }
        catch (Exception ignored) {}
    }
}