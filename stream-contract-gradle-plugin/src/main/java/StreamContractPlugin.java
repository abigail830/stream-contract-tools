import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class StreamContractPlugin implements Plugin<Project> {


    @Override
    public void apply(Project target) {
        target.getLogger().info("This is in StreamContractPlugin");
        target.getTasks().create("cleanContract", CleanContractTask.class);

    }
}
