package carato.carato_backend.UZJEWELLERY.UzJewelleryService;

import carato.carato_backend.Configurations.Telegram.MainTelegramBot;
import carato.carato_backend.UZJEWELLERY.UzJewelleryDTO.UzJewelleryParticipantsDTO;
import carato.carato_backend.UZJEWELLERY.UzJewelleryModels.UzJewelleryParticipantsMessages;
import carato.carato_backend.UZJEWELLERY.UzJewelleryRepositories.UzJewelleryParticipantsMessagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static carato.carato_backend.UZJEWELLERY.UzJewelleryUtils.UzJewelleryStaticVariables.TG_GROUP_ID;

@Service
@RequiredArgsConstructor
public class UzJewelleryParticipantsService {
    
    private final UzJewelleryParticipantsMessagesRepository participantsRepository;
    private final MainTelegramBot telegramBot;

    //------------------------------------CRUD-----------------------------------------//

    public String participantRegistration(UzJewelleryParticipantsDTO uzJewelleryParticipantsDTO) {

        UzJewelleryParticipantsMessages participant = new UzJewelleryParticipantsMessages();

        participant.setFullName(uzJewelleryParticipantsDTO.getFullName());
        participant.setEmail(uzJewelleryParticipantsDTO.getEmail());
        participant.setMessage(uzJewelleryParticipantsDTO.getMessage());
        participant.setCreationTime(LocalDateTime.now());

        participantsRepository.save(participant);

        SendMessage message = new SendMessage();

        message.setChatId(TG_GROUP_ID);
        message.setText("New Message Received:\n--------------------------------\n\nFull Name: "
                + participant.getFullName() + "\nEmail: " + participant.getEmail()
                + "\nMessage: " + participant.getMessage());

        telegramBot.sendMessage(message);

        return "You Successfully Registered!";
    }

    public String updateParticipant(Long id, UzJewelleryParticipantsDTO uzJewelleryParticipantsDTO) {

        UzJewelleryParticipantsMessages participant = participantsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User Not Found!"));

        if (uzJewelleryParticipantsDTO.getEmail() != null) {
            participant.setEmail(uzJewelleryParticipantsDTO.getEmail());
        }

        if (uzJewelleryParticipantsDTO.getFullName() != null) {
            participant.setFullName(uzJewelleryParticipantsDTO.getFullName());
        }

        if (uzJewelleryParticipantsDTO.getMessage() != null) {
            participant.setMessage(uzJewelleryParticipantsDTO.getMessage());
        }

        participantsRepository.save(participant);

        return "You Successfully Updated Participant!";
    }

    public String deleteParticipant(Long id) {

        UzJewelleryParticipantsMessages participant = participantsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User Not Found!"));

        participantsRepository.delete(participant);

        return "You Successfully Deleted " + participant.getFullName() + "!";
    }

    //-----------------------------------VIEWS-------------------------------------------//

    public List<UzJewelleryParticipantsDTO> allParticipants() {

        return participantsRepository.findAll(Sort.by("creationTime").ascending())
                .stream().map(UzJewelleryParticipantsDTO::new).collect(Collectors.toList());
    }

    public UzJewelleryParticipantsDTO singleParticipant(Long id) {

        return participantsRepository.findById(id)
                .stream().map(UzJewelleryParticipantsDTO::new).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User Not Found!"));
    }
}