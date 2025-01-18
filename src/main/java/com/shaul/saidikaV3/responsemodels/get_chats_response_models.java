package com.shaul.saidikaV3.responsemodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class get_chats_response_models {
    private String  chat_id;
    private  String last_received_message;
    private String sender_name;
    private String recipient_name;
}
