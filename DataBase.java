package RainDropMaths;

import java.sql.*;

public class DataBase {
    String DBDriver="com.mysql.cj.jdbc.Driver";
    String URL="jdbc:mysql://localhost:3306/";
    String DBName="raindropmath";
    String userName="root";
    String password="password";
    String tableName="gamescores";

    public Connection getDatabaseConnection() throws ClassNotFoundException, SQLException {

        Class.forName(DBDriver);
        Connection con= DriverManager.getConnection(URL+DBName,userName,password);
        return con;

    }

    public void insertData(int score) throws SQLException, ClassNotFoundException {
        String quary="Insert into "+tableName+" value("+score+");";
        Connection con=getDatabaseConnection();
        Statement st= con.createStatement();
        int count=st.executeUpdate(quary);
        System.out.println(count+" rows affected");
        st.close();
        con.close();
    }

    public int getMaxScore() throws SQLException, ClassNotFoundException {
        String quary="select max(score) from gamescores;";
        Connection con= getDatabaseConnection();

        Statement st= con.createStatement();
        ResultSet rs=st.executeQuery(quary);
        rs.next();
        int maxScore=rs.getInt(1);
        st.close();
        con.close();
        return maxScore;
    }

}
