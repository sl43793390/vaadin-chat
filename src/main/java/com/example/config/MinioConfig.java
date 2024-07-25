package com.example.config;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
 
/**
 * Minio参数配置类
 *
 * @author meng
 */
@Component
@ConfigurationProperties(prefix = MinioConfig.PREFIX)
public class MinioConfig {
 
	/**
	 * 配置前缀
	 */
	public static final String PREFIX = "oss";
 
	/**
	 * 对象存储名称
	 */
	private String name;
 
	/**
	 * 对象存储服务的URL
	 */
	private String endpoint;
 
	/**
	 * Access key 账户ID
	 */
	private String accessKey;
 
	/**
	 * Secret key 密码
	 */
	private String secretKey;
 
	/**
	 * 默认的存储桶名称
	 */
	private String bucketName = "meng";
 
	/**
	 * 可上传的文件后缀名
	 */
	private List<String> fileExt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public List<String> getFileExt() {
		return fileExt;
	}

	public void setFileExt(List<String> fileExt) {
		this.fileExt = fileExt;
	}

	public static String getPrefix() {
		return PREFIX;
	}
 
	
}