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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy:MM:dd HH:mm:ssX")
    private Date fileModifyDate;

    @JsonProperty("ModifyDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy:MM:dd HH:mm:ss")
    private Date modifyDate;

    @JsonProperty("CreateDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy:MM:dd HH:mm:ss")
    private Date createDate;

    @JsonProperty("ProfileCreator")
    private String profileCreator;

    @JsonProperty("Model")
    private String model;

    @JsonProperty("FileType")
    private String fileType;

    public File getFile() {
        return  new File(String.format("%s/%s", this.directory, this.fileName));
    }

    public String toInfo() {
        String info = "";
        info = info + String.format("File:             %s\n", getFile());
        info = info + String.format("FileModifyDate:   %s\n", fileModifyDate);
        info = info + String.format("ModifyDate:       %s\n", modifyDate);
        info = info + String.format("Model:            %s\n", model);
        info = info + String.format("ProfileCreator:   %s\n", profileCreator);
        info = info + String.format("FileType:         %s", fileType);
        return info;
    }
}
