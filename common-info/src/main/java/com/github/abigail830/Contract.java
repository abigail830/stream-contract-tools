package com.github.abigail830;

public class Contract {

    /**
     * File fileName when generated into file
     */
    String fileName;

    String fileContent;

    String fileExtension;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }


    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "fileName='" + fileName + '\'' +
                ", fileContent='" + fileContent + '\'' +
                ", fileExtension='" + fileExtension + '\'' +
                '}';
    }
}
