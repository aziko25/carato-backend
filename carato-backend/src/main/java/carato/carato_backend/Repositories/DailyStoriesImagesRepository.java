package carato.carato_backend.Repositories;

import carato.carato_backend.Models.DailyStoriesImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyStoriesImagesRepository extends JpaRepository<DailyStoriesImages, Long> {
}