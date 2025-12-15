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
public class AttendanceDTO {
    private Long id;

    @NotNull(message = "La sesión es obligatoria")
    private Long sessionId;

    @NotNull(message = "El participante es obligatorio")
    private Long participantId;

    @NotNull(message = "La fecha de asistencia es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date attendedAt;
    private String sessionTitle;
    private String participantName;
}
