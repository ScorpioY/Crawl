import java.sql.*;
import java.util.Map;

public class SqlUtil {
    private static final String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=DocHalo_LOCAL";
    private static final String user = "sa";
    private static final String password = "root";
    private static Connection connection = null;

    public static void executeUpdate(Map dataMap){

        String Company_Name = dataMap.get("Company_Name").toString();
        String Exhibitor_Address = dataMap.get("Exhibitor_Address").toString();
        String Phone_Number = dataMap.get("Phone_Number").toString();
        String WebSite = dataMap.get("WebSite").toString();
        String FloorPlan_location = dataMap.get("FloorPlan_location").toString();
        String Company_Contacts = dataMap.get("Company_Contacts").toString();
        String Company_Brief_Description =  dataMap.get("Company_Brief_Description ").toString();
        String Email_Address = dataMap.get("Email_Address ").toString();


        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");// 加载驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(url, user, password);// 连接数据库
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String sql = "insert into CRAWL(CompanyName,ExhibitorAddress,PhoneNumber,website,FloorPlanLocation,CompanyContacts,CompanyBriefDescription,EmailAddress) values(?,?,?,?,?,?,?,?)";
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,Company_Name);
            ps.setString(2,Exhibitor_Address);
            ps.setString(3,Phone_Number);
            ps.setString(4,WebSite);
            ps.setString(5,FloorPlan_location);
            ps.setString(6,Company_Contacts);
            ps.setString(7,Company_Brief_Description);
            ps.setString(8,Email_Address);
            ps.addBatch();
            ps.executeBatch();
            connection.commit();
            System.out.println("导入完成");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
