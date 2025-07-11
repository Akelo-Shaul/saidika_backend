package com.shaul.saidikaV3.requestModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private String title;
    private String body;
    private String topic;
    private String token;
}
