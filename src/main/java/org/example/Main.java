package org.example;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        File uploadDir = new File("uploaded-csv");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist
        //System.out.println("Hello world!");
        get("/hello", (req, res) -> "Hello Jay");


//        post("/", (req, res) -> {
//
//            Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
//
//            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
//
//            try (InputStream input = req.raw().getPart("uploaded_file").getInputStream()) { // getPart needs to use same "name" as input field in form
//                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
//            }
//        });

        post("/upload", "multipart/form-data", (request, response) -> {

            Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            try (InputStream input = request.raw().getPart("uploaded_file").getInputStream()) { // getPart needs to use same "name" as input field in form
                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }


//            String location = "uploaded-csv";          // the directory location where files will be stored
//            System.out.println("Average finder v0.1");
//            long maxFileSize = 100000000;       // the maximum size allowed for uploaded files
//            long maxRequestSize = 100000000;    // the maximum size allowed for multipart/form-data requests
//            int fileSizeThreshold = 1024;       // the size threshold after which files will be written to disk
//
//            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
//                    location, maxFileSize, maxRequestSize, fileSizeThreshold);
//            request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
//                    multipartConfigElement);

//            Collection<Part> parts = request.raw().getParts();
//            for (Part part : parts) {
//                System.out.println("Name: " + part.getName());
//                System.out.println("Size: " + part.getSize());
//                System.out.println("Filename: " + part.getSubmittedFileName());
//            }
//
//            String fName = request.raw().getPart("file").getSubmittedFileName();
//            System.out.println("Title: " + request.raw().getParameter("title"));
//            System.out.println("File: " + fName);
//
//            Part uploadedFile = request.raw().getPart("file");
//            Path out = Paths.get("image/" + fName);
//            try (final InputStream in = uploadedFile.getInputStream()) {
//                Files.copy(in, out);
//                uploadedFile.delete();
//            }
//// cleanup
//            multipartConfigElement = null;
//            parts = null;
//            uploadedFile = null;

            return "OK"+tempFile;
        });

    }
}