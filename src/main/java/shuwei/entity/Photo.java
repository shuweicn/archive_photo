package shuwei.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.File;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo {
    @JsonProperty("FileName")
    private String fileName;

    @JsonProperty("Directory")
    private String directory;

    @JsonProperty("FileModifyDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy:MM:dd HH:mm:ssX", timezone="Asia/Shanghai")
    private Date fileModifyDate;

    @JsonProperty("ModifyDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy:MM:dd HH:mm:ss", timezone="Asia/Shanghai")
    private Date modifyDate;

    @JsonProperty("CreateDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy:MM:dd HH:mm:ss", timezone="Asia/Shanghai")
    private Date createDate;

    @JsonProperty("DateTimeOriginal")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy:MM:dd HH:mm:ss", timezone="Asia/Shanghai")
    private Date dateTimeOriginal;

    @JsonProperty("ProfileCreator")
    private String profileCreator;

    @JsonProperty("Model")
    private String model;

    @JsonProperty("FileType")

    private String fileType;

    @JsonProperty("SHA256")
    private String sha256;

    public File getFile() {
        return  new File(String.format("%s/%s", this.directory, this.fileName));
    }

    public String toInfo() {
        String info = "";
        info = info + String.format("File:             %s\n", getFile());
        info = info + String.format("FileModifyDate:   %s\n", fileModifyDate);
        info = info + String.format("ModifyDate:       %s\n", modifyDate);
        info = info + String.format("CreateDate:       %s\n", createDate);
        info = info + String.format("DateTimeOriginal: %s\n", dateTimeOriginal);
        info = info + String.format("Model:            %s\n", model);
        info = info + String.format("ProfileCreator:   %s\n", profileCreator);
        info = info + String.format("FileType:         %s\n", fileType);
        info = info + String.format("SHA256            %s", sha256);
        return info;
    }
}
