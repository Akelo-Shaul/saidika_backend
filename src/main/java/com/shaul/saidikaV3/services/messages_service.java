package com.shaul.saidikaV3.services;


import com.shaul.saidikaV3.entities.messages;
import com.shaul.saidikaV3.repositories.messages_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class messages_service {

    @Autowired
    messages_repo mrrp;

    public messages add_message(messages meSSage){

       return mrrp.save(meSSage);
    }

}
