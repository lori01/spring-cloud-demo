package com.daimeng.interceptor;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 设置一个@ControllerAdvice用来监控Multipart上传的文件大小是否受限，当出现此异常时在前端页面给出提示。
 * 利用@ControllerAdvice可以做很多东西，比如全局的统一异常处理等
 * @author daimeng
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MultipartException.class)
    public String handleError1(MultipartException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
        return "redirect:/file/uploadStatus";
    }
}
