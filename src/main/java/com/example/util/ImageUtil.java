package com.example.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;

public class ImageUtil {

	
	private static final Logger log = LoggerFactory.getLogger(ImageUtil.class);

	/**
	 * @Description: 将base64编码字符串转换为图片
	 * @Author:
	 * @CreateTime:
	 * @param file base64编码字符串
	 * @param path 图片路径-具体到文件
	 * @return
	 */
	public static String generateImage(byte[] b, String imageName, String destDir) {
		// 解密
		try {
			// 图片分类路径+图片名+图片后缀
			String imgClassPath = imageName;
			// 解密
//			Base64.Decoder decoder = Base64.getDecoder();
			// 去掉base64前缀 data:image/jpeg;base64,
//			file = file.substring(file.indexOf(",", 1) + 1, file.length());
//			byte[] b = decoder.decode(file);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			// 保存图片
			String savePath = destDir.concat(imgClassPath);
			OutputStream out = new FileOutputStream(savePath);
			out.write(b);
			out.flush();
			out.close();
			// 返回图片的相对路径 = 图片分类路径+图片名+图片后缀
			return savePath;
		} catch (IOException e) {
			return null;
		}
	}
	/**
     * BufferedImage 编码转换为 base64
     *
     * @param bufferedImage
     * @return
     */
    public static String BufferedImageToBase64(BufferedImage bufferedImage) {
        //io流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            //写入流中
            ImageIO.write(bufferedImage, "png", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //转换成字节
        byte[] bytes = baos.toByteArray();

        //转换成base64串
        String png_base64 = Base64Encoder.encode(bytes).trim();
        //删除 \r\n
        return png_base64.replaceAll("\n", "").replaceAll("\r", "");
    }

    /**
     * BufferedImage 编码转换为 base64
     * 文件格式为jpg
     *
     * @param bufferedImage
     * @return
     */
    public static String BufferedImageToBase64V2(BufferedImage bufferedImage) {
        String base64 = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            //写入流中
            ImageIO.write(bufferedImage, "jpg", baos);

            //转换成base64串
            base64 = Base64Encoder.encode(baos.toByteArray());
        } catch (IOException e) {
            log.error("BufferedImageToBase64V2 ERROR:{}", e.getMessage());
        }
        return base64;
    }

    /**
     * base64 编码转换为 BufferedImage
     *
     * @param base64
     * @return
     */
    public static BufferedImage base64ToBufferedImage(String base64) {

        try {
            byte[] bytes1 = Base64Decoder.decode(base64);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            return ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 将MultipartFile 图片文件编码为base64
     *
     * @param file
     * @param status true加:data:multipart/form-data;base64,前缀   false 不加前缀
     * @return
     */
    public static String generateBase64(MultipartFile file, Boolean status) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("图片不能为空！");
        }
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        String contentType = file.getContentType();
        byte[] imageBytes = null;
        String base64EncoderImg = "";
        try {
            imageBytes = file.getBytes();

            /**
             * 1.Java使用BASE64Encoder 需要添加图片头（"data:" + contentType + ";base64,"），
             *   其中contentType是文件的内容格式。
             * 2.Java中在使用BASE64Enconder().encode()会出现字符串换行问题，这是因为RFC 822中规定，
             *   每72个字符中加一个换行符号，这样会造成在使用base64字符串时出现问题，
             *   所以我们在使用时要先用replaceAll("[\\s*\t\n\r]", "")解决换行的问题。
             */
            if (status) {
                base64EncoderImg = "data:" + contentType + ";base64," + Base64Encoder.encode(imageBytes);
            } else {
                base64EncoderImg = Base64Encoder.encode(imageBytes);
            }
            base64EncoderImg = base64EncoderImg.replaceAll("[\\s*\t\n\r]", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64EncoderImg;
    }

    public static String GetImageStr(String imgFilePath) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            log.debug("目标文件不存在,文件地址为" + imgFilePath);
        }

        // 对字节数组Base64编码

        // 返回Base64编码过的字节数组字符串
        if (data != null) {
            return Base64Encoder.encode(data);
        }
        return null;
    }

    public static boolean GenerateImage(String imgStr, String imageUrl, String imageName) {
        //对字节数组字符串进行Base64解码并生成图片
        //图像数据为空
        if (imgStr == null) {
            return false;
        }

        try {
            //Base64解码
            byte[] b = Base64Decoder.decode(imgStr);

            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    //调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            //System.currentTimeMillis()
            //新生成的图片
            File file = new File(imageUrl);
            //创建文件夹
            file.mkdirs();
            String imgFilePath = imageUrl + imageName;
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
