package qss.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import qss.impl.UploadImpl;
import qss.service.Qss;
import qss.util.DateUtil;
import qss.util.FileUtil;
import qss.vo.UploadVo;

/**
 * 업로드 관리
 * <pre>
 * qss.controller
 *    |_ UploadController.java
 *
 * </pre>
 * @date : 2018. 12. 25. 오전 12:22:52
 * @version :
 * @author : admin
 */
@Controller
public class UploadController {
	@Resource(name="uploadService")
	Qss uploadService = new UploadImpl();

	@Value("#{serverProp['Server.upload.path']}")
	private String configPath;

	@Value("#{serverProp['Server.download.path']}")
	private String downloadPath;

	@Value("#{serverProp['Server.image.playtime']}")
	private int imgPlayTime;

	@Value("#{serverProp['Server.ffmpeg.path']}")
	private String ffmpegPath;

	private static final Log logger = LogFactory.getLog(UploadController.class);

	@RequestMapping(value = "Upload/View/{SaveFolder}/{Extens}", method = RequestMethod.GET)
	public String FileUploadSingle(@ModelAttribute("UploadVo")UploadVo uploadVo, ModelMap model) throws Exception
	{
		model.addAttribute(uploadVo);

		return "/Upload/View";
	}

	@RequestMapping(value = "Upload/Save")
	@ResponseBody
	public UploadVo Save(UploadVo uploadVo, HttpServletRequest request, HttpSession session) throws Exception, IOException
	{
		String uploadPath = configPath + "/" + uploadVo.getSaveFolder();

//		String uploadPath = request.getContextPath() + configPath + "/" + uploadVo.getSaveFolder();

		String contextRealPath = request.getSession().getServletContext().getRealPath("");
		UploadVo uv = new UploadVo();
		File directory = new File(contextRealPath);
		MultipartFile file;
		MultipartFile[] files = uploadVo.getUploadFile();
		contextRealPath = directory.getParent();

		if(files != null) {
			String fileName = "";
			String saveFileName = "";
			long fileSize = 0;

			try {
				for(int i = 0; i < files.length; i++) {
					file = files[i];

					if(file == null) {
						continue;
					}

					fileName = file.getOriginalFilename();
					fileSize = file.getSize();
					saveFileName = UUID.randomUUID().toString().replace("-","");

					if (fileName != "") {
						String fileExt = FileUtil.getFileExt(fileName);
						boolean chkFile = false;
						String[] ext = uploadVo.getExtens().split(",");

						for (int j=0; j < ext.length; j++) {
							if (org.apache.commons.lang3.StringUtils.upperCase(ext[j]).equals(org.apache.commons.lang3.StringUtils.upperCase(fileExt))) {
								chkFile = true;
								break;
							}
						}
						//유효 파일만 업로드.
						if (chkFile) {
							//파일명UUID.jpg 형태로 저장.
							//String serverPath = uploadPath + "/" + DateUtil.getDateNow() + "/";

							String serverPath = contextRealPath + uploadPath + "/" + DateUtil.getDateNow() + "/";
							String savePath = serverPath + saveFileName + "." + fileExt;
							FileUtil fileWrite = new FileUtil();
							String serverFileName = null;
							serverFileName = fileWrite.write(savePath, file.getInputStream());
//							serverFileName = fileWrite.write(savePath, file.getBytes());
							int idx = serverFileName.indexOf(saveFileName.toString());
							String fileType = file.getContentType().split("/")[0].toLowerCase().toString();

							if (fileType.equals("video")) {
								fileExt = "png";
							}
							else {
								fileExt = fileExt.toLowerCase().toString();
							}

							String saveThumbnailPath =  serverPath + saveFileName + "_thumb." + fileExt.toLowerCase().toString();
							int playtime = Thumnail(savePath, saveThumbnailPath, fileType, fileName, fileExt.toLowerCase().toString());
							String checkSum = getFileChecksum(savePath);

							serverFileName = serverFileName.substring(idx);
							uploadVo.setFileSize(fileSize);
							uploadVo.setPlayTime(playtime);
							uploadVo.setFileName(fileName);
							uploadVo.setFileSaveName(serverFileName);
							uploadVo.setSavePath(downloadPath + "/" + uploadVo.getSaveFolder() + "/" + DateUtil.getDateNow() + "/" + serverFileName);
							uploadVo.setThumbnailPath(downloadPath + "/" + uploadVo.getSaveFolder() +  "/" + DateUtil.getDateNow() + "/" + saveFileName + "_thumb." + fileExt);
							uploadVo.setSaveFolder(uploadVo.getSaveFolder());
							uploadVo.setCheckSum(checkSum);

							//uploadVo.setRegUser((Integer)session.getAttribute("IDX"));

							uploadVo.setCaseString("File_Create");
							uploadVo.setRegUser((String)session.getAttribute("id"));
							String returnKey = uploadService.InsertReturnKeyData(uploadVo);

							if (returnKey != null) {
								uv.setFileContentIdx(returnKey);
								uv.setFileName(uploadVo.getFileName());
								uv.setFileSaveName(uploadVo.getFileSaveName());
								uv.setSavePath(uploadVo.getSavePath());
								uv.setThumbnailPath(uploadVo.getThumbnailPath());
								uv.setFileSize(uploadVo.getFileSize());
								uv.setCheckSum(uploadVo.getCheckSum());
								uv.setPlayTime(uploadVo.getPlayTime());

							}
							else {
								uv = null;
							}
						}
						else {
							uv = null;
						}
					}
					else {
						uv = null;
					}
				}
			}
			catch (Exception e) {
				logger.error(e.getMessage());
				uv.setErrorCode(-1);
				uv.setMessages("서버오류가 발생하였습니다.");
				uv = null;
			}
		}

		return uv;
	}

	private int Thumnail(String savePath, String serverThumbnailPath, String fileType, String fileName, String fileExt)  throws Exception, IOException
	{
		int playTime = 0;

		if (fileType.equals("video")) {
			String[] cmdLine = new String[] {
					ffmpegPath,
					"-i",
					savePath,
					"-ss",
					"00:00:07",
					"-s",
					"134*134",
					"-f",
					"image2",
					"-vframes",
					"1",
					serverThumbnailPath
			};
			ProcessBuilder pb = new ProcessBuilder(cmdLine);
			pb.redirectErrorStream(true);
			Process process = null;

			try {
				process = pb.start();
			}
			catch (Exception e) {
				logger.error(e.getMessage());
			    process.destroy();
			}

			exhaustInputStream(process.getInputStream());

			try {
				process.waitFor();
			}
			catch (InterruptedException e) {
				process.destroy();
			}

			if (process.exitValue() != 0) {
				System.out.println("변환 중 에러 발생");
			}
			if (serverThumbnailPath.length() == 0) {
				System.out.println("변환된 파일의 사이즈가 0임");
			}

			process.destroy();
			playTime = GetVideoPlayTime(savePath);

			if (playTime == 0) playTime = imgPlayTime;
		}
		else {
			File image = new File(savePath);
			File thumbnail = new File(serverThumbnailPath);

			if (image.exists()) {
//				BufferedImage bufferedImage = new BufferedImage(134,134, BufferedImage.TYPE_INT_BGR);


				thumbnail.getParentFile().mkdirs();
//				Thumbnails.of(image).size(134, 134).toFile(thumbnail);
				// 20190326 자바 기본 라이브러리의 사용자 지정 색 미지원으로 인해 라이브러리 변경
//				ImageIO.write(bufferedImage, fileName, thumbnail);

				FileInputStream  file = new FileInputStream (savePath);
		        Thumbnails.of(new InputStream[] { new BufferedInputStream((file)) }).size(134, 134).outputFormat(fileExt).useExifOrientation(false).toFile(thumbnail);

			}

			playTime = imgPlayTime;
		}

		return playTime;
	}

	private int GetVideoPlayTime (String savePath)
	{
		String duration = "";

		try {
			Process process = new ProcessBuilder(
					ffmpegPath,
					"-i",
					savePath
			).start();

			Scanner sc = new Scanner(process.getErrorStream());
			Pattern durPattern = Pattern.compile("(?<=Duration: )[^,]*");

			duration = sc.findWithinHorizon(durPattern, 0);

	        sc.close();
		}
		catch (IOException e) {
			logger.error(e.getMessage());
		}

		return parseDuration(duration);
	}

	private int parseDuration(String duration)
	{
		String hour = duration.split(":")[0];
		String min = duration.split(":")[1];
		String sec = duration.split(":")[2];
		String msec = sec.split("\\.")[1];
		int returnDuration = Integer.parseInt(hour) * 3600 + Integer.parseInt(min) * 60 + Integer.parseInt(sec.split("\\.")[0]);

		returnDuration = returnDuration*1000 + Integer.parseInt(msec);

		return returnDuration;
	}

	@SuppressWarnings("unused")
	private void exhaustInputStream(final InputStream is)
	{
         try {
        	 InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr);
             String line;

             while ((line = br.readLine()) != null);
         }
         catch(IOException e) {
        	 logger.error(e.getMessage());
         }
	}

	private String getFileChecksum(String savePath)
	{
		String checksum;
		byte[] byteData = createChecksum(savePath);

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
		}

		checksum = sb.toString();

		return checksum;
	}

	private byte[] createChecksum(String savePath)
	{
		byte[] result = null;
		InputStream fis;

		try {
			fis = new FileInputStream(savePath);
			byte[] buffer = new byte[1024];
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			int numRead = 0;

			do {
				numRead = fis.read(buffer);

				if (numRead > 0) {
					md.update(buffer, 0, numRead);
				}
			}
			while (numRead != -1);

			fis.close();
			result = md.digest();
		}
		catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
		catch (NoSuchAlgorithmException e) {
			System.out.println(e.toString());
		}
		catch (IOException e) {
			System.out.println(e.toString());
		}

		return result;
	}
}
