package carato.carato_backend.UZJEWELLERY.UzJewelleryRepositories;

import carato.carato_backend.UZJEWELLERY.UzJewelleryModels.UzJewelleryParticipantsMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UzJewelleryParticipantsMessagesRepository extends JpaRepository<UzJewelleryParticipantsMessages, Long> {
}