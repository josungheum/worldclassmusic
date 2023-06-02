package qss.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import qss.impl.KioskClientImpl;
import qss.impl.UploadImpl;
import qss.service.Qss;
import qss.vo.KioskClientVo;
import qss.vo.UploadVo;

/**
 * 다운로드 관리
 * <pre>
 * qss.controller
 *    |_ DownloadController.java
 *
 * </pre>
 * @date : 2018. 12. 25. 오전 12:19:47
 * @version :
 * @author : admin
 */
@Controller
public class DownloadController {
	@Resource(name = "uploadService")
	Qss uploadService = new UploadImpl();

	@Resource(name = "kioskClientService")
	Qss kioskClientService = new KioskClientImpl();

	@Value("#{serverProp['Server.upload.path']}")
	private String configPath;

	private static final Log logger = LogFactory.getLog(DownloadController.class);

	@RequestMapping(value = "download/File/{fileContentIdx}")
	@ResponseBody
	public void download(HttpServletRequest request, HttpServletResponse response, UploadVo uploadVo) throws Exception {

		String contextRealPath = request.getSession().getServletContext().getRealPath("");
		File filePathLocation = new File(contextRealPath);
		contextRealPath = filePathLocation.getParent();
		OutputStream out = null;
		FileInputStream in = null;
		try {
			if (uploadVo.getFileContentIdx() != "0") {
				uploadVo.setCaseString("File_getFileInfo");
				uploadVo = (UploadVo) uploadService.SelectData(uploadVo);

				//String filePath = "/app/resource/images"+uploadVo.getSavePath();
				String filePath = contextRealPath + uploadVo.getSavePath();
				String realfilename = uploadVo.getFileName();
				File downloadfile = new File(filePath);

				response.setContentType("application/octet-stream; charset=utf-8");
				response.setContentLength((int) downloadfile.length());

				response.setHeader("Content-Disposition", getDisposition(realfilename, ""));
				response.setHeader("Content-Transfer-Encoding", "binary");
				out = response.getOutputStream();
				in = new FileInputStream(downloadfile);
				// Spring framework 사용할 경우
				// FileCopyUtils.copy(fis, out);

				// 일반 자바/JSP 파일다운로드
				byte[] buf = new byte[8192];
				int bytesread = 0, bytesBuffered = 0;
				while ((bytesread = in.read(buf)) > -1) {
					out.write(buf, 0, bytesread);
					bytesBuffered += bytesread;
					if (bytesBuffered > 1024 * 1024) { // flush after 1MB
						bytesBuffered = 0;
						out.flush();
					}
				}
				out.flush();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				out.flush();
				out.close();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

	}

//	@SuppressWarnings("unchecked")
//	private String check_browser(HttpServletRequest request) {
//		String browser = "";
//		String header = request.getHeader("User-Agent");
//		// 신규추가된 indexof : Trident(IE11) 일반 MSIE로는 체크 안됨
//		if (header.indexOf("MSIE") > -1 || header.indexOf("Trident") > -1) {
//			browser = "ie";
//		}
//		// 크롬일 경우
//		else if (header.indexOf("Chrome") > -1) {
//			browser = "chrome";
//		}
//		// 오페라일경우
//		else if (header.indexOf("Opera") > -1) {
//			browser = "opera";
//		}
//		// 사파리일 경우
//		else if (header.indexOf("Apple") > -1) {
//			browser = "safari";
//		} else {
//			browser = "firfox";
//		}
//		return browser;
//	}

	private String getDisposition(String down_filename, String browser_check) throws UnsupportedEncodingException {
		String prefix = "attachment;filename=";
		String encodedfilename = null;

		if (browser_check.equals("ie")) {
			encodedfilename = URLEncoder.encode(down_filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser_check.equals("chrome")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < down_filename.length(); i++) {
				char c = down_filename.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodedfilename = sb.toString();
		} else {
			encodedfilename = "\"" + new String(down_filename.getBytes("UTF-8"), "8859_1") + "\"";
		}
		return prefix + encodedfilename;
	}

	@RequestMapping(value = "download/ClientFile/{IDX}")
	@ResponseBody
	public void clientDownload(HttpServletRequest request, HttpServletResponse response, KioskClientVo kioskClientVo) throws Exception {

		String contextRealPath = request.getSession().getServletContext().getRealPath("");
		File filePathLocation = new File(contextRealPath);
		contextRealPath = filePathLocation.getParent();
		OutputStream out = null;
		FileInputStream in = null;
		try {
//			if (kioskClientVo.getIDX() != 0) {
//				kioskClientVo.setCaseString("KioskClient_ClientInfo");
////				kioskClientVo = (KioskClientVo) kioskClientService.SelectData(kioskClientVo);
//
////				String filePath = "/app/resource/client/" + kioskClientVo.getSAVEFILENAME();
////				String filePath = "C:/qss/app/resource/client/" + kioskClientVo.getFILENAME();
////				String realfilename = kioskClientVo.getFILENAME();
////				File downloadfile = new File(filePath);
//
//				response.setContentType("application/octet-stream; charset=utf-8");
////				response.setContentLength((int) downloadfile.length());
////
////				response.setHeader("Content-Disposition", getDisposition(realfilename, ""));
////				response.setHeader("Content-Transfer-Encoding", "binary");
////				out = response.getOutputStream();
////				in = new FileInputStream(downloadfile);
//				// Spring framework 사용할 경우
//				// FileCopyUtils.copy(fis, out);
//
//				// 일반 자바/JSP 파일다운로드
//				byte[] buf = new byte[8192];
//				int bytesread = 0, bytesBuffered = 0;
//				while ((bytesread = in.read(buf)) > -1) {
//					out.write(buf, 0, bytesread);
//					bytesBuffered += bytesread;
//					if (bytesBuffered > 1024 * 1024) { // flush after 1MB
//						bytesBuffered = 0;
//						out.flush();
//					}
//				}
//				out.flush();
//			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (in != null) {
					in.close();
				}

				if(out != null) {
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

	}
}
