package com.example.exercisesix.Service;

import com.example.exercisesix.Repository.TextRepository;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class TextServiceTest {

    @Autowired
    private TextService textService;
    @MockBean
    private Configuration configuration;
    @MockBean
    private TextRepository textRepository;
    @MockBean
    private ModelMapper modelMapper;


    @Test
    void search() throws TemplateException, IOException {
        String string = """
                <request>
                <id>1</id>
                <text>Ayan</text>
                <dateReg>2022-12-20T02:43:28</dateReg>
                </request>""";
        Pageable pageable = PageRequest.of(
                0, 10,
                Sort.Direction.ASC, "dateReg");
       String xmlReturn = textService.search(string,pageable);
        assertTrue(CoreMatchers.is(xmlReturn).matches("test_positive.xml"));

    }
}