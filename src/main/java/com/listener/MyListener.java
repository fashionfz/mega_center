package com.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.po.SysMenu;
import com.po.SysRole;
import com.po.SysUser;
import com.po.WorkBook;
import com.service.IMenuService;
import com.service.IRoleService;
import com.service.IUserService;
import com.service.IWorkBookService;

public class MyListener extends ContextLoaderListener{

	private static ServletContext  context;


	public static ServletContext  getContext() {
		return context;
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		super.contextInitialized(event);
		context = event.getServletContext();
		
		initData();
	}

	public static void setContext(ServletContext context) {
		MyListener.context = context;
	}
	
	
	
	
    public static void initData() {
        ApplicationContext bean = WebApplicationContextUtils
                .getRequiredWebApplicationContext(context);
        IUserService userService = (IUserService) bean.getBean("userService");
        long count = userService.getAllCount();
        if (count > 0)
            return;
        System.out.println("系统初始数据开始....");
        IMenuService menuService = (IMenuService) bean.getBean("menuService");
        List<SysMenu> menuList = new ArrayList<SysMenu>();
        menuList.add(new SysMenu("365系统", "/main.html", 1));
        menuList.add(new SysMenu("用户管理", "/user.html", 1));
        menuList.add(new SysMenu("角色管理", "/role.html", 1));
        menuList.add(new SysMenu("功能管理", "/menu.html", 1));
        menuList.add(new SysMenu("服务管理", "/service.html", 1));
        menuList.add(new SysMenu("DVR管理", "/device.html", 1));
        menuList.add(new SysMenu("检测报告", "/check_report.html", 1));
        menuList.add(new SysMenu("检测日志", "/check_log.html", 1));
        menuList.add(new SysMenu("设备组管理", "/devicegroup.html", 1));
        menuList.add(new SysMenu("检测配置", "/checkconfig.html", 1));
        menuList.add(new SysMenu("授权管理", "/licence.html", 1));
        menuList.add(new SysMenu("历史报告", "/history_report.html", 1));
        menuService.init(menuList);

        IRoleService roleService = (IRoleService) bean.getBean("roleService");
        SysRole role = new SysRole();
        role.setName("系统管理员");
        role.setStatus(1);
        role.setRemark("超级用户");
        roleService.save(role);

        String[] menuIds = { menuList.get(0).getId(), menuList.get(1).getId(),
                menuList.get(2).getId(), menuList.get(3).getId(),
                menuList.get(4).getId(), menuList.get(5).getId(),
                menuList.get(6).getId(), menuList.get(7).getId(),
                menuList.get(8).getId(), menuList.get(9).getId(),
                menuList.get(10).getId(),menuList.get(11).getId() };
        roleService.roleAnthMenu(role.getId(), menuIds);

        SysUser user = new SysUser();
        user.setName("megaeyes");
        user.setPassword("megaeyes");
        user.setAge(30);
        user.setIsAdmin(1);
        user.setSex(1);
        user.setStatus(1);
        user.setNote("系统预设超级用户");
        userService.init(user);

        String[] roleIds = { role.getId() };
        userService.userAnthRole(user.getId(), roleIds);
        
        
        IWorkBookService deviceService = (IWorkBookService) bean.getBean("workBookService");
        List<WorkBook> bookList = new ArrayList<WorkBook>();
        bookList.add(new WorkBook("device_field","表字段",1,"编号",1,1));
        bookList.add(new WorkBook("device_field","表字段",2,"VIC名称",2,1));
        bookList.add(new WorkBook("device_field","表字段",3,"DVR名称",3,1));
        bookList.add(new WorkBook("device_field","表字段",4,"DVR-ip",4,1));
        bookList.add(new WorkBook("device_field","表字段",5,"DVR用户名",5,1));
        bookList.add(new WorkBook("device_field","表字段",6,"DVR密码",6,1));
        bookList.add(new WorkBook("device_field","表字段",7,"类型",7,1));
        bookList.add(new WorkBook("device_field","表字段",8,"存储周期",8,1));
        bookList.add(new WorkBook("device_field","表字段",9,"存储策略",9,1));
        bookList.add(new WorkBook("device_field","表字段",10,"存储类型",10,1));
        bookList.add(new WorkBook("device_field","表字段",11,"通道",11,1));
        bookList.add(new WorkBook("device_field","表字段",12,"VIC类型",12,1));
        deviceService.init(bookList);
        

    }
	

	

}
