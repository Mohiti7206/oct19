package com.user;

import java.io.PrintWriter;
import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.GenericServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.ServletContext;
import java.io.*;
import java.util.*;
import java.sql.*;

@MultipartConfig

public class Register extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
           

            String name = request.getParameter("user_name");
            String email = request.getParameter("user_email");
            String password = request.getParameter("user_password");
            Part part = request.getPart("image");
            String filename = part.getSubmittedFileName();
       
            
            
            
            

            try {
                Thread.sleep(3000);

                Class.forName("com.mysql.jdbc.Driver");
                
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube", "mm1", "111");
                
                String q = "insert into user(name, password, email, imageName) values(?,?,?,?)";
                
                
                PreparedStatement psmt = con.prepareStatement(q);
                psmt.setString(1, name);
                psmt.setString(2, password);
                psmt.setString(3, email);
                psmt.setString(4, filename);
                
               psmt.executeUpdate();
              
               InputStream is = part.getInputStream();
               byte []data = new byte[is.available()];
               
               is.read(data);
               ServletContext context = request.getServletContext();
               String relativePath = "img" + File.separator + filename;
               String realPath = context.getRealPath(relativePath);
               
               FileOutputStream fos  = new FileOutputStream(realPath);
              fos.write(data);
               fos.close();
               
               out.println("done");
                    
            } catch (Exception e) {
                e.printStackTrace();
                out.println("already registered");
            }

         
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
