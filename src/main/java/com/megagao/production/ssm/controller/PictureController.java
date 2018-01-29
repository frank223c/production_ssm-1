package com.megagao.production.ssm.controller;

import com.megagao.production.ssm.service.PictureService;
import com.megagao.production.ssm.util.CollectionsFactory;
import com.megagao.production.ssm.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * 
 * created on 2016年9月27日 
 *
 * 上传图片处理
 *
 * @author  megagao
 * @version  0.0.1
 */
@RestController
public class PictureController {

	@Autowired
	private PictureService pictureService;
	
	@RequestMapping("/pic/upload")
	public String pictureUpload(MultipartFile uploadFile) throws Exception{
		Map<String,Object> result = pictureService.uploadPicture(uploadFile);
		//为了保证功能的兼容性，需要把Result转换成json格式的字符串。
		String json = JsonUtils.objectToJson(result);
		return json;
	}
	
	@RequestMapping("/pic/delete")
	public String pictureDelete(@RequestParam String picName) throws Exception{
		pictureService.deleteFile(picName);
		Map<String,Object> result = CollectionsFactory.newHashMap();
		result.put("data", "success");
		String json = JsonUtils.objectToJson(result);
		return json;
	}
	@RequestMapping("/pic/{picName}")
	public String picByPicName(@PathVariable String picName, ServletResponse response) throws IOException {
		String filePath = "D:\\upload\\temp\\img\\";
		FileInputStream hFile = new FileInputStream(filePath+picName+".png"); // 以byte流的方式打开文件
		 int i = hFile.available(); // 得到文件大小
		 byte data[] = new byte[i];
		 hFile.read(data); // 读数据
		 hFile.close();
		 response.setContentType("image/*"); // 设置返回的文件类型
		 OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
		 toClient.write(data); // 输出数据
		 toClient.close();
		return null;
	}
}
