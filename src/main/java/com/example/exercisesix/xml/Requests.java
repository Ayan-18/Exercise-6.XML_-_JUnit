package com.example.exercisesix.xml;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Requests {
    @XmlElement(name = "request")
    List<Request> requestList = new ArrayList<>();

    public Requests() {
    }

    public boolean add(Request request) {
        return requestList.add(request);
    }

    @Override
    public String toString() {
        return "Requests{" +
                "requestList=" + requestList +
                '}';
    }
}
