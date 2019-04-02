package com.daimeng.web.file.action;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/file")
public class FileController {
	
	private static String UPLOADED_FOLDER = "D:/java_test/";

	@GetMapping("/page")
	public String list(Model model) {
		return "file/upload";
	}
	
	@GetMapping("/uploadStatus")
	public String list(Model model,RedirectAttributes redirectAttributes) {
		return "file/uploadStatus";
	}
	
	@PostMapping("/upload") 
	//@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
	        redirectAttributes.addFlashAttribute("message", "请选择一个文件进行上传！");
	        return "redirect:/file/uploadStatus";
	    }

	    try {
	        // Get the file and save it somewhere
	        byte[] bytes = file.getBytes();
	        Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
	        Files.write(path, bytes);

	        redirectAttributes.addFlashAttribute("message",
	                "成功上传文件：'" + file.getOriginalFilename() + "'");

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return "redirect:/file/uploadStatus";
	}
	
}
