/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staffFeature;

import DAO.DAOBooking;
import DAO.DAOCustomer;
import DAO.DAODoctor;
import DAO.DAOFeedback;
import DAO.DAOUser;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.Doctor;
import model.Feedback;
import model.User;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "staffFeedback", urlPatterns = {"/feedbackstaff"})
public class staffFeedback extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet staffFeedback</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet staffFeedback at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        HttpSession session = request.getSession();
        DAOUser userDao = new DAOUser();
        DAOBooking bookingDao = new DAOBooking();
        DAOCustomer cusDao = new DAOCustomer();
        DAODoctor docDao = new DAODoctor();
        User user = (User) session.getAttribute("user");
        Doctor doctor = docDao.getDoctorbyID(user.getUserId());
        DAOFeedback feedbackDao = new DAOFeedback();
        String rate = request.getParameter("rate");
        ArrayList<Feedback> listFeedback;
        if (rate != null && !rate.isEmpty()) {
            listFeedback = feedbackDao.getListFeedbackByRate(doctor.getDoctorId(), rate);
        } else {
            listFeedback = feedbackDao.getListFeedback(doctor.getDoctorId());
        }
        session.setAttribute("roleId", user.getRoleId());
        request.setAttribute("listFeedback", listFeedback);
        request.getRequestDispatcher("staff_feedback_list.jsp").forward(request, response);
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

    public static void main(String[] args) {

        DAOFeedback feedbackDao = new DAOFeedback();
        ArrayList<Feedback> listFeedback = feedbackDao.getListFeedback(1);
        System.out.println(listFeedback.toString());
    }

}
