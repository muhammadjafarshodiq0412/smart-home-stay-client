package com.smarthomestayclient.model.request;

import lombok.Data;

@Data
public class AddRoomRequest {
    private int roomNumber;
    private int roomType;
    private String capacity;
    private String floor;
}
