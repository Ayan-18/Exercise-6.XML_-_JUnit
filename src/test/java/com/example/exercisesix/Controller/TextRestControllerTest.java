package com.example.exercisesix.Controller;

import com.example.exercisesix.Document.Text;
import com.example.exercisesix.ExerciseSixApplication;
import com.example.exercisesix.Repository.TextRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = ExerciseSixApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TextRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TextRestController restController;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TextRepository textRepository;


    @Test
    public void all() throws Exception {
        this.mockMvc.perform(
                        get("/documents/all")
                                .param("page", "1")
                                .param("size", "2")
                                .param("sortBy", "dateReg"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void delete() throws Exception {
        Text text = createTextTest();

        this.mockMvc.perform(
                        get("/documents/{id}", text.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void search() throws Exception {
        String string = """
                <request>
                <id>122</id>
                <text>Aidana</text>
                <dateReg>2022-12-20T02:43:28</dateReg>
                </request>
                """;
        this.mockMvc.perform(
                        post("/documents/search").content(string).contentType("application/xml"))
                .andDo(print()).andExpect(status().isOk());
    }

    private Text createTextTest() {
        Text textTest = new Text("1", "Ayan Toleu java", LocalDateTime.of(2022, 12, 19, 2, 44, 31));
        return textRepository.save(textTest);
    }
}
