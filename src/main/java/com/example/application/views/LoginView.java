package com.example.application.views;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.User;
import com.example.service.UserService;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import cn.hutool.crypto.SmUtil;

@AnonymousAllowed
@PageTitle("Login")
@Route(value = "")
public class LoginView extends LoginOverlay {
//	@AnonymousAllowed, @PermitAll, @RolesAllowed.
	
	private static final Logger logger = LoggerFactory.getLogger(LoginView.class);
	
    public LoginView(@Autowired UserService userService) {

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("系统登录");
        i18n.getHeader().setDescription("提示：");
        i18n.getForm().setTitle("WorkingCoo");
        i18n.setAdditionalInformation(null);
        setI18n(i18n);
        
        setForgotPasswordButtonVisible(false);
        setOpened(true);
        addLoginListener(new ComponentEventListener<AbstractLogin.LoginEvent>() {
			
			private static final long serialVersionUID = 3541080283633713772L;

			@Override
			public void onComponentEvent(LoginEvent event) {
				Notification notification = new Notification();
				notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
//				进行认证操作
				 UsernamePasswordToken token = new UsernamePasswordToken(event.getUsername(), SmUtil.sm3(event.getPassword()));
			        //获取当前的Subject  
			        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();  
			        try {  
			            logger.debug("对用户[" + event.getUsername() + "]进行登录验证..验证开始");  
			            currentUser.login(token);  
			            logger.debug("对用户[" + event.getUsername() + "]进行登录验证..验证通过");  
			        }catch(UnknownAccountException uae){  
			        	notification.show("用户名不存在",3000,Position.MIDDLE);
			            return;
			        }catch(IncorrectCredentialsException ice){  
			        	notification.show("用户名或密码不正确",3000,Position.MIDDLE);
			        	  return;
			        }catch(LockedAccountException lae){  
			        	notification.show("账户已锁定",3000,Position.MIDDLE);
			        	  return;
			        }catch(ExcessiveAttemptsException eae){  
			        	notification.show("密码输入次数过多",3000,Position.MIDDLE);
			        	  return;
			        }catch(AuthenticationException ae){  
			            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景  
			            ae.printStackTrace();  
			            notification.show("用户名或密码不正确",3000,Position.MIDDLE);
			            return;
			        }  
			        User user = userService.getUserById(event.getUsername());
			        VaadinSession.getCurrent().setAttribute("user",user) ;
					VaadinSession.getCurrent().setAttribute("userName", event.getUsername());
						logger.info("用户{}登录成功", event.getUsername());
//						UI.getCurrent().getPage().setLocation("/menu");
//						event.getSource().getUI().ifPresent(ui -> ui.navigate("/menu"));
						getUI().ifPresent(ui -> ui.navigate("/chat"));
//						getUI().ifPresent(ui -> ui.navigate("/leftview"));
			}
		});
    }

}