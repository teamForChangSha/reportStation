package com.jxxp.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jxxp.service.CaseService;

/*
 * @author cj
 */
@Controller("caseController")
@RequestMapping("/case")
public class CaseController {
	@Resource
	private CaseService caseService;
	/*** 
     * 添加案件 
     * @author cj
     * @param 
     * @return 
     */  
    @RequestMapping("addCase")
	public String addCase(HttpServletRequest request, HttpServletResponse response) {
    	
//    	caseService.saveCaseInfo(caseInfo);
    	return null;
	}
	
	
	/*** 
     * 上传文件 用@RequestParam注解来指定表单上的file为MultipartFile 
     * @author cj
     * @param file 
     * @return 
     */  
    @RequestMapping("fileUpload")  
    public String fileUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request, HttpServletResponse response) {  
        response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if (!file.isEmpty()) {  
				String rootPath = request.getSession().getServletContext().getRealPath("/") + "fileupload/temp/";
		        String trackingNo = request.getParameter("trackingNo");
				saveTempFile(rootPath,file,trackingNo );
		        out.print(getTempFileNames(rootPath, trackingNo));
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;  
    }  

    //保存临时文件
    public void saveTempFile(String rootPath,MultipartFile file,String trackingNo) throws IllegalStateException, IOException {
    	String fileDir = rootPath + File.separator + trackingNo;
    	File dir = new File(fileDir);
    	if(!dir.exists()) {
    		dir.mkdirs();
    	}
    	String fileName = fileDir + File.separator + file.getOriginalFilename();
        // 转存文件   
        file.transferTo(new File(fileName));
    }
    
    //获取临时文件列表
    public String getTempFileNames(String rootPath,String trackingNo) {
    	String fileNames = "";
    	String path = rootPath + trackingNo;
    	File root = new File(path);
    	if(root.exists()) {
    		File[] files = root.listFiles();
    		for (int i = 0; i < files.length; i++) {
				fileNames += files[i].getName() + ",";
			}
    	}
    	if(fileNames.length() > 0) {
    		fileNames = fileNames.substring(0, fileNames.length() - 1);
    	}
    	return fileNames;
    }
    
    
}
