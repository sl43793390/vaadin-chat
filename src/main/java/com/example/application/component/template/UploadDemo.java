package com.example.application.component.template;

import java.io.File;
import java.net.URI;
import java.util.ResourceBundle;

import com.example.application.component.util.ComFactory;
import com.example.application.component.util.FileReceiverImpl;
import com.example.application.component.util.UploadExamplesI18N;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileBuffer;
import com.vaadin.flow.server.frontend.installer.DefaultFileDownloader;

/**
 * 文件缓存类型：
 * 1 MemoryBuffer

	Handles a single file upload at once, writes the file data into an in-memory buffer. Using MemoryBuffer automatically configures the component so that only a single file can be selected.
	
	2 MultiFileMemoryBuffer
	
	Handles multiple file uploads at once, writes the file data into a set of in-memory buffers.
	
	3 FileBuffer
	
	Handles a single file upload at once, saves a file on the system. Files are saved into the current working directory of the Java application. Using FileBuffer automatically configures the component so that only a single file can be selected.
	
	4 MultiFileBuffer
	
	Handles multiple file uploads at once, and for each, saves a file on the system. Files are saved into the current working directory of the Java application.
 * 
 * @author Administrator
 *
 */
public class UploadDemo extends VerticalLayout{

	private static final long serialVersionUID = -7599457982054001810L;
	//	private MultiFileMemoryBuffer buffer;
	private MultiFileBuffer multiFileBuffer;
	private File fileUploaded;

	public UploadDemo() {
		super();
		multiFileBuffer = new MultiFileBuffer();
//		 MultiFileBuffer buffer = new MultiFileBuffer();
	        Upload upload = new Upload(multiFileBuffer);
	        upload.setAutoUpload(true); //自动开始上传

	        UploadExamplesI18N i18n = new UploadExamplesI18N();
	        upload.setI18n(i18n);
	        upload.setMaxFiles(1);
	        //限制文件类型
	        upload.setAcceptedFileTypes("application/pdf", ".pdf",".jpg",".png","docx","xls","xlsx","zip");
//	        int maxFileSizeInBytes = 100* 1024 * 1024; // 100MB/注意此处设置不一定起作用，spring本身会有大小拦截，需要配置在application.properties中
//	        upload.setMaxFileSize(maxFileSizeInBytes);
//	        upload.setMaxFiles(5);
	        String customDir = ResourceBundle.getBundle("application").getString("upload.custom.dir");
	        FileReceiverImpl fileReceiverImpl = new FileReceiverImpl(customDir);
	        //添加监听器
	        upload.addFileRejectedListener(event -> {
	            String errorMessage = event.getErrorMessage();
	            Notification notification = Notification.show(errorMessage, 5000,
	                    Notification.Position.MIDDLE);
	            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
	        });
	        
	        upload.addFinishedListener(fileReceiverImpl);
	        upload.setReceiver(fileReceiverImpl);

	        add(upload);
	        //进度条
	        ProgressBar progressBar = new ProgressBar();
	        progressBar.setIndeterminate(true);

	        Div progressBarLabel = new Div();
	        progressBarLabel.setText("上传进行中，请稍等...");

	        add(progressBarLabel, progressBar);
	        
//	        完成后进行关闭或隐藏进度条
	        Button primaryBtn = ComFactory.getPrimaryBtn("完成");
//	        primaryBtn.addClickListener(e -> {progressBar.setVisible(false);progressBarLabel.setVisible(false)});
	        primaryBtn.addClickListener(e -> this.remove(progressBar,progressBarLabel));//完成后直接移除进度条组件
	        add(primaryBtn);
	        
	        /**************************文件下载**************/
			Button downloadBtn = ComFactory.getPrimaryBtn("下载");
			downloadBtn.addClickListener(event -> {
//				文件下载只需要访问对应接口，发送制定文件名即可${pageContext.request.contextPath}/file/download?fileName=aa.txt
				UI.getCurrent().getPage().open("http://192.168.190.100:9001/api/v1/download-shared-object/aHR0cDovLzEyNy4wLjAuMTo5MDAwL3Rlc3QvJUU0JUJFJTlCJUU1JUJBJTk0JUU5JTkzJUJFJUU3JUFFJTgwJUU1JThEJTk1JUU2JUI1JTgxJUU3JUE4JThCLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUhLMjNCM0tVMU9QNDc4TjNBVktOJTJGMjAyNDA3MjIlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNzIyVDA4MjcwOVomWC1BbXotRXhwaXJlcz00MzE5OSZYLUFtei1TZWN1cml0eS1Ub2tlbj1leUpoYkdjaU9pSklVelV4TWlJc0luUjVjQ0k2SWtwWFZDSjkuZXlKaFkyTmxjM05MWlhraU9pSklTekl6UWpOTFZURlBVRFEzT0U0elFWWkxUaUlzSW1WNGNDSTZNVGN5TVRZM09UTTNNaXdpY0dGeVpXNTBJam9pYlhsdGFXNXBiMkZrYldsdUluMC5ma2pJSjBwTzJueGhaRm5xWUdKVzNEaE1pWXNfTGxTNGx5N2Nad1RhN2k1cHQyM0ROMVFXX1hqNFFkN1U5LTh1RnVnWThxOFczSEg0MUJCMHVuaXI2dyZYLUFtei1TaWduZWRIZWFkZXJzPWhvc3QmdmVyc2lvbklkPW51bGwmWC1BbXotU2lnbmF0dXJlPWQyZmRiYmVkM2QyOWE1NGIxOTE5YTM3YTA2MDY4NDVkNWI0ZDNiNjE3NDA2ODBiOWE5MjcwYTY3MzRkOWY5MjE", "123");
			});
		      add(downloadBtn);
		      
		        
		     DatePickerIndividualInputFields f = new DatePickerIndividualInputFields();
		     add(f);
	        
	}

}
