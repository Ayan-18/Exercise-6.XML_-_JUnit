package com.example.exercisesix.Controller;

import com.example.exercisesix.DTO.TextDTO;
import com.example.exercisesix.Service.TextService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/documents")
@RequiredArgsConstructor
public class TextRestController {
    @Autowired
    private final TextService textService;

    @GetMapping(path = "/all", produces = "application/json")
    @Tag(name = "Все документы", description = "Показывает документы")
    public List<TextDTO> all(
            @RequestParam(value = "page") Optional<Integer> page,
            @RequestParam(value = "size") Optional<Integer> size,
            @RequestParam(value = "sortBy") Optional<String> sortBy
    ) {
        return textService.allDTO(PageRequest.of(
                page.orElse(0), size.orElse(1),
                Sort.Direction.ASC, sortBy.orElse("id")));
    }

    @GetMapping(path = "/{id}")
    @Tag(name = "Удалить документ", description = "Удаляет документ по ID")
    public void delete(@PathVariable String id) {
        textService.delete(id);
    }

    @PostMapping(path = "/search",consumes = "application/xml", produces = "application/xml")
    public String search(
            @RequestBody String string,
            @RequestParam(value = "page") Optional<Integer> page,
            @RequestParam(value = "sortBy") Optional<String> sortBy
    ) throws Exception {
        return textService.search(string, PageRequest.of(
                page.orElse(0), 10,
                Sort.Direction.ASC, sortBy.orElse("dateReg")));
    }
}
