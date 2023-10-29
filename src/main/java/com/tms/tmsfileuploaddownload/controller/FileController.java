package com.tms.tmsfileuploaddownload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tms.tmsfileuploaddownload.model.FileResponse;
import com.tms.tmsfileuploaddownload.service.IFileSystemStorage;

@Controller
@RequestMapping("/api")
public class FileController {

	@Autowired
	private IFileSystemStorage fileSytemStorage;
	
	@PostMapping("/uploadfile")
    public ResponseEntity<FileResponse> uploadSingleFile (@RequestParam("file") MultipartFile file) {
        String upfile = fileSytemStorage.saveFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/download/")
                .path(upfile)
                .toUriString();
        
        System.out.println("File uploaded with success!");
        
        return ResponseEntity.status(HttpStatus.OK).body(new FileResponse(upfile,fileDownloadUri,"File uploaded with success!"));
	 }
	 
	 @GetMapping("/download/{filename:.+}")
     public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        
         Resource resource = fileSytemStorage.loadFile(filename);

         return ResponseEntity.ok()
                 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                 .body(resource);
     }
}
