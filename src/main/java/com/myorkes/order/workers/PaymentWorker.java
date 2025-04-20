package com.myorkes.order.workers;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.sdk.workflow.task.InputParam;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentWorker implements Worker {

    @Override
    public String getTaskDefName() {
        return "process_payment";
    }

    /**
     * Processes the payment and returns transaction ID.
     */
    @Override
    public TaskResult execute(Task task) {
        String orderId = (String) task.getInputData().get("orderId");
        Integer amount = (Integer) task.getInputData().get("amount");
        String txId = "tx-" + System.currentTimeMillis();

        TaskResult result = new TaskResult(task);
        boolean payStatus = false;
        try {
            System.out.println("[PaymentWorker] Payment in progress....");
            Thread.sleep(5000);
            payStatus = new Random().nextBoolean();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (!payStatus) {
            System.out.println("[PaymentWorker] Payment failed of INR " + amount
                    + " for Order Id: " + orderId + " against transaction Id: " + txId);
            result.setStatus(TaskResult.Status.FAILED);
            result.getOutputData().put("transactionId", txId);
        }
        else {
            System.out.println("[PaymentWorker] Processed payment of INR " + amount
                    + " for Order Id: " + orderId + " against transaction Id: " + txId);
            result.setStatus(TaskResult.Status.COMPLETED);
            result.getOutputData().put("transactionId", txId);
        }
        return result;
    }
}