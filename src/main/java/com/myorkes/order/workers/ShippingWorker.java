package com.myorkes.order.workers;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.sdk.workflow.task.InputParam;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;
import org.springframework.stereotype.Service;

@Service
public class ShippingWorker implements Worker {

    /*@WorkerTask("ship_order")
    public String shipOrder(@InputParam("orderId") String orderId) {
        String shipmentId = "ship-" + orderId;
        System.out.println("[ShippingWorker] Shipped order " + orderId + ", shipmentId=" + shipmentId);
        return shipmentId;
    }*/

    @Override
    public String getTaskDefName() {
        return "ship_order";
    }

    /**
     * For Shipping the order. It returns shipment ID.
     */
    @Override
    public TaskResult execute(Task task) {
        String orderId = (String) task.getInputData().get("orderId");
        String shipmentId = "ship-" + orderId;
        System.out.println("[ShippingWorker] Shipped order: " + orderId);

        TaskResult result = new TaskResult(task);
        result.setStatus(TaskResult.Status.COMPLETED);
        result.getOutputData().put("shipmentId", shipmentId);
        return result;
    }
}