package com.tms.tmsfileuploaddownload.service;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tms.tmsfileuploaddownload.properties.FileUploadProperties;

@Service
public class FileStorageSystemService implements IFileSystemStorage {

	private final Path dirLocation;

	@Autowired
	public FileStorageSystemService(FileUploadProperties fileUploadProperties) {
		this.dirLocation = Paths.get(fileUploadProperties.getLocation()).toAbsolutePath().normalize();
	}

	@Override
	@PostConstruct
	public void init() {

		try {
			Files.createDirectories(this.dirLocation);
		} catch (Exception ex) {
			System.out.println("Could not create upload dir! " + ex);
			// throw new FileStorageException("Could not create upload dir!");
		}

	}

	@Override
	public String saveFile(MultipartFile file) {
		String fileName=null;
		try {

			fileName = file.getOriginalFilename();
			Path dfile = this.dirLocation.resolve(fileName);
			Files.copy(file.getInputStream(), dfile, StandardCopyOption.REPLACE_EXISTING);

		} catch (Exception e) {
			System.out.println("Could not upload file" + e);
			// throw new FileStorageException("Could not upload file");
		}
		return fileName;
	}

	@Override
	public Resource loadFile(String fileName) {

		Path file;
		Resource resource=null;
		try {
			file = this.dirLocation.resolve(fileName).normalize();
			resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				System.out.println("Could not find file");
				// throw new FileNotFoundException("Could not find file");
			}
		} catch (MalformedURLException e) {
			System.out.println("Could not download file" + e);
			// throw new FileNotFoundException("Could not download file");
		}
		return resource;
	}

}
