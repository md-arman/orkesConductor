package com.myorkes.order.workers;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.sdk.workflow.task.InputParam;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class InventoryWorker implements Worker {

    @Override
    public String getTaskDefName() {
        return "reserve_inventory";
    }

    /**
     * To reserves inventory for the given order having orderId>3 digits
     * Note: the task name is same as the one defined in the Orkes Conductor
     */
    @Override
    public TaskResult execute(Task task) {
        String orderId = (String) task.getInputData().get("orderId");
        System.out.println("[InventoryWorker] Reserving inventory for order Id: " + orderId);

        TaskResult result = new TaskResult(task);
        if (orderId.length()>3) {
            result.setStatus(TaskResult.Status.COMPLETED);
            result.getOutputData().put("reserved", true);
        }
        else {
            result.setStatus(TaskResult.Status.FAILED);
            result.getOutputData().put("reserved", false);
        }
        return result;
    }
}