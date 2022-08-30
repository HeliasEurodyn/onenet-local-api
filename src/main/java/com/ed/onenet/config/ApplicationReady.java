package com.ed.onenet.config;

import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class ApplicationReady implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${server.port}")
    private String serverPort;

    @SneakyThrows
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        System.out.println("Application started ... launching browser now");
        browse("http://localhost:" + this.serverPort + "/api/onenet.html");
    }

    public static void browse(String url) {
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
