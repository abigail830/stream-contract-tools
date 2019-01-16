
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class CleanContractTask extends DefaultTask {

    @TaskAction
    public void cleanContract(){
        getLogger().info("This is in cleanContract task");
    }
}
