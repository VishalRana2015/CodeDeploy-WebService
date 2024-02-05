package com.example.demo.controllers;

import com.example.demo.services.PublishMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.Dimension;
import software.amazon.awssdk.services.cloudwatch.model.MetricDatum;
import software.amazon.awssdk.services.cloudwatch.model.PutMetricDataRequest;
import software.amazon.awssdk.services.xray.model.Http;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class MetricUploaderTrigger {
    Thread thread = null;
    @Autowired
    PublishMetricService publishMetricService;

    @GetMapping("/trigger")
    public ResponseEntity<String> startPublishingMetric() {
        String message = publishMetricService.startPublishingMetric("CustomMetric", "WebServiceMetric", "VishalLaptop");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/stopTrigger")
    public ResponseEntity<String> stopPublishingMetric() {
        String message = publishMetricService.stopPublishingMetric();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
