package com.app.emsx.dtos;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SpeakerDTO {
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 255, message = "El nombre no puede superar los 255 caracteres")
    private String nombre;

    @NotBlank(message = "La biografía es obligatoria")
    @Size(max = 1000, message = "La bio no puede superar los 1000 caracteres")
    private String bio;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;

    @Size(max = 255, message = "La compañía no puede superar los 255 caracteres")
    private String company;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "Por favor ingresa una fecha de nacimiento válida (debe ser en el pasado)")
    private LocalDate date_nac;
}
