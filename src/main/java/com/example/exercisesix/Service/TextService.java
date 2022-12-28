package com.example.exercisesix.Service;

import com.example.exercisesix.DTO.TextDTO;
import com.example.exercisesix.Document.Text;
import com.example.exercisesix.Mapper.ModelMaper;
import com.example.exercisesix.Repository.TextRepository;
import com.example.exercisesix.xml.Request;
import com.example.exercisesix.xml.Requests;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TextService {
    private final TextRepository textRepository;
    private final ModelMaper modelMapper;

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
        return modelMapper.mapAll(textRepository.findAll(pageable),TextDTO.class);
    }

    public String search(String string, Pageable pageable) throws JAXBException {
        if (validateXMLSchema(string)) {
            Request request = new Request();
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(Request.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                request = (Request) unmarshaller.unmarshal(new StringReader(string));
            } catch (JAXBException e) {
                e.printStackTrace();
            }

            Page<Text> texts = textRepository.findAllByTextContainingIgnoreCase(request.getText(), pageable);
            List<TextDTO> dtoList = modelMapper.mapAll(texts, TextDTO.class);

            Requests requests = new Requests();
            for (TextDTO dto : dtoList) {
                Request r = new Request();
                r.setId(dto.getId());
                r.setText(dto.getText());
                r.setDateReg(dto.getDateReg().toString());
                requests.add(r);
            }
            JAXBContext jaxbContext = JAXBContext.newInstance(Requests.class);
            Marshaller marshaller = jaxbContext.createMarshaller();

            StringWriter sw = new StringWriter();

            marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
            marshaller.marshal(requests, sw);

            return sw.toString();
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