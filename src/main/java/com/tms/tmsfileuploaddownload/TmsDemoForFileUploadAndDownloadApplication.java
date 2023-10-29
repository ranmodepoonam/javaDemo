package com.tms.tmsfileuploaddownload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.tms.tmsfileuploaddownload.properties.FileUploadProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileUploadProperties.class
})
public class TmsDemoForFileUploadAndDownloadApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmsDemoForFileUploadAndDownloadApplication.class, args);
	}

}
