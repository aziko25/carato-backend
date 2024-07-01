package carato.carato_backend.UZJEWELLERY.UzJewelleryDTO;

import carato.carato_backend.UZJEWELLERY.UzJewelleryModels.UzJewelleryParticipantsMessages;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UzJewelleryParticipantsDTO {

    private Long id;
    private String fullName;
    private String email;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationTime;

    public UzJewelleryParticipantsDTO(UzJewelleryParticipantsMessages participant) {

        setId(participant.getId());
        setFullName(participant.getFullName());
        setEmail(participant.getEmail());
        setMessage(participant.getMessage());
    }
}