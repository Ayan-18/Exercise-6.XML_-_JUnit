package com.example.exercisesix.Controller;

import com.example.exercisesix.DTO.TextDTO;
import com.example.exercisesix.Document.Text;
import com.example.exercisesix.Repository.TextRepository;
import com.example.exercisesix.Service.TextService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class TextRestControllerTest {
//    @Autowired
//    private ModelMapper modelMapper;
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private TextRepository repo;

    @Test
    void all() throws Exception {

    }

    @Test
    void delete() {
    }

    @Test
    void search() {
    }

//    private TextDTO convertEntityToDto(Text text) {
//        modelMapper.getConfiguration()
//                .setMatchingStrategy(MatchingStrategies.LOOSE);
//        return modelMapper.map(text, TextDTO.class);
//    }
}