package com.itmk.web.home.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class EchartsVo {
    private List<String> names = new ArrayList<>();
    private List<Integer> values = new ArrayList<>();
}
