package com.medilabo.frontendservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class NoteDTO {
    @Id
    private String id;
    private Integer patId;

    @Size(min=4, message= "patients's full name must be 4 characters long or more")
    private String patient;

    @JsonProperty("note")
    @NotBlank(message = "note cannot be empty")
    private String content;

    public void setPatIdAndContent(Integer patId, String content) {
        this.patId = patId;
        this.content = content;
    }

}
