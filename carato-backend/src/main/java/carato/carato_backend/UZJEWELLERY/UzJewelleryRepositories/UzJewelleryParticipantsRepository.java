package carato.carato_backend.UZJEWELLERY.UzJewelleryRepositories;

import carato.carato_backend.UZJEWELLERY.UzJewelleryModels.UzJewelleryParticipants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UzJewelleryParticipantsRepository extends JpaRepository<UzJewelleryParticipants, Long> {
}