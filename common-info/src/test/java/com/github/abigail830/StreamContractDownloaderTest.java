package com.github.abigail830;

import com.github.abigail830.RestEndPoint;
import com.github.abigail830.StreamContractDownloader;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.net.URI;

@Ignore
public class StreamContractDownloaderTest {

    StreamContractDownloader downloader;

    @Before
    public void setUp() throws Exception {

        RestEndPoint restEndPoint = new RestEndPoint();
        restEndPoint.setBaseUrl(URI.create("http://contract-listener.saraqian.cn/v1/streamContracts"));
        RestEndPoint[] restEndPoints = new RestEndPoint[1];
        restEndPoints[0] = restEndPoint;
        downloader = new StreamContractDownloader(new File("target/contract"), restEndPoints);
    }

    @Test
    public void download() {
        try {
            downloader.download();
        } catch (MojoFailureException e) {
            e.printStackTrace();
        } catch (MojoExecutionException e) {
            e.printStackTrace();
        }
    }
}