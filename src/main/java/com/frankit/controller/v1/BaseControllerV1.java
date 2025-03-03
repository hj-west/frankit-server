package com.frankit.controller.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BaseControllerV1.PATH_NAME)
public class BaseControllerV1 {
    public static final String PATH_NAME = "/api/v1";
}