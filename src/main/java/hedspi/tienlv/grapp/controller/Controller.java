package hedspi.tienlv.grapp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@org.springframework.stereotype.Controller
public class Controller {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "pages/staypoint";
	}

	@RequestMapping(value = "/staypoint", method = RequestMethod.GET)
	public String staypoint() {
		return "pages/staypoint";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/staypoint")
	public String handleFileUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {

		// Root Directory.
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		File uploadRootDir = new File(uploadRootPath);
		System.out.println("uploaded folder:" + uploadRootPath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		if (!file.isEmpty()) {
			try {
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(uploadRootPath + "/" + file.getOriginalFilename())));
				FileCopyUtils.copy(file.getInputStream(), stream);
				stream.close();
			} catch (Exception e) {
			}
		}

		return "redirect:/";
	}

}
