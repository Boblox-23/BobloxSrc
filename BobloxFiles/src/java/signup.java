import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Acer
 */
public class signup extends HttpServlet {
    //Connection con= MyDb.connectdb();
    PreparedStatement ps=null;
    ResultSet rs=null;
    
    public signup() {
        MyDb.connectdb();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String signin = "INSERT INTO BobloxInfo (username, email, password) values (?, ?, ?)";
        String checkuser = "SELECT * FROM BobloxInfo WHERE USERNAME = ?";
        
    try (PrintWriter out = response.getWriter()) {
        String uname = request.getParameter("uname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmpass = request.getParameter("confirm-password");
        
        MyDb db = new MyDb();
        Connection con = db.connectdb();
        
        ps = con.prepareStatement(checkuser);
        ps.setString(1, uname);
        rs = ps.executeQuery();
            
        if(rs.next()) {
            response.setContentType("text/html");
            out.println("<script type=\"text/javascript\">"); 
            out.println("alert('Username already exists!');"); 
            out.println("</script>"); 
        }
        else if(uname.isEmpty()) {
            out.println("alert('Insert a Username!');"); 
        }
        else if(email.isEmpty()) {
            out.println("alert('Insert an Email!');");
        }
        else if(password.isEmpty()) {
            out.println("alert('Insert a Password!');");
        }
        else if(!password.equals(confirmpass)) {
            out.println("alert('Check your Password again if it is the same!');");
        }
        else {
            ps = con.prepareStatement(signin);
            ps.setString(1, uname);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.executeUpdate();
            response.sendRedirect("LogIn.html");
        }
        response.sendRedirect("SignupForm.html");
    } catch (Exception ex) {
            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
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
