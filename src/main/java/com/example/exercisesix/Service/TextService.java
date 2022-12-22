package com.example.exercisesix.Service;

import com.example.exercisesix.DTO.TextDTO;
import com.example.exercisesix.Document.Text;
import com.example.exercisesix.Repository.TextRepository;
import com.example.exercisesix.xml.Request;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TextService {
    private final Configuration freemarker;
    private final TextRepository textRepository;
    @Autowired
    private ModelMapper modelMapper;


    public void create(final Text text) {
        textRepository.save(text);
    }

    public Page<Text> find(String text, Pageable pageable) {
        return textRepository.findAllByTextContainingIgnoreCase(text, pageable);
    }

    public Page<Text> all(Pageable pageable) {
        return textRepository.findAll(pageable);
    }

    public void delete(String id) {
        textRepository.deleteById(id);
    }

    public List<TextDTO> allDTO(Pageable pageable) {
        return textRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    private TextDTO convertEntityToDto(Text text) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(text, TextDTO.class);
    }


    public String search(String string, Pageable pageable) throws IOException, TemplateException {
        if (validateXMLSchema(string)) {
            Request request = new Request();
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(Request.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                request = (Request) unmarshaller.unmarshal(new StringReader(string));
            } catch (JAXBException e) {
                e.printStackTrace();
            }

//            JAXBContext jaxbContext = JAXBContext.newInstance(Request.class);
//            Marshaller marshaller = jaxbContext.createMarshaller();


            List<TextDTO> dtoList = textRepository.findAllByTextContainingIgnoreCase(request.getText(), pageable)
                    .stream()
                    .map(this::convertEntityToDto).toList();

            List<Request> requestList = new ArrayList<>();

            for (TextDTO dto : dtoList) {
                Request r = new Request() {
                };
                r.setId(dto.getId());
                r.setText(dto.getText());
                r.setDateReg(dto.getDateReg().toString());
                requestList.add(r);
            }
            Template template = freemarker.getTemplate("response/request_response.xml");
            Writer writer = new StringWriter();
            template.process(Map.of("requests", requestList), writer);
            return writer.toString();


//            OutputStream os = new FileOutputStream("src/main/resources/templates/response/requests.xml");
//            marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
//            marshaller.marshal(requestList, os);
        } else {
            System.out.println("Валидация не прошла успешно");
        }
        return null;
    }

    private boolean validateXMLSchema(String string) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File("src/main/resources/static/text.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(string)));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
        return true;
    }
}