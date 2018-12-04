package com.webshop;

import com.webshop.services.CategoryService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

@WebListener
public class CreateCategoryServer implements ServletContextListener {

    Server server;

    @Autowired
    private CategoryService service;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        server = ServerBuilder
                .forPort(8082)
                .addService(new CategoryCreateServiceImpl(service)).build();

        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        server.shutdown();
    }
}
