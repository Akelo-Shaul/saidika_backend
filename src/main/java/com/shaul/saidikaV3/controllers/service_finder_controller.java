package com.shaul.saidikaV3.controllers;


import com.shaul.saidikaV3.entities.service_finder;
import com.shaul.saidikaV3.services.service_finder_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/finder")
@RestController
public class service_finder_controller {
    @Autowired
    service_finder_service finder_service;

    @PostMapping()
    public service_finder addFinder(@RequestBody service_finder finder_request_model){
      return  finder_service.add_finder(finder_request_model);
    }

    @GetMapping() //TODO remove this nonsense
    public List<service_finder> get_all_finders(){
     return finder_service.get_all();
    }

}
