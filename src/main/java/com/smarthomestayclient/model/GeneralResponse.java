package com.smarthomestayclient.model;

import lombok.Data;

@Data
public class GeneralResponse<E> {
    private int code;
    private String status;
    private String message;
    private E data;
}
