package com.example.demo.mapper;

import com.example.demo.dto.MentorDTO;
import com.example.demo.model.Mentor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MentorMapper {

    Mentor convertDTOToMentor(MentorDTO mentorDTO);

    MentorDTO convertMentorToDTO(Mentor mentor);
}
