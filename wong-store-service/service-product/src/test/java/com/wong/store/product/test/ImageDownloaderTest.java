package com.wong.store.product.test;

import com.wong.store.model.entity.product.Category;
import com.wong.store.product.mapper.CategoryMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/28 15:35
 */
@SpringBootTest
public class ImageDownloaderTest {
    @Resource
    CategoryMapper categoryMapper;

    @Test
    public void downloadImages() {
        String outputPath = "/Users/andywong/Documents/Dev/DevCode/AndyProjects/wong-store-parent/images";
        List<Category> categoryList = categoryMapper.queryAll();
        List<String> imageUrls = categoryList.stream().map(Category::getImageUrl).filter(imageUrl -> !ObjectUtils.isEmpty(imageUrl)).toList();
        if (imageUrls.isEmpty()) {
            System.out.println("No image URLs provided.");
            return;
        }

        try {
            Files.createDirectories(Paths.get(outputPath));
        } catch (IOException e) {
            System.out.println("Failed to create output directory: " + outputPath);
            e.printStackTrace();
            return;
        }

        for (String imageUrl : imageUrls) {
            downloadImage(imageUrl, outputPath);
        }
    }

    private static void downloadImage(String imageUrl, String outputPath) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Failed to download image from: " + imageUrl);
                return;
            }

            ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream());
            Path imagePath = Paths.get(outputPath, getImageNameFromUrl(imageUrl));
            FileOutputStream fos = new FileOutputStream(imagePath.toFile());
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
            connection.disconnect();

            System.out.println("Successfully downloaded image from: " + imageUrl);
        } catch (Exception e) {
            System.out.println("Error occurred while downloading image from: " + imageUrl);
            e.printStackTrace();
        }
    }

    private static String getImageNameFromUrl(String imageUrl) {
        int lastIndex = imageUrl.lastIndexOf("/");
        return imageUrl.substring(lastIndex + 1);
    }
}
