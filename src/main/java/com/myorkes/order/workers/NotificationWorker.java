package com.myorkes.order.workers;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.sdk.workflow.task.InputParam;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;
import org.springframework.stereotype.Service;

@Service
public class NotificationWorker implements Worker {

    @Override
    public String getTaskDefName() {
        return "send_notification";
    }

    @Override
    public TaskResult execute(Task task) {
        String orderId = (String) task.getInputData().get("orderId");
        System.out.println("[NotificationWorker] Notifying customer about order: " + orderId);

        TaskResult result = new TaskResult(task);
        result.setStatus(TaskResult.Status.COMPLETED);
        result.getOutputData().put("notificationSent", "success");
        return result;
    }
}