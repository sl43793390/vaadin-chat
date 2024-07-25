package com.example.application.views;

import java.io.File;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.github.olafj.vaadin.flow.Video;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "video")
//@AnonymousAllowed
public class VideoView extends VerticalLayout {

    private static final long serialVersionUID = 8728059005569047780L;
	
    public VideoView() {
    	
    	Video video = new Video();
    	video.setWidth("540px");
    	video.setControls(true);
    	video.setSource(Paths.get("d:\\test.mkv"));
//    	video.setPosterSource(Paths.get("./target/test-classes/cover.jpeg"));
		add(video);
    }


}
