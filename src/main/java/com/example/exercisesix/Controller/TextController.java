package com.example.exercisesix.Controller;

import com.example.exercisesix.Document.Text;
import com.example.exercisesix.Service.TextService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(path = "/documents")
@RequiredArgsConstructor
public class TextController {
    public final TextService textService;

    @GetMapping(path = "/create")
    public ModelAndView create() {
        return new ModelAndView("create");
    }

    @PostMapping(path = "/create")
    public ModelAndView create(
            @RequestParam String text
    ) {
        Text textToSave = new Text();
        textToSave.setText(text);
        textToSave.setId(UUID.randomUUID().toString());
        textToSave.setDateReg(LocalDateTime.now());
        textService.create(textToSave);
        return new ModelAndView("create");
    }

    @GetMapping(path = "/find")
    public ModelAndView find(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) Optional<Integer> page,
            @RequestParam(required = false) Optional<Integer> size,
            @RequestParam(required = false) Optional<String> sortBy
    ) {
        Page<Text> texts;
        if (text != null) {
            texts = textService.find(text, PageRequest.of(
                    page.orElse(0), size.orElse(1),
                    Sort.Direction.ASC, sortBy.orElse("id")));
        } else {
            texts = textService.all(PageRequest.of(
                    page.orElse(0), size.orElse(1),
                    Sort.Direction.ASC, sortBy.orElse("id")));
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("find");
        modelAndView.addObject("texts", texts);
        return modelAndView;
    }
}
