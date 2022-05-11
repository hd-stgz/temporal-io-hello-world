// @@@SNIPSTART hello-world-project-template-java-workflow-initiator
package helloworldapp;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class InitiateHelloWorld {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldWorker.class);

    public static void main(String[] args) throws Exception {
        logger.info("client: TEMPORAL_CLI_ADDRESS: {}", Shared.TEMPORAL_ADDRESS);
        WorkflowServiceStubs service = null;
        if (Shared.IS_LOCAL_TEMPORAL) {
            service = WorkflowServiceStubs.newLocalServiceStubs();
        } else {
            service = WorkflowServiceStubs.newServiceStubs(WorkflowServiceStubsOptions.newBuilder()
                    .setTarget(Shared.TEMPORAL_ADDRESS)
                    .build());
        }
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setWorkflowId(String.format("%s-%s", "hello-world", UUID.randomUUID().toString()))
                .setTaskQueue(Shared.TASK_QUEUE)
                .build();
        // WorkflowStubs enable calls to methods as if the Workflow object is local, but actually perform an RPC.
        HelloWorldWorkflow workflow = client.newWorkflowStub(HelloWorldWorkflow.class, options);
        // Synchronously execute the Workflow and wait for the response.
        String greeting = workflow.getGreeting("World");
        System.out.println(greeting);
        System.exit(0);
    }
}
// @@@SNIPEND
