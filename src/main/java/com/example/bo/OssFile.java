package com.example.bo;
import java.util.Date;
 
/**
 * OssFile
 *
 * @author meng
 */
public class OssFile {
	/**
	 * 文件地址
	 */
	private String filePath;
	/**
	 * 域名地址
	 */
	private String domain;
	/**
	 * 文件名
	 */
	private String name;
	/**
	 * 原始文件名
	 */
	private String originalName;
	/**
	 * 文件hash值
	 */
	public String hash;
	/**
	 * 文件大小
	 */
	private long size;
	/**
	 * 文件上传时间
	 */
	private Date putTime;
	/**
	 * 文件contentType
	 */
	private String contentType;
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public Date getPutTime() {
		return putTime;
	}
	public void setPutTime(Date putTime) {
		this.putTime = putTime;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
}