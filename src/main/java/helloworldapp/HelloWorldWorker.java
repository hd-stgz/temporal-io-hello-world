// @@@SNIPSTART hello-world-project-template-java-worker
package helloworldapp;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorldWorker {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldWorker.class);

    public static void main(String[] args) {
        logger.info("worker: TEMPORAL_CLI_ADDRESS: {}", Shared.TEMPORAL_ADDRESS);
        WorkflowServiceStubs service = null;
        if (Shared.IS_LOCAL_TEMPORAL) {
            service = WorkflowServiceStubs.newLocalServiceStubs();
        } else {
            service = WorkflowServiceStubs.newServiceStubs(WorkflowServiceStubsOptions.newBuilder()
                    .setTarget(Shared.TEMPORAL_ADDRESS)
                    .build());
        }

        WorkflowClient client = WorkflowClient.newInstance(service);
        // Create a Worker factory that can be used to create Workers that poll specific Task Queues.
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(Shared.TASK_QUEUE);
        // This Worker hosts both Workflow and Activity implementations.
        // Workflows are stateful, so you need to supply a type to create instances.
        worker.registerWorkflowImplementationTypes(HelloWorldWorkflowImpl.class);
        // Activities are stateless and thread safe, so a shared instance is used.
        worker.registerActivitiesImplementations(new FormatImpl());
        // Start polling the Task Queue.
        factory.start();
    }
}
// @@@SNIPEND
