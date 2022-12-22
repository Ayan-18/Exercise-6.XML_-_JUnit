package com.example.exercisesix.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Request {
    @XmlElement(required = true)
    private String id;
    @XmlElement(required = true)
    private String text;
    @XmlElement(required = true)
    private String dateReg;

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDateReg(String dateReg) {
        this.dateReg = dateReg;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getDateReg() {
        return dateReg;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", dateReg='" + dateReg + '\'' +
                '}';
    }
}
