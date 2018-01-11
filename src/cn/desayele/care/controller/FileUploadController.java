package cn.desayele.care.controller;

import cn.desayele.care.entity.TFileEntity;
import cn.desayele.care.entity.TStaffProfileEntity;
import cn.desayele.care.object.Loginer;
import cn.desayele.care.serviceimpl.FileService;
import cn.desayele.care.serviceimpl.StaffService;
import cn.desayele.care.util.Gettime;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/file")
public class FileUploadController {

    @Resource(name="staffService")
    private StaffService staffService;
    @Resource(name="fileService")
    private FileService fileService;

    private static String [] extensionPermit_img = {"jpeg","jpg","gif","png","bmp"};
    /**
     * 单个图片上传，多个请使用[]定义数组
     * @param request
     * @param response
     * @param myfile
     * @return
     */
    @RequestMapping("/uploadimg")
	public @ResponseBody String upimg(HttpServletRequest request, HttpServletResponse response,@RequestParam("myfile") CommonsMultipartFile myfile){
        String return_msg=null;
        Map map=new HashMap<String,Object>();
        try {
            if(!myfile.isEmpty()){
                String fileExtension= FilenameUtils.getExtension(myfile.getOriginalFilename());
                if(Arrays.asList(extensionPermit_img).contains(fileExtension)){
                    String type = myfile.getOriginalFilename().substring(myfile.getOriginalFilename().indexOf("."));
                    String filename = "IMG"+ Gettime.getRandom12() + type;
                    String path = request.getSession().getServletContext().getRealPath("/file/img/" + filename);
                    File destFile = new File(path);
                    FileUtils.copyInputStreamToFile(myfile.getInputStream(), destFile);//复制临时文件到指定目录下
                    map.put("msg","ok");
                    map.put("url","/file/img/"+filename);
                }else{
                    map.put("msg","error0");
                }
            }else{
                map.put("msg","error0");
            }
        } catch (Exception e) {
            map.put("msg","error0");
        }
        return_msg= JSON.toJSONString(map);
        return return_msg;
	}
	//删除文件
    @RequestMapping("/deletefile")
    public @ResponseBody Map deleteFile(HttpServletRequest request, HttpServletResponse response,@RequestParam("myfile") CommonsMultipartFile myfile){
        Map map=new HashMap<String,Object>();
        String filename=request.getParameter("filename");
        try {
            if(filename==null || filename.equals("")){
                map.put("msg","error0");
            }else{
                String filepath=request.getSession().getServletContext().getRealPath("/file/img/" + filename);
                File targetFile = new File(filepath);
                if (targetFile.isDirectory()) {
                    FileUtils.deleteDirectory(targetFile);
                } else if (targetFile.isFile()) {
                    targetFile.delete();
                }
                map.put("msg","ok");
            }
        } catch (Exception e) {
            map.put("msg","error0");
        }
        return map;
    }
    /**
     * 更新头像
     * @param request
     * @param response
     * @param myfile
     * @return
     */
    @RequestMapping("/userportrait")
    public @ResponseBody String userPortrait(HttpServletRequest request, HttpServletResponse response,@RequestParam("myfile") CommonsMultipartFile myfile){
        String return_msg=null;
        Map map=new HashMap<String,Object>();
        try {
            Loginer l=(Loginer)request.getSession().getAttribute("loginer");
            if(l==null){
                map.put("msg","error1");
            }else{
                if(!myfile.isEmpty()){
                    String fileExtension= FilenameUtils.getExtension(myfile.getOriginalFilename());
                    if(Arrays.asList(extensionPermit_img).contains(fileExtension)){
                        String type = myfile.getOriginalFilename().substring(myfile.getOriginalFilename().indexOf("."));
                        String filename = "IMG"+ Gettime.getRandom12() + type;
                        String path = request.getSession().getServletContext().getRealPath("/file/portrait/" + filename);
                        File destFile = new File(path);
                        FileUtils.copyInputStreamToFile(myfile.getInputStream(), destFile);//复制临时文件到指定目录下
                        String url=modifyPortrait(l.getOid(),"/file/portrait/"+filename);
                        map.put("msg","ok");
                        map.put("url",url);
                    }else{
                        map.put("msg","error0");
                    }
                }else{
                    map.put("msg","error0");
                }
            }
        } catch (Exception e) {
            map.put("msg","error0");
        }
        return_msg= JSON.toJSONString(map);
        return return_msg;
    }
    //更新头像资料并返回头像
    public String modifyPortrait(String oid,String portrait){
        TStaffProfileEntity tspe=staffService.getTStaffProfileEntityBySID(oid);
        if(tspe==null){//new
            tspe=new TStaffProfileEntity();
            String tspid="SP"+Gettime.getRandom12();
            tspe.setOid(tspid);
            tspe.setSportrait(portrait);
            tspe.setSid(oid);
        }else{//modify
            tspe.setSportrait(portrait);
        }
        staffService.addTStaffProfileEntity(tspe);
        return portrait;
    }
    /**
     * 添加产品图片
     * @param request
     * @param response
     * @param myfile
     * @return
     */
    @RequestMapping("/uploadproduct")
    public @ResponseBody String uploadProduct(HttpServletRequest request, HttpServletResponse response,@RequestParam("myfile") CommonsMultipartFile myfile){
        String return_msg=null;
        Map map=new HashMap<String,Object>();
        try {
            Loginer l=(Loginer)request.getSession().getAttribute("loginer");
            if(l==null){
                map.put("msg","error1");
            }else{
                if(!myfile.isEmpty()){
                    String fileExtension= FilenameUtils.getExtension(myfile.getOriginalFilename());
                    if(Arrays.asList(extensionPermit_img).contains(fileExtension)){
                        String type = myfile.getOriginalFilename().substring(myfile.getOriginalFilename().indexOf("."));
                        String filename = "IMG"+ Gettime.getRandom12() + type;
                        String path = request.getSession().getServletContext().getRealPath("/file/product/" + filename);
                        File destFile = new File(path);
                        FileUtils.copyInputStreamToFile(myfile.getInputStream(), destFile);//复制临时文件到指定目录下
                        String url="/file/product/"+filename;

                        map.put("msg","ok");
                    }else{
                        map.put("msg","error0");
                    }
                }else{
                    map.put("msg","error0");
                }
            }
        } catch (Exception e) {
            map.put("msg","error0");
        }
        return_msg= JSON.toJSONString(map);
        return return_msg;
    }
    //添加头像信息到file表
    public String addFile(String filename,String filepath,String filetype,String sid){
        TFileEntity tf=new TFileEntity();
        String oid="TF"+Gettime.getRandom12();
        tf.setOid(oid);
        tf.setFilename(filename);
        tf.setFileurl(filepath);
        tf.setFiletype(filetype);
        tf.setSid(sid);
        fileService.addTFileEntity(tf);
        return filepath;
    }
}
