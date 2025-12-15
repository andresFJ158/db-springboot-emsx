package com.app.emsx.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {
    private Long id;

    @NotBlank(message = "El título de la sesión es obligatorio")
    private String title;

    @NotBlank(message = "La descripción de la sesión es obligatoria")
    @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres")
    private String description;

    @NotNull(message = "La hora de inicio es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date startTime;

    @NotNull(message = "La hora de fin es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date endTime;

    @NotNull(message = "El evento asociado es obligatorio")
    private Long eventId;

    @NotNull(message = "El speaker asociado es obligatorio")
    private Long speakerId;

    // Campos informativos (response)
    private String eventName;    // p.ej. nombre/título del evento
    private String speakerName;  // p.ej. fullName del speaker
}
