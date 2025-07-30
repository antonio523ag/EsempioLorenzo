package dev.antoniogrillo.esempilorenzo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SingolaDomanda {
    @JsonProperty("type")
    private String tipo;
    @JsonProperty("difficulty")
    private String difficolta;
    @JsonProperty("category")
    private String categoria;
    @JsonProperty("question")
    private String domanda;
    @JsonProperty("correct_answer")
    private String rispostaCorretta;
    @JsonProperty("incorrect_answers")
    private List<String> risposteErrate;
}
