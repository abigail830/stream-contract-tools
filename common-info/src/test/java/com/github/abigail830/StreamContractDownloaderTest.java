package com.github.abigail830;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


public class StreamContractDownloaderTest {

    StreamContractDownloader downloader;

    @Before
    public void setUp() throws Exception {

        RestEndPoint restEndPoint = new RestEndPoint();
        restEndPoint.setBaseUrl(URI.create("http://contract-listener.saraqian.cn/v1/streamContracts"));
        Map<String, String> queryParamMap = new HashMap<String, String>();
        queryParamMap.put("providerID", "d6bd63cf-898b-4f50-92d1-93d02b82ca0f");
        queryParamMap.put("providerSystem","1");
        restEndPoint.setQueryParamMap(queryParamMap);

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