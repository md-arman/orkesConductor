package com.myorkes.order.controller;

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import io.orkes.conductor.client.WorkflowClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OrderController {

    private final WorkflowClient workflowClient;

    @Autowired
    public OrderController(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    /**
     * This endPoint starts the order processing workflow in the Conductor.
     * @return workflowInstanceId - this will be returned (same will be on Conductor UI).
     */
    @PostMapping("/startOrder")
    public String startOrder(
            @RequestParam String orderId,
            @RequestParam Integer amount) {
        Map<String, Object> input = new HashMap<>();
        input.put("orderId", orderId);
        input.put("amount", amount);

        StartWorkflowRequest request = new StartWorkflowRequest();
        //workflow name same as the one defined in Conductor
        request.setName("order_processing_workflow");
        request.setVersion(1);
        request.setInput(input);

        return workflowClient.startWorkflow(request);
    }
}