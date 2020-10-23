package com.example.demo.mapper;

import com.example.demo.dto.MentorDTO;
import com.example.demo.model.Mentor;

public class MentorMapper {

    public static MentorDTO convertMentorToDTO(Mentor mentor) {
        if(mentor != null) {
            return new MentorDTO(mentor.getId(), mentor.getNome());
        }
        return null;
    }
}
