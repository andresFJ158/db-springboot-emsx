package com.app.emsx.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {
    private Long id;

    @NotNull(message = "El participante es obligatorio")
    private Long participantId;

    @NotNull(message = "El evento es obligatorio")
    private Long eventId;

    @NotNull(message = "La fecha de registro es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date registeredAt;

    // info
    private String participantName;
    private String eventName;
}
