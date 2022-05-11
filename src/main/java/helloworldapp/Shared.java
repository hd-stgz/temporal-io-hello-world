// @@@SNIPSTART hello-world-project-template-java-shared-constants
package helloworldapp;

public interface Shared {
    String TASK_QUEUE = System.getenv().getOrDefault("TEMPORAL_TASK_QUEUE", "HELLO_WORLD_TASK_QUEUE");
    String TEMPORAL_ADDRESS = System.getenv().getOrDefault("TEMPORAL_CLI_ADDRESS", "localhost:7233");
    Boolean IS_LOCAL_TEMPORAL = !System.getenv().containsKey("TEMPORAL_CLI_ADDRESS");
}
// @@@SNIPEND
