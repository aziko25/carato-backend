package carato.carato_backend.Services.DailyStoriesImages;

import carato.carato_backend.Configurations.Images.FileUploadUtil;
import carato.carato_backend.Models.DailyStoriesImages;
import carato.carato_backend.Repositories.DailyStoriesImagesRepository;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteDailyStoriesImageJob implements Job {

    private final DailyStoriesImagesRepository dailyStoriesImagesRepository;

    private final FileUploadUtil fileUploadUtil;

    @Override
    public void execute(JobExecutionContext context) {

        Long imageId = context.getJobDetail().getJobDataMap().getLong("imageId");
        DailyStoriesImages image = dailyStoriesImagesRepository.findById(imageId).orElse(null);
        
        if (image != null) {

            fileUploadUtil.handleMediaDeletion(image.getImageUrl());
            dailyStoriesImagesRepository.delete(image);
        }
    }
}