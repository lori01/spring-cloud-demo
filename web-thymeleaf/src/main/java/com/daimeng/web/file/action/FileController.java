package com.daimeng.web.file.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.daimeng.util.Constants;
import com.daimeng.util.WaterMarkUtils;

@Controller
@RequestMapping("/file")
public class FileController {
	
	@Value("${upload_path}")
	private String uploadPath;

	@GetMapping("/upload")
	public String list(Model model) {
		return "file/upload";
	}
	
	@GetMapping("/water")
	public String water(Model model) {
		return "file/water";
	}
	
	@GetMapping("/pageStatus")
	public String list(Model model,RedirectAttributes redirectAttributes) {
		return "file/pageStatus";
	}
	
	@PostMapping("/doUpload") 
	//@ResponseBody
	public String doUpload(@RequestParam("file") MultipartFile file, String type, String context, 
            RedirectAttributes redirectAttributes,HttpServletResponse response) {
		if (file.isEmpty()) {
	        redirectAttributes.addFlashAttribute("message", "请选择一个文件进行上传！");
	        return "redirect:/file/pageStatus";
	    }

	    try {
	        // Get the file and save it somewhere
	        byte[] bytes = file.getBytes();
	        if(uploadPath == null || "".equals(uploadPath)){
	        	redirectAttributes.addFlashAttribute("message", "路径不存在！");
		        return "redirect:/file/pageStatus";
	        }
	        Path path = Paths.get(uploadPath + file.getOriginalFilename());
	        Files.write(path, bytes);
	        if(type != null && "water".equals(type)){
	        	String newfile = WaterMarkUtils.addWaterMark(path.toString(), context);
	        	//redirectAttributes.addFlashAttribute("message","添加水印成功：'" + newfile + "'");
	        	down(response, newfile);
	        	return null;
	        }else
	        redirectAttributes.addFlashAttribute("message","成功上传文件：'" + file.getOriginalFilename() + "'");

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return "redirect:/file/pageStatus";
	}
	
	public void down(HttpServletResponse response, String filePath){
		File dfile = new File(filePath);
		try {
			if (dfile.exists()) {
				// 配置文件下载
	            response.setHeader("content-type", "application/octet-stream");
	            response.setContentType("application/octet-stream");
	            // 下载文件能正常显示中文
	            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(dfile.getName(), "UTF-8"));

	            // 实现文件下载
	            byte[] buffer = new byte[1024];
	            FileInputStream fis = null;
	            BufferedInputStream bis = null;
	            try {
	                fis = new FileInputStream(dfile);
	                bis = new BufferedInputStream(fis);
	                OutputStream os = response.getOutputStream();
	                int i = bis.read(buffer);
	                while (i != -1) {
	                    os.write(buffer, 0, i);
	                    i = bis.read(buffer);
	                }
	                Constants.println("Download the song successfully!");
	            }
	            catch (Exception e) {
	                Constants.println("Download the song failed!");
	            }
	            finally {
	                if (bis != null) {
	                    try {
	                        bis.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	                if (fis != null) {
	                    try {
	                        fis.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
			}
		} catch (Exception e) {
			Constants.println(e.getMessage());
		}
	}
	
}
