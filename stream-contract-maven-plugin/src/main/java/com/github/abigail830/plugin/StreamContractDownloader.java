package com.github.abigail830.plugin;


import com.github.abigail830.Contract;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Response;

public class StreamContractDownloader {

    private static final Logger log = LoggerFactory.getLogger(StreamContractDownloader.class);

    private File targetContractDirectory;
    private RestEndPoint[] restEndPoints;

    public StreamContractDownloader(File targetContractDirectory, RestEndPoint[] restEndPoints) {
        this.targetContractDirectory = targetContractDirectory;
        this.restEndPoints = restEndPoints;
    }

    public void download() throws MojoFailureException, MojoExecutionException {
        if(!shouldDownloadContracts()){
            log.warn("Either targetContractDirectory or restEndPoints are missing " +
                    "while they should be mandatory for trigger streamConvert goal.");
            return;
        }

        log.info("Going to start download contract to file...");
        downloadContractToFile(this.targetContractDirectory);

    }

    private void downloadContractToFile(File targetRootDirectory) throws MojoFailureException {

        for(RestEndPoint restEndPoint : restEndPoints){

            if(restEndPoint.getBaseUrl()==null) return;

            Client client = ClientBuilder.newClient();
            WebTarget baseTarget = client.target(restEndPoint.getBaseUrl());
            baseTarget = getWebTargetWithQueryParam(restEndPoint, baseTarget);
            log.info("- Remote URL: {}", baseTarget.getUri().getPath());

            Invocation.Builder invocationBuilder =  baseTarget.request(restEndPoint.getRequestType());

            if("GET".equals(restEndPoint.getRestMethod())){
                Response response = invocationBuilder.get();
                if(response.getStatusInfo().getFamily()== Response.Status.Family.SUCCESSFUL){
                    String result = response.readEntity(String.class);
                    log.debug(result);
                    Gson gson = new Gson();
                    ArrayList<Contract> contractArrayList = gson.fromJson(result,
                            new TypeToken<List<Contract>>(){}.getType());

                    writeContractListJsonToFiles(contractArrayList, targetRootDirectory);
                }else{
                    log.warn("Fail to access RestEndPoint [{}] with sattus {}, no file would be generated",
                            restEndPoint.getBaseUrl(), response.getStatus() );
                }
            }else{
                log.warn("Only HTTP GET is supported at the moment.");
            }
        }
    }

    private WebTarget getWebTargetWithQueryParam(RestEndPoint restEndPoint, WebTarget baseTarget) {
        // Load up the query parameters if they exist
        if ( null != restEndPoint.getQueryParamMap() )
        {
            for ( String k : restEndPoint.getQueryParamMap().keySet() )
            {
                String param = restEndPoint.getQueryParamMap().get( k );
                baseTarget = baseTarget.queryParam( k, param );
                log.debug( String.format( "Param [%s:%s]", k, param ) );
            }
        }
        return baseTarget;
    }

    private void writeContractListJsonToFiles(List<Contract> contractList,
                                              File targetRootDirectory) throws MojoFailureException {

        try{
            for(Contract contract : contractList) {

                if(contract.getFilePath()!=null){
                    targetRootDirectory= new File(targetRootDirectory, contract.getFilePath());
                }

                File contractFile = createContractFile(
                        targetRootDirectory + "/" + contract.getFileName()+"."+contract.getFileExtension());
                FileUtils.writeStringToFile(contractFile, contract.getFileContent(), "UTF-8", false);
                log.info("Target file created: {}", contractFile.getAbsolutePath());
            }
        } catch (IOException e) {
            log.warn("Fail to write contract to target Directory.");
            throw new MojoFailureException(e.getMessage(), e.getCause());
        }
    }

    private File createContractFile(String fileName) throws IOException {
        File file = new File(fileName);
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        log.debug("Empty file created: {}", file.getAbsolutePath());
        return file;
    }

    private boolean shouldDownloadContracts(){
        return this.targetContractDirectory != null && Arrays.asList(this.restEndPoints).size()>0;
    }


}
