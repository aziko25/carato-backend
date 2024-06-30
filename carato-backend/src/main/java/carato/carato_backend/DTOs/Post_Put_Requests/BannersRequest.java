package carato.carato_backend.DTOs.Post_Put_Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BannersRequest {

    private String name;
    private Boolean isActive;
}