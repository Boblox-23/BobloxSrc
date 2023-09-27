import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyDb {
    public static Connection connectdb() {
        Connection con=null;
        try {
            con=DriverManager.getConnection("jdbc:derby://localhost:1527/BobloxDatabase","ict23","stronk");
            return con;
        } catch (SQLException ex) {
            Logger.getLogger(MyDb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
}
