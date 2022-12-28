package com.example.exercisesix.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
public class TextDTO {
    private String id;
    private String text;
    private LocalDateTime dateReg;

    public TextDTO(String id, String text, LocalDateTime dateReg) {
        this.id = id;
        this.text = text;
        this.dateReg = dateReg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateReg() {
        return dateReg;
    }

    public void setDateReg(LocalDateTime dateReg) {
        this.dateReg = dateReg;
    }

    @Override
    public String toString() {
        return "TextDTO{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", dateReg=" + dateReg +
                '}';
    }
}