package com.example.demo.services;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.Dimension;
import software.amazon.awssdk.services.cloudwatch.model.MetricDatum;
import software.amazon.awssdk.services.cloudwatch.model.PutMetricDataRequest;
import software.amazon.awssdk.services.cloudwatch.model.PutMetricDataResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PublishMetricService {
    CloudWatchClient cloudWatchClient;
    PublishMetricService(){
        cloudWatchClient = CloudWatchClient.builder()
                .region(Region.AP_SOUTH_1)
                .credentialsProvider(ProfileCredentialsProvider.create("vishal-ranaabhishek1947"))
                .build();
    }

    private class PublishMetricThread extends Thread {
        boolean trigger;
        int count = 0;
        CloudWatchClient cloudWatchClient;
        String namespace, metricName, instanceName;

        PublishMetricThread(CloudWatchClient cloudWatchClient, String namespace, String metricName, String instanceName) {
            trigger = true;
            this.cloudWatchClient = cloudWatchClient;
            this.namespace = namespace;
            this.metricName = metricName;
            this.instanceName = instanceName;
        }

        @Override
        public void run() {
            while (trigger) {
                try {
                    publishMetric();
                    Thread.sleep(2000);
                } catch (InterruptedException exp) {
                    System.out.println("Exception thrown");
                }
            }
        }


        private void publishMetric() {
            List<Dimension> dimensions = new ArrayList<>();
            dimensions.add(Dimension.builder().name("InstanceName").value(instanceName).build());
            MetricDatum metricDatum = MetricDatum.builder()
                    .metricName(metricName)
                    .dimensions(dimensions)
                    .timestamp(new Date().toInstant())
                    .value((double) getValue())
                    .build();

            PutMetricDataRequest putMetricDataRequest = PutMetricDataRequest.builder()
                    .metricData(metricDatum)
                    .namespace(namespace)
                    .build();
            PutMetricDataResponse putMetricDataResponse = cloudWatchClient.putMetricData(putMetricDataRequest);

            System.out.println("Metric published to cloudWatch : " + metricDatum.toString() + ", statusCode: " + putMetricDataResponse.sdkHttpResponse().statusCode());

        }

        private int getValue() {
            int factor = count / 30;
            count++;
            return 5 * (1 + factor);
        }

        public void setTrigger(boolean trigger){
            this.trigger =trigger;
        }
    }
    PublishMetricThread thread = null;
    public String startPublishingMetric(String namespace, String metricName, String instanceName) {
        // creates a new thread and start publishing records.
        String message = null;
        if ( thread != null){
            return  "An existing thread is already running, please kill it first";
        }
        thread = new PublishMetricThread(cloudWatchClient, namespace, metricName, instanceName);
        thread.start();
        return "Thread started";
    }

    public String stopPublishingMetric(){
        if ( thread == null){
            return "No Thread is active";
        }
        thread.setTrigger(false);
        thread = null;
        return "Thread stopped";
    }
}
