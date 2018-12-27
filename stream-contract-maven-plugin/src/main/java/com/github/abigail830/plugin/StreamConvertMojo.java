package com.github.abigail830.plugin;

import com.google.common.net.MediaType;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.Map;

@Mojo(name = "streamConvert", requiresProject = false, defaultPhase = LifecyclePhase.COMPILE)
public class StreamConvertMojo extends AbstractMojo {

    static final String DEFAULT_CONTRACT_DIR = "${project.basedir}/contracts";
    /**
     * Directory where the generated spring contracts should be placed.
     */
    @Parameter(defaultValue = DEFAULT_CONTRACT_DIR)
    private File targetContractDirectory;

    /**
     * End point list to collect contract for generation
     */
    @Parameter
    private RestEndPoint[] restEndPoints;
    /**
     * Flag using to skip the plugin execute function
     */
    @Parameter(property = "stream.contract.plugin.skip", defaultValue = "false")
    private boolean skip;

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;


    public void execute() throws MojoExecutionException, MojoFailureException {

        if (this.skip) {
            getLog().info(String.format(
                    "Skipping Spring Cloud Contract Verifier execution: spring.cloud.contract.verifier.skip=%s",
                    this.skip));
            return;
        }

        new StreamContractDownloader(targetContractDirectory,
                restEndPoints)
                .download();


    }
}
