package com.example.application.component.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.upload.FailedEvent;
import com.vaadin.flow.component.upload.FileRejectedEvent;
import com.vaadin.flow.component.upload.FinishedEvent;
import com.vaadin.flow.component.upload.MultiFileReceiver;

/**
 * 保存文件到本地的实现类
 * @author Administrator
 *
 */
public class FileReceiverImpl implements MultiFileReceiver,ComponentEventListener<FinishedEvent>{

	private static final long serialVersionUID = 6792454734458616252L;

	private static final Logger log = LoggerFactory.getLogger(FileReceiverImpl.class);

	private String saveDir = "";
	private String fileName;
	
	/**
	 * 传入要保存的路径
	 * @param saveDir
	 */
	public FileReceiverImpl(String saveDir) {
		super();
		this.saveDir = saveDir;
	}

	@Override
	public OutputStream receiveUpload(String fileName, String mimeType) {
		log.warn("开始接收文件。。。"+fileName);
		this.fileName = fileName;
		File fileUploaded = new File(saveDir + File.separator + fileName);
		FileOutputStream fileStream = null;
		try {
			fileStream = new FileOutputStream(fileUploaded);
		} catch (FileNotFoundException e) {
			log.error(ExceptionUtils.getStackTrace(e));
			return null;
		}
		return fileStream;
	}

	@Override
	public void onComponentEvent(FinishedEvent event) {
		Notification.show("保存成功", 5000, Notification.Position.MIDDLE);
	}

	public String getSaveDir() {
		return saveDir;
	}

	public void setSaveDir(String saveDir) {
		this.saveDir = saveDir;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
