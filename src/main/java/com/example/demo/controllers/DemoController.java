package com.example.demo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;

@RestController
public class DemoController {

    @GetMapping("/home")
    public ResponseEntity<String> getHome(HttpServletRequest request, HttpServletResponse response){
        System.out.println("GET: /home invoked");
        printRequestResponse(request, response);
        return new ResponseEntity<>("GET: /home invoked", HttpStatus.OK);
    }

    @PostMapping("/home")
    public ResponseEntity<String> postHome(HttpServletRequest request, HttpServletResponse response){
        String message = "POST: /home invoked";
        System.out.println(message);
        printRequestResponse(request, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/home")
    public ResponseEntity<String> deleteHome(HttpServletRequest request, HttpServletResponse response){
        String message = "DELETE: /home invoked";
        System.out.println(message);
        printRequestResponse(request, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/home")
    public ResponseEntity<String> putHome(HttpServletRequest request, HttpServletResponse response){
        String message = "PUT: /home invoked";
        System.out.println(message);
        printRequestResponse(request, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    // GET, POST, PUT, DELETE, HEAD, OPTIONS, PATCH, TRACE
    @PatchMapping("/home")
    public ResponseEntity<String> patchHome(HttpServletRequest request, HttpServletResponse response){
        String message = "PATCH: /home invoked";
        System.out.println(message);
        printRequestResponse(request, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/home", method= RequestMethod.HEAD)
    public ResponseEntity<String> headHome(HttpServletRequest request, HttpServletResponse response){
        String message = "HEAD: /home invoked";
        System.out.println(message);
        printRequestResponse(request, response);
        return new ResponseEntity<>(message, HttpStatus.OK); // any respone will be discarded.
    }

    @RequestMapping(value = "/home", method = RequestMethod.OPTIONS)
    public ResponseEntity<String> optionsHome(HttpServletRequest request, HttpServletResponse response){
        String message = "Options: /home invoked";
        System.out.println(message);
        printRequestResponse(request, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    private static String convertHttpRequestToString(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-------------------------- Starting of Request ------\n");
        // Append request method and URI
        stringBuilder.append(request.getMethod()).append(" ").append(request.getRequestURI()).append("\n");

        // Append request headers
        stringBuilder.append("----- Header Section -----\n");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = Collections.list(request.getHeaders(headerName)).toString();
            stringBuilder.append(headerName).append(": ").append(headerValue).append("\n");
        }
        stringBuilder.append("----- Parameters section ------").append("\n");

        // Append request parameters
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            stringBuilder.append(paramName).append(": ").append(String.join(", ", paramValues)).append("\n");
        }
        stringBuilder.append("-----Body Section -----" ).append("\n");
        // Append request body
        try {
            BufferedReader reader = request.getReader();
            StringBuilder bodyBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                bodyBuilder.append(line);
            }
            stringBuilder.append("Body: ").append(bodyBuilder.toString()).append("\n");
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception based on your needs
        }
        stringBuilder.append("-------------------------------------------- End of request ------ \n\n");
        return stringBuilder.toString();
    }

    public static String convertHttpResponseToString(HttpServletResponse response) {
        // Create a response wrapper to capture the response content
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        PrintWriter writer = new PrintWriter(charArrayWriter);
        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response) {
            @Override
            public PrintWriter getWriter() {
                return writer;
            }
        };

        // Your servlet or code that uses the HttpServletResponse goes here
        // ...

        // Extract the response body
        String responseBody = charArrayWriter.toString();

        // Build the string representation
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---------------------------------------------------- Starting of Response -----\n");
        stringBuilder.append("Status Code: ").append(responseWrapper.getStatus()).append("\n");

        // Append response headers
        stringBuilder.append("------ Header Section ---------\n");
        responseWrapper.getHeaderNames().forEach(headerName ->
                stringBuilder.append(headerName).append(": ").append(responseWrapper.getHeader(headerName)).append("\n"));

        // Append response body
        stringBuilder.append("------- Body Section -----\n");
        stringBuilder.append("Body: ").append(responseBody).append("\n");
        stringBuilder.append("------------------------------------------ End of Response ----- \n\n");
        return stringBuilder.toString();
    }

    private static void printRequestResponse( HttpServletRequest request, HttpServletResponse response){
        System.out.println(convertHttpRequestToString(request));
        System.out.println(convertHttpResponseToString(response));
    }
}
