package com.smarthomestayclient.model;

import lombok.Data;

@Data
public class Room {
    private Long id;
    private String createdDate;
    private String createdBy;
    private String updatedDate;
    private String updatedBy;
    private String roomNumber;
    private RoomType roomType;
    private String capacity;
    private String floor;
    private String status;
}
