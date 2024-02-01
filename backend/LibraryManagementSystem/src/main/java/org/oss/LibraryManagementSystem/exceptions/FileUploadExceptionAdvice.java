package org.oss.LibraryManagementSystem.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class FileUploadExceptionAdvice {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(RedirectAttributes redirectAttributes, MaxUploadSizeExceededException e, HttpServletRequest req) {
        redirectAttributes.addFlashAttribute("fileUploadMessage", "File is too large!, Limit is 2MB");

        if(req.getRequestURI().contains("saveBook")) {
            return "redirect:/books/add";
        } else {
            return "redirect:/books";
        }
    }
}
