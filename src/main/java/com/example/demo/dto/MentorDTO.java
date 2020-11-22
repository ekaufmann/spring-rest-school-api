package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorDTO {

    private Long id;

    @Size(min = 5, max = 96, message = "Nome must be between 5 and 96 characters")
    @NotNull(message = "Nome cannot be null")
    private String nome;

    private Boolean active = true;
}
