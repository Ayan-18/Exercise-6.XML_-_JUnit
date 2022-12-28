package com.example.exercisesix.Service;

import com.example.exercisesix.DTO.TextDTO;
import com.example.exercisesix.Mapper.ModelMaper;
import com.example.exercisesix.Repository.TextRepository;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TextService.class})
@ExtendWith(SpringExtension.class)
class TextServiceTest {
    @Autowired
    private TextService textService;
    @MockBean
    private ModelMaper modelMapper;
    @MockBean
    private TextRepository textRepository;

    String string = """
            <request>
            <id>122</id>
            <text>Ayan</text>
            <dateReg>2022-12-20T02:43:28</dateReg>
            </request>
            """;
    private final Pageable pageable = PageRequest.of(0, 1, Sort.Direction.ASC, "dateReg");

    @BeforeEach
    public void setUp() {
        List<TextDTO> dtoList = new ArrayList<>();
        dtoList.add(new TextDTO("1", "Toleu 1", LocalDateTime.of(2022, 12, 20, 2, 44, 31)));
        dtoList.add(new TextDTO("2", "Ayan 2", LocalDateTime.of(2022, 12, 20, 2, 44, 32)));
        dtoList.add(new TextDTO("3", "Toleu Ayan 3", LocalDateTime.of(2022, 12, 20, 2, 44, 33)));
        dtoList.add(new TextDTO("4", "TA 4", LocalDateTime.of(2022, 12, 20, 2, 44, 34)));
        when(modelMapper.mapAll(any(), any())).thenReturn(Arrays.asList(dtoList.toArray()));
    }

    @Test
    void searchRight() throws JAXBException, IOException, URISyntaxException {
        String actual = textService.search(string, pageable);
        String expected = Files.readString(Path.of(getClass().getClassLoader().getResource("test_positive.xml").toURI()));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void searchFalse() throws JAXBException, IOException, URISyntaxException {
        String actual = textService.search(string, pageable);
        String expected = Files.readString(Path.of(getClass().getClassLoader().getResource("test_negative.xml").toURI()));
        Assertions.assertNotEquals(expected, actual);
    }
}