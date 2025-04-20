package com.myorkes.order.config;

import com.myorkes.order.workers.*;
import com.netflix.conductor.client.worker.Worker;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.automator.TaskRunnerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class ConductorConfig {

    @Value("${conductor.server.url}")
    private String serverUrl;

    @Value("${conductor.security.client.key-id}")
    private String apiKey;

    @Value("${conductor.security.client.secret}")
    private String apiSecret;

    @Bean
    public ApiClient apiClient() {
        return new ApiClient(serverUrl, apiKey, apiSecret);
    }

    @Bean
    public OrkesClients orkesClients(ApiClient apiClient) {
        return new OrkesClients(apiClient);
    }

    @Bean
    public WorkflowClient workflowClient(OrkesClients orkesClients) {
        return orkesClients.getWorkflowClient();
    }

    @Bean(initMethod = "init")
    public TaskRunnerConfigurer taskRunnerConfigurer(
            OrkesClients orkesClients,
            InventoryWorker inventoryWorker,
            PaymentWorker paymentWorker,
            ShippingWorker shippingWorker,
            NotificationWorker notificationWorker
    ) {
        List<Worker> workers = List.of(
                inventoryWorker,
                paymentWorker,
                shippingWorker,
                notificationWorker
        );

        return new TaskRunnerConfigurer.Builder(orkesClients.getTaskClient(), workers)
                .withThreadCount(4)
                .build();
    }

}