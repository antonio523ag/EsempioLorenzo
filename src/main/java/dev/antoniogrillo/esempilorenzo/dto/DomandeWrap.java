package dev.antoniogrillo.esempilorenzo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DomandeWrap {
    @JsonProperty("response_code")
    private int codiceRisposta;
    @JsonProperty("results")
    private List<SingolaDomanda> domande;
}
