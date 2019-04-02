package com.jsx.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.jsx.model.Page;
import com.jsx.model.User;
import com.jsx.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@SuppressWarnings("finally")
	@RequestMapping("addInfo")
	public String add(User user,HttpServletRequest request,@RequestParam MultipartFile pictureFile)throws Exception{
		try {			
//			user.setId(UUID.randomUUID().toString());
			System.out.println(user.getId() + ":::::" + user.getUsername() + ":::::" + user.getPassword());
			//使用UUID给图片重命名，并去掉四个“-”
			String name = UUID.randomUUID().toString().replaceAll("-", "");
			//获取文件的扩展名
			String ext = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
			if(ext==null) {
				user.setImage(null);
			}
			else {
			//设置图片上传路径
			String url = request.getSession().getServletContext().getRealPath("/upload");
			System.out.println(url);
			//以绝对路径保存重名命后的图片
			pictureFile.transferTo(new File(url+"/"+name + "." + ext));
			//把图片存储路径保存到数据库
			user.setImage("upload/"+name + "." + ext);
			}
			String str = userService.addInfo(user);			
			System.out.println(str);
			request.setAttribute("InfoMessage", str);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "添加信息失败！具体异常信息：" + e.getMessage());
		} finally {			
			return "result";
		}
	}
	
	@RequestMapping("getAll")
	public String getAddInfoAll(HttpServletRequest request){
		try {
			 Page page=null;
		//获取当前页数
        String pageNow=request.getParameter("pageNow");
        //获取总页数
        int totalCount=userService.getallpages();
        List<User> list=new ArrayList<User>();
        if (pageNow!=null) {
            page=new Page(Integer.parseInt(pageNow), totalCount);
            list=this.userService.getAll(page.getStartPos(),page.getPageSize());
        }else {
            page=new Page(1, totalCount);
            list=this.userService.getAll(page.getStartPos(),page.getPageSize());
        }	
			System.out.println("------User_list-----"+list.size());
			request.setAttribute("addLists", list);
			request.setAttribute("page", page);
			return "listAll";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "信息载入失败！具体异常信息：" + e.getMessage());
			return "result";
		}
	}
	
	@SuppressWarnings("finally")
	@RequestMapping("del")
	public String del(int id,HttpServletRequest request){
		try {			
			String str = userService.delete(id);
			request.setAttribute("InfoMessage", str);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "删除信息失败！具体异常信息：" + e.getMessage());
		} finally {			
			return "result";
		}
	}
	@RequestMapping("modify")
	public String modify(int id,HttpServletRequest request){
		try {			
			User user = userService.findById(id);
			request.setAttribute("add", user);
			return "modify";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "信息载入失败！具体异常信息：" + e.getMessage());
			return "result";
		}
	}
	@SuppressWarnings("finally")
	@RequestMapping("update")
	public String update(User user,HttpServletRequest request){
		try {			
			String str = userService.update(user);
			request.setAttribute("InfoMessage", str);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "更新信息失败！具体异常信息：" + e.getMessage());
		} finally {			
			return "result";
		}
	}
	@RequestMapping("login")
	public String login(User user,HttpServletRequest request,@RequestParam(value = "code",required = false) String code){
		try {
			 int b = checkValidateCode(code);
			 if(b==-1||b==0) {
				 request.setAttribute("loginUser", "验证码错误，登录失败");
					return "index";
			 }else {
			System.out.println("------login--qian----"+user.getUsername()+","+user.getPassword()+".");
			User loginUser = null;
			loginUser=userService.login(user);
			
			if(loginUser!=null){
				request.setAttribute("loginUser", loginUser.getUsername());
				return "index";
			}else{
				request.setAttribute("loginUser", "登录失败");
				return "index";
			}
			 }
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "登录失败！具体异常信息：" + e.getMessage());
			return "result";
		}
	}
//	@RequestMapping("login")
//	public ModelAndView annotationValidate(@Validated User user,Errors errors) {
//		if(errors.hasErrors()) {
//			List<FieldError>errorList=errors.getFieldErrors();
//			for(FieldError error: errorList) {
//			System.err.println("fied"+error.getField()+"\t"+"msg:"+error.getDefaultMessage());
//			}
//		}
//		ModelAndView mView=new ModelAndView();
//		mView.setViewName("index");
//		return mView;
//	}
	// 匹对验证码的正确性
	public int checkValidateCode(String code) {
	    Object vercode = getRequest().getSession().getAttribute("VERCODE_KEY");
	    if (null == vercode) {
	        return -1;
	    }
	    if (!code.equalsIgnoreCase(vercode.toString())) {
	        return 0;
	    }
	    return 1;
	}
	 /* 获取request
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }
}
