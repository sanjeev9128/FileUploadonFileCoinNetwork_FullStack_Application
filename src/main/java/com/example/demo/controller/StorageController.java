package com.example.demo.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.StorageService;

@Controller
//@RequestMapping("/file")
public class StorageController {

	@Autowired
	private StorageService service;

	@GetMapping("/page")
	public String index() {
		System.out.println("testing...");
		return "upload";
	}

	@PostMapping("/upload")
	public String fileUpload(@RequestParam("file") MultipartFile file, Model model) {
		System.out.println("testing 222....");
		try {
			service.uploandFile(file);
			model.addAttribute("message", "File uploaded successfully.");
		} catch (Exception e) {
			model.addAttribute("message", "File upload failed: " + e.getMessage());

		}
		return "upload";
	}

	@PostMapping("/multiFileUpload")
	public String fileUpload(@RequestParam("files") MultipartFile[] files, Model model) {
		
		try {
			Arrays.asList(files).stream().forEach(file -> service.uploandFile(file));
			System.out.println("test multiple-controller");
			model.addAttribute("message1", "Multiple File Uploaded Successfully...");
		}catch(Exception e) {
			model.addAttribute("message1"," Multiple File Upload failed "+ e.getMessage());
		}
		

		
		return "upload";
	}

	@GetMapping("/download/{fileName}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {

		byte[] data = service.downloadFile(fileName);
		ByteArrayResource resource = new ByteArrayResource(data);
		return ResponseEntity.ok().contentLength(data.length).header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + fileName + "\"").body(resource);

	}

	@DeleteMapping("/delete/{fileName}")
	public ResponseEntity<String> deleteFile(@PathVariable String fileName) {

		return new ResponseEntity<String>(service.deleteFile(fileName), HttpStatus.OK);
	}
}
