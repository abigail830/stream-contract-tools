package com.github.abigail830.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URI;


public class StreamContractDownloaderTest {

    StreamContractDownloader downloader;

    @Before
    public void setUp() throws Exception {

        RestEndPoint restEndPoint = new RestEndPoint();
        restEndPoint.setBaseUrl(URI.create("http://127.0.0.1:8080/contracts/ContractProvider"));
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