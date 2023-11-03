/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staffFeature;

import DAO.DAODoctor;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MedicalInfo;
import model.Slot;
import model.SlotDoctor;
import model.User;

/**
 *
 * @author Admin
 */
public class reservationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        DAODoctor d = new DAODoctor();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("403.jsp");
        } else {
            // get all data of booking by doctor
            int doctorId = d.getDoctorIdByUserId(currentUser.getUserId());
            ArrayList<SlotDoctor> slotDoc = d.getReservationByDocId(doctorId);
            ArrayList<SlotDoctor> newSlotDoc = new ArrayList<>();
            String specialty = d.getspecialtyNameByDocId(doctorId);

            // handle filter date
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = null;
            try {
                currentDate = formatter.parse(LocalDateTime.now().toString());
            } catch (ParseException ex) {
                Logger.getLogger(reservationController.class.getName()).log(Level.SEVERE, null, ex);
            }

            String module = request.getParameter("module");
            String date = request.getParameter("date");
            // get date from now
            if (module == null) {
                for (SlotDoctor slotDoctor : slotDoc) {
                    try {
                        Date slotDoctorDate = formatter.parse(slotDoctor.getDay().toString());
                        if (slotDoctorDate.after(currentDate) || slotDoctorDate.equals(currentDate)) {
                            newSlotDoc.add(slotDoctor);
//                            System.out.println("true: " + slotDoctorDate);
                        } else {
//                            System.out.println("false: " + slotDoctorDate);
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(reservationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                newSlotDoc.addAll(slotDoc);
            }

            Date dateFilter = new Date();

            if (date != null) {
                switch (date) {
                    case "all":
                        dateFilter = null;
                        break;
                    case "today":
                        dateFilter = currentDate;
                        break;
                    case "tommorow": 
                    try {
                        dateFilter = formatter.parse(LocalDateTime.now().plusDays(1).toString());
                    } catch (ParseException ex) {
                        Logger.getLogger(reservationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                    default:
                    try {
                        dateFilter = formatter.parse(date);
                    } catch (ParseException ex) {
                        Logger.getLogger(reservationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }
            else {
                dateFilter = null;
            }

            if (dateFilter != null) {
                ArrayList<SlotDoctor> rawData = new ArrayList<>();
                rawData.addAll(newSlotDoc);
                newSlotDoc.clear();
                for (SlotDoctor slotDoctor : rawData) {
                    try {
                        Date slotDoctorDate = formatter.parse(slotDoctor.getDay().toString());
                        if (slotDoctorDate.equals(dateFilter)) {
                            newSlotDoc.add(slotDoctor);
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(reservationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            request.setAttribute("date", date);
            request.setAttribute("module", module);
            request.setAttribute("currentUser", currentUser);
            request.setAttribute("slotDoc", newSlotDoc);
            request.setAttribute("specialty", specialty);
            request.getRequestDispatcher("reservation.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAODoctor d = new DAODoctor();
        int slotId = Integer.parseInt(request.getParameter("slotId"));
        int doctorId = Integer.parseInt(request.getParameter("doctorId"));
        int slotDoctorId = d.getSlotDoctorId(slotId, doctorId);
        User khachHang = d.getUserInfoBySlotDoctorId(slotDoctorId);
        Slot rightSlot = d.getSlotBySlotId(slotId, doctorId);
        MedicalInfo med = d.getMedInfo(slotDoctorId);

        request.setAttribute("med", med);
        request.setAttribute("slotId", slotId);
        request.setAttribute("doctorId", doctorId);
        request.setAttribute("khachHang", khachHang);
        request.setAttribute("rightSlot", rightSlot);
        request.setAttribute("slotDoctorId", slotDoctorId);
        request.getRequestDispatcher("reservationDetail.jsp").forward(request, response);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public static void main(String[] args) {
        // a before b -> a<b
        // a after b -> a>=b
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date a = new Date(); // 2023-11-01
        Date b = null;
        try {
            b = formatter.parse("2023-11-01");
        } catch (ParseException ex) {
            Logger.getLogger(reservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println("compare: " + b.after(a));
    }

}
