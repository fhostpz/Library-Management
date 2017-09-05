
import java.sql.*;

public class Login
{
  public static void main(String[] args)
  {
    String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
	String url = "jdbc:db2://localhost:50000/tf2";
	String user = "db2admin";
	String password = "ibmadmin";
	
	Connection conn = null;
	
	try
	{
	  Class.forName(jdbcClassName);
	  conn = DriverManager.getConnection(url, user, password);
	}
	catch(ClassNotFoundException e)
	{
	  e.printStackTrace();
	}
	catch(SQLException e)
	{
	  e.printStackTrace();
	}
	finally
	{
	  if(conn != null)
	  {
		System.out.println("Connection success!");
		
	  }
	}
	
	Statement stmt = null;
	String pass;
	ResultSet rs = null;
	
	try
	{
	  stmt = conn.createStatement();
	  System.out.println("Created JDBC Statement object");
	
	  rs = stmt.executeQuery("SELECT PASSWORD FROM DB2ADMIN.USERINFO");
	  System.out.println("Create JDBC ResultSet object");
	
	  while(rs.next())
	  {
	    pass = rs.getString(1);
	    System.out.println("Password = " + pass);
	  }
	  
	  rs.close();
	}
	catch(SQLException e)
	{
	  e.printStackTrace();
	}
	
	try
	{
	  stmt.close();
	  conn.close();
    }
	catch (SQLException e)
	{
      e.printStackTrace();
    }
  }
}

/*
create table userinfo
(
username varchar(10) not null,
password varchar(10) not null,
pin int not null,
primary key(username)
);

create table userinfo(username varchar(10) not null, password varchar(10) not null, pin int not null, primary key(username))

insert into userinfo values ('dummyacc', 'abcd_1234', 12345678)
insert into userinfo values ('testacc', 'abcd_1234', 12345678)
insert into userinfo values ('trialacc', 'abcd_1234', 12345678)
*/