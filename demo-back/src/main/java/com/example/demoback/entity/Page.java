package com.example.demoback.entity;

import lombok.Data;

import java.util.List;

@Data
public class Page {

    int page = 1;
    int size = 10;
    int total;
    List<?> data;
}
