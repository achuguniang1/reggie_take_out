package com.itheima.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author ：sean
 * @date ：Created in 2022/4/19
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

	@Value("${reggie.path}")
	private String basePath;

	@PostMapping("/upload")
	public R<String> upload(MultipartFile file) {
		log.info("文件:{}",file.getOriginalFilename());
		//原始文件名和后缀名
		String originalFilename = file.getOriginalFilename();//abc.jpg
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

		//使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
		String fileName = UUID.randomUUID().toString() + suffix;//dfsdfdfd.jpg

		//创建一个文件保存的基本目录
		File dir = new File(basePath);
		//判断当前目录是否存在
		if(!dir.exists()){
			//目录不存在，需要创建
			dir.mkdirs();
		}
		try {
			//将临时文件转存到指定位置
			file.transferTo(new File(basePath ,fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.success(fileName);
	}

	@GetMapping("/download")
	public void download(String name, HttpServletResponse response){
		try {
			//输入流，通过输入流读取文件内容
			File file = new File(basePath + name);
			if(file.exists()){
				FileInputStream fileInputStream
					= new FileInputStream(file);
				//输出流，通过输出流将文件写回浏览器
				ServletOutputStream outputStream = response.getOutputStream();
				response.setContentType("image/jpeg");

				int len = 0;
				byte[] bytes = new byte[1024];
				while ((len = fileInputStream.read(bytes)) != -1){
					outputStream.write(bytes,0,len);
					outputStream.flush();
				}
				//关闭资源
				outputStream.close();
				fileInputStream.close();
			}else{
				response.setContentType("image/jpeg");
				response.getOutputStream().write(new byte[]{});
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
