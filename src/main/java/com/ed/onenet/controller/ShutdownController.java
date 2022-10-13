package com.ed.onenet.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Shutdown", description = "Shutdown the application")
public class ShutdownController implements ApplicationContextAware {
    private ApplicationContext context;

    @PostMapping("/shutdown")
    public void shutdownContext() {
        ((ConfigurableApplicationContext) context).close();
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context = ctx;

    }

}
