package me.archdev.staffrelay.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class StaffMessage {
    private final String username;
    private final String message;
    private final Timestamp messageSendTime;
}
