package common;

import com.google.common.reflect.TypeToken;
import org.gradle.internal.impldep.com.google.gson.Gson;
import org.gradle.internal.impldep.com.google.gson.JsonSyntaxException;
import org.gradle.internal.impldep.org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StreamContractDownloader {

    private static final Logger log = LoggerFactory.getLogger(StreamContractDownloader.class);

    private File targetContractDirectory;
    private RestEndPoint[] restEndPoints;

    public StreamContractDownloader(File targetContractDirectory, RestEndPoint[] restEndPoints) {
        this.targetContractDirectory = targetContractDirectory;
        this.restEndPoints = restEndPoints;
    }

    public void download() {
        if(!shouldDownloadContracts()){
            log.warn("Either targetContractDirectory or restEndPoints are missing " +
                    "while they should be mandatory for trigger streamConvert goal.");
            return;
        }

        log.info("Going to start download contract to file...");
        downloadContractToFile(this.targetContractDirectory);
        log.info("streamConvert goal complete.");

    }

    private void downloadContractToFile(File targetRootDirectory) {

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
                    try{
                        String result = response.readEntity(String.class);
                        log.debug(result);
                        Gson gson = new Gson();
                        ArrayList<Contract> contractArrayList = gson.fromJson(result,
                                new TypeToken<List<Contract>>(){}.getType());

                        writeContractListJsonToFiles(contractArrayList, targetRootDirectory);

                    }catch(JsonSyntaxException e){
                        log.warn("Fail to parse response content, please double check the response format.");
                    }

                }else{
                    log.warn("Fail to access common.RestEndPoint [{}] with sattus {}, no file would be generated",
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
                                              File targetRootDirectory)  {

        try{
            for(Contract contract : contractList) {

                File contractDir = targetRootDirectory;

                if(contract.getFilePath()!=null){
                    contractDir= new File(targetRootDirectory, contract.getFilePath());
                }

                String f_name = constructureFileName(contractDir, contract.getFileName(),
                        contract.getFileExtension());
                if(f_name!=null){
                    contractDir = createContractFile(f_name);
                    FileUtils.writeStringToFile(contractDir, contract.getFileContent(),
                            "UTF-8", false);
                    log.info("Target file created: {}", contractDir.getAbsolutePath());
                }else{
                    log.warn("No file would be generated given no fileName provided in common.RestEndPoint.");
                    return;
                }
            }
        } catch (IOException e) {
            log.warn("Fail to write contract to target Directory.");
//            throw new MojoFailureException(e.getMessage(), e.getCause());
        }
    }

    private String constructureFileName(File contractDir, String fileName, String fileExtension){

        if(fileName!=null){
            if(fileExtension!=null) {
                return contractDir + "/" + fileName + "." + fileExtension;
            }else{
                return contractDir + "/" + fileName;
            }
        }else{
            log.warn("No file would be generated given no fileName provided in common.RestEndPoint.");
            return null;
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
