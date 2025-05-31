package com.affles.watchout.server.domain.disaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleNotification {
    private String title;
    private String contents;
}