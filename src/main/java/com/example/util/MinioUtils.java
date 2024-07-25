package com.example.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.example.bo.OssFile;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PostPolicy;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.SetBucketPolicyArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import io.minio.messages.Item;

/**
 * @description： minio工具类
 * @version：1.0
 */
@Component
public class MinioUtils {

	
	private static final Logger log = LoggerFactory.getLogger(MinioUtils.class);

	/**
	 * 字符集
	 */
	private static final String ENCODING = "UTF-8";
	  @Autowired
	    public  void setMinioClient(MinioClient minioClient) {MinioUtils.minioClient = minioClient;}
	    @Value("${oss.bucket-name}")
	    public  void setBucketName(String bucketName) {
	        MinioUtils.bucketName = bucketName;
	    }
	    @Value("${oss.endpoint}")
	    public  void setEndpoint(String endpoint) {
	        MinioUtils.endpoint = endpoint;
	    }
	    @Value("${oss.folder}")
	    public  void setFolder(String folder) {
	        MinioUtils.folder = folder;
	    }
	    @Value("${oss.access-key}")
	    public  void setAccessKey(String accessKey) {
	        MinioUtils.accessKey = accessKey;
	    }
	    @Value("${oss.secret-key}")
	    public  void setSecretKey(String secretKey) {
	        MinioUtils.secretKey = secretKey;
	    }
	    private static MinioClient minioClient;
	    private static String bucketName;

	    private static String endpoint;

	    private static String folder;

	    private static String accessKey;

	    private static String secretKey;

	    
    public static boolean  bucketExists(String bucketName) {
		try {
			return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
		} catch (Exception e) {
			log.error("minio bucketExists Exception:{}", e);
		}
		return false;
	}

	/**
	 * @Description: 创建 存储桶
	 * @Param bucketName: 存储桶名称
	 * @return: void
	 * @Date: 2023/8/2 11:28
	 */
	public static void makeBucket(String bucketName) {
		try {
			if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
				minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
				log.info("minio makeBucket success bucketName:{}", bucketName);
			}
		} catch (Exception e) {
			log.error("minio makeBucket Exception:{}", e);
		}
	}
    /**
     * 上传File文件
     *
     * @param file
     * @return
     */
    public static String uploadFile(File file) {
        MultipartFile multipartFile = fileToMultipartFile(file);
        return uploadMultipartFile(multipartFile,minioClient,endpoint,bucketName,folder);
    }

    /**
     * 上传multipartFile文件
     * @param multipartFile
     * @return
     */
    public static String uploadFile(MultipartFile multipartFile) {
        return uploadMultipartFile(multipartFile,minioClient,endpoint,bucketName,folder);
    }

    /**
     * 上传MultipartFile文件
     * @param file
     * @param minioClient
     * @param endpoint
     * @param bucketName
     * @param folder
     * @return
     */
    private static String uploadMultipartFile(MultipartFile file, MinioClient minioClient, String endpoint, String bucketName, String folder) {
        //判断bucket是否存在
        existBucket(minioClient, bucketName);
        //判断目录路径是否存在
        if (!doesFolderExist(minioClient,bucketName, folder)) {
            try {
                createFolder(minioClient,bucketName, folder);
            } catch (Exception e) {
                //throw new RuntimeException(e);
                log.error("创建目录失败!");
            }
        }
        String fileName = file.getOriginalFilename();

        String[] split = fileName.split("\\.");
        if (split.length > 1) {
            fileName = split[0] + "_" + System.currentTimeMillis() + "." + split[1];
        } else {
            fileName = fileName + System.currentTimeMillis();
        }
        String objectName = folder +StrUtil.C_SLASH + fileName;
        //log.debug("objectName是路径，文件最终整合的路径的值为:{}", objectName);
        InputStream in = null;
        try {
            in = file.getInputStream();
            //此方法为动态获取contentType,可以直接预览
            String contentType = getContentTypeByFileName(fileName);
            log.info("contentType的值为:{}", contentType);
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(in, in.available(), -1)
                    //如果为流,默认不预览直接下载
                    //.contentType("application/octet-stream")
                    .contentType(contentType)
                    .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传失败"+e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //String url=endpoint + "/" + bucketName + "/" + objectName;
        String url=endpoint + "/" + bucketName + "/" + objectName;
        log.info("上传minIo后文件url的值为:{}", url);
        return url;
    }

    /**
     * 根据文件名称下载File类型的文件
     *
     * @param fileUrl
     * @return
     */
    public MultipartFile downloadMultipartFileByUrl(String fileUrl) {

        try {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
            MultipartFile multipartFile = getMultipartFile(inputStream, fileName);
            return multipartFile;
        } catch (Exception e) {
            log.error("文件下载失败：{}", e.getMessage());
            throw  new RuntimeException("文件下载失败"+e.getMessage());
        }
    }
    /**
	 * @Description: 下载文件
	 * @Param response: 响应
	 * @Param fileName: 文件名
	 * @Param filePath: 文件路径
	 * @return: void
	 * @Date: 2023/8/2 14:08
	 */
    public static void downloadFile(HttpServletResponse response, String fileName, String filePath) {
		GetObjectResponse is = null;
		try {
			GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket(bucketName).object(filePath)
					.build();
			is = minioClient.getObject(getObjectArgs);
			// 设置文件ContentType类型，这样设置，会自动判断下载文件类型
			response.setContentType("application/x-msdownload");
			response.setCharacterEncoding(ENCODING);
			// 设置文件头：最后一个参数是设置下载的文件名并编码为UTF-8
			response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, ENCODING));
			IoUtil.copy(is, response.getOutputStream());
			log.info("minio downloadFile success, filePath:{}", filePath);
		} catch (Exception e) {
			log.error("minio downloadFile Exception:{}", e);
		} finally {
			IoUtil.close(is);
		}
	}

    /**
     * 根据文件名称下载File类型的文件
     *
     * @param fileUrl
     * @return
     */
    public File downloadFileByUrl(String fileUrl) {
        try {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
            MultipartFile multipartFile = getMultipartFile(inputStream, fileName);
            File file = MultipartFileToFile(multipartFile);
            return file;

        } catch (Exception e) {
            log.error("文件下载失败：{}", e.getMessage());
            throw  new RuntimeException("文件下载失败"+e.getMessage());
        }
    }
    
    public static MultipartFile getMultipartFile(InputStream inputStream, String fileName) {
        FileItem fileItem = createFileItem(inputStream, fileName);
        //CommonsMultipartFile是feign对multipartFile的封装，但是要FileItem类对象
        return new CommonsMultipartFile(fileItem);
    }


    /**
     * FileItem类对象创建
     *
     * @param inputStream inputStream
     * @param fileName    fileName
     * @return FileItem
     */
    public static FileItem createFileItem(InputStream inputStream, String fileName) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "file";
        FileItem item = factory.createItem(textFieldName, MediaType.MULTIPART_FORM_DATA_VALUE, true, fileName);
        int bytesRead = 0;
        byte[] buffer = new byte[10 * 1024 * 1024];
        OutputStream os = null;
        //使用输出流输出输入流的字节
        try {
            os = item.getOutputStream();
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            inputStream.close();
        } catch (IOException e) {
            log.error("Stream copy exception", e);
            throw new IllegalArgumentException("文件上传失败");
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    log.error("Stream close exception", e);

                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("Stream close exception", e);
                }
            }
        }
        return item;
    }


    /**
     * 断点下载
     *
     * @param fileName 文件名称
     * @param offset   起始字节的位置
     * @param length   要读取的长度
     * @return 流
     */
    public InputStream getObject(String fileName, long offset, long length)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        return minioClient.getObject(
                GetObjectArgs.builder().bucket(bucketName).object(fileName).offset(offset).length(length)
                        .build());
    }

    /**
     * 获取路径下文件列表
     *
     * @param prefix    文件名称
     * @param recursive 是否递归查找，如果是false,就模拟文件夹结构查找
     * @return 二进制流
     */
    public Iterable<Result<Item>> listObjects(String prefix,
                                              boolean recursive) {
        return minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(recursive).build());
    }

    /**
     * 获取文件外链
     *
     * @param objectName 文件名称
     * @param expires    过期时间 <=7天 秒级
     * @return url
     */
    public static String getPresignedObjectUrl(String objectName,
                                        Integer expires) {
        try {
            String fileUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET)
					.bucket(bucketName).object(objectName).expiry(expires).build());
            return fileUrl;
        } catch (Exception e) {
            log.error("获取文件外链失败：{}", e.getMessage());
            throw  new RuntimeException("获取文件外链失败：{}"+e.getMessage());
        }
    }

    /**
     * 给presigned URL设置策略
     * 允许给POST请求的presigned URL设置策略，比如接收对象上传的存储桶名称的策略，key名称前缀，过期策略。
     *
     * @param objectName 对象名
     * @return map
     */
    public Map<String, String> presignedGetObject(String objectName) {
        try {
        	PostPolicy postPolicy = new PostPolicy(bucketName, ZonedDateTime.now().plusDays(7));
            Map<String, String> formData = minioClient.getPresignedPostFormData(postPolicy);
            log.info("curl -X POST ");
            for (Map.Entry<String, String> entry : formData.entrySet()) {
                log.info(" -F " + entry.getKey() + "=" + entry.getValue());
            }
            return formData;
        } catch (Exception e) {
            log.error("Error occurred: " + e);
        }
        return null;
    }

    /**
     * 将URLDecoder编码转成UTF8
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getUtf8ByURLDecoder(String str) throws UnsupportedEncodingException {
        String url = str.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        return URLDecoder.decode(url, "UTF-8");
    }

    /**
     * 根据key删除服务器上的文件
     */
    public void deleteFileByUrl(String fileUrl) {
        try {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(folder + fileName).build());
            log.info("删除" + bucketName + "下的文件" + folder + fileName + "成功");
        } catch (Exception e) {
            log.error("删除文件失败:{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    /**
	 * 获取文件信息
	 * @param fileName 存储桶文件名称
	 * @return InputStream
	 */
	public static OssFile getOssInfo(String fileName) {
		try {
			StatObjectResponse stat = minioClient.statObject(StatObjectArgs.builder()
					.bucket(bucketName).object(fileName).build());
			OssFile ossFile = new OssFile();
			ossFile.setName(ObjectUtil.isEmpty(stat.object()) ? fileName : stat.object());
			ossFile.setFilePath(ossFile.getName());
			ossFile.setDomain(endpoint);
			ossFile.setHash(String.valueOf(stat.hashCode()));
			ossFile.setSize(stat.size());
			ossFile.setPutTime(DateUtil.date(stat.lastModified().toLocalDateTime()));
			ossFile.setContentType(stat.contentType());
			return ossFile;
		} catch (Exception e) {
			log.error("minio getOssInfo Exception:{}", e);
		}
		return null;
	}

    /**
     * 创建文件夹或目录
     *
     * @param bucketName 存储桶
     * @param objectName 目录路径
     */
    private static ObjectWriteResponse createFolder(MinioClient minioClient,String bucketName, String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                                new ByteArrayInputStream(new byte[]{}), 0, -1)
                        .build());
    }

    /**
     * 判断文件夹是否存在
     *
     * @param bucketName 存储桶
     * @param objectName 文件夹名称（去掉/）
     * @return true：存在
     */
    private static boolean doesFolderExist(MinioClient minioClient, String bucketName, String objectName) {
        boolean exist = false;
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucketName).prefix(objectName).recursive(false).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                if (item.isDir() && objectName.equals(item.objectName())) {
                    exist = true;
                }
            }
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    /**
     * 创建minioClient
     */
    private static MinioClient createMinioClient(MinioClient minioClient,String accessKey,String secretKey,String endpoint,String bucketName) {
        try {
            if (null == minioClient) {
                minioClient = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey)
                        .build();
                createBucket(minioClient,bucketName);
                //设置全局访问策略
                setBucketPolicy(minioClient,bucketName);
            } else {
                //设置全局访问策略
                setBucketPolicy(minioClient,bucketName);
            }
        } catch (Exception e) {
            log.error("初始化MinIO服务器异常：{}", e);
        }
        return minioClient;
    }

    private static void setBucketPolicy(MinioClient minioClient, String bucketName) {
        try {
            String config = "{\n" +
                    "     \"Statement\": [\n" +
                    "         {\n" +
                    "             \"Action\": [\n" +
                    "                 \"s3:GetBucketLocation\",\n" +
                    "                 \"s3:ListBucket\"\n" +
                    "             ],\n" +
                    "             \"Effect\": \"Allow\",\n" +
                    "             \"Principal\": \"*\",\n" +
                    "             \"Resource\": \"arn:aws:s3:::" + bucketName + "\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "       \"Action\": [\n" +
                    "                \"s3:GetObject\",\n" +
                    "                \"s3:PutObject\",\n" +
                    "                \"s3:DeleteObject\"\n" +
                    "      ],\n" +
                    "             \"Effect\": \"Allow\",\n" +
                    "             \"Principal\": {\"AWS\":[\"*\"]},\n" +
                    "             \"Resource\": \"arn:aws:s3:::" + bucketName + "/" + "**" + "\"\n" +
                    "         }\n" +
                    "     ],\n" +
                    "     \"Version\": \"2012-10-17\"\n" +
                    "}";

            minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucketName).config(config).build());

        } catch (ErrorResponseException | InsufficientDataException
                 | InternalException | InvalidKeyException
                 | InvalidResponseException | IOException
                 | NoSuchAlgorithmException | ServerException
                 | XmlParserException e) {
            log.error("minio设置桶:{}策略失败", bucketName, e);
        }
    }

    /**
     * description: 判断bucket是否存在，不存在则创建
     *
     * @return: void
     */
    private static void existBucket(MinioClient minioClient,String bucketName) {
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Bucket
     *
     * @throws Exception 异常
     */
    private static void createBucket(MinioClient minioClient,String bucketName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    @PostConstruct
    private void handlerInit() {
        createMinioClient(minioClient,accessKey,secretKey,endpoint,bucketName);
        log.info("MinIo初始化成功");
    }

    /**
     * 、
     * 根据文件后缀名获取contentType
     */
    public static String getContentTypeByFileName(String fileName) {
        //此方法可以替代spring中根据文件后缀名获取contentType
        Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(fileName);
        String contentType = mediaType.map(MediaType::toString).orElseGet(() -> MediaType.APPLICATION_OCTET_STREAM.toString());
        return contentType;
    }

    /**
     * File转换为MultipartFile
     *
     * @param file
     * @return
     */
    public static MultipartFile fileToMultipartFile(File file) {
        FileItem item = new DiskFileItemFactory().createItem("file"
                , MediaType.MULTIPART_FORM_DATA_VALUE
                , true
                , file.getName());
        try (InputStream input = new FileInputStream(file);
             OutputStream os = item.getOutputStream()) {
            // 流转移
            IOUtils.copy(input, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }

        return new CommonsMultipartFile(item);
    }
    /**
     * 将MultipartFile转换为File
     *
     * @param multiFile
     * @return
     */
    public static File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        fileName = "tmp-" + fileName;
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 若须要防止生成的临时文件重复,能够在文件名后添加随机码
        try {
            //File file = File.createTempFile(FileUtil.getFileNameNotPrefix(fileName), suffix);
            File file = File.createTempFile(fileName.substring(0, fileName.lastIndexOf(".")), suffix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * MultipartFile 保存本地
     *
     * @param file
     * @param path
     * @return
     */
    public static String downloadFile(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        File dest = new File(new File(path).getAbsolutePath() + "/" + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest); // 保存文件
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path + "/" + fileName;
    }
}



