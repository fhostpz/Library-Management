import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class Login {
  private String loggedUsername = "null";
  private Boolean loginComplete = true;
  
  //Panel for the Login.
  private JPanel LoginPanel;
  
  //Text fields for user to input his username, and password.
  private JTextField inputUsername;
  private JTextField inputPassword;
  
  //Buttons for "Forgot Password", and "Log In".
  private JButton forgotYourPasswordButton;
  private JButton loginButton;
  
  //Labels for Username and Password.
  private JLabel userName;
  private JLabel passWord;
  
  //Getter for inputUsername.
  public JTextField getInputUsername() {
    return inputUsername;
  }
  
  //Getter for loginPanel
  public JPanel getLoginPanel() {
    return LoginPanel;
  }
  
  //Setter for loggedUsername.
  public void setLoggedUsername(String loggedUsername) {
    this.loggedUsername = loggedUsername;
  }
  
  //Class Constructor.
  //The Constructor is an Overloaded Constructor.
  public Login(String username, final JPanel panel) {
    loggedUsername = username;
    loginButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (credentialsValid() == true) {
          Profile f = new Profile();
          f.setLoggedUsername(loggedUsername);
          System.out.println(loggedUsername);
          inputUsername.setText("");
          inputPassword.setText("");
          CardLayout cardLayout = (CardLayout) panel.getLayout();
          cardLayout.show(panel, "profile");
        }
        else {
          loginFail fail = new loginFail();
          fail.setLocationRelativeTo(userName);
          fail.pack();
          fail.show();
        }
      }
    });
    
    forgotYourPasswordButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        CardLayout cardLayout = (CardLayout) panel.getLayout();
        cardLayout.show(panel, "forgotPassword_1");
      }
    });
  }
  
  //Checks for matching, valid user credentials.
  public Boolean credentialsValid() {
    //Stores the MB_NAME, MB_PIN from the database.
    String memberName;
    String memberPIN;
    
    //
    ArrayList<String> usernames = new ArrayList<String>();
    ArrayList<String> passwords = new ArrayList<String>();
    
    //
    String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
    String url = "jdbc:db2:testlib";
    Connection conn = null;
    
    try {
      Class.forName(jdbcClassName);
      conn = DriverManager.getConnection(url);
      
      System.out.println("DEBUG: Creating statement.");
      Statement st = conn.createStatement();
      
      //Extract records in ascending order by first name.
      //Stores SQL command in a string variable.
      //Execute the SQL command.
      System.out.println("DEBUG: Fetching records in ascending order.");
      String sqlCommand = ("SELECT MB_NAME, MB_PASSWORD from member");
      ResultSet rs = st.executeQuery(sqlCommand);
      
      while (rs.next()) {
        //Stores the MB_NAME, and MB_PIN of a row to these two String variable.
        memberName = rs.getString(1);
        memberPIN = rs.getString(2);
        
        //Stores the MB_NAME, and MB_PIN of a row to these two arrayList
        usernames.add(memberName);
        passwords.add(memberPIN);
      }
      
      for(int i = 0; i < usernames.size(); i++) {
        //Gets the username, and password typed by the user.
        String typedUsername = inputUsername.getText();
        String typedPassword = inputPassword.getText();
        
        if (typedUsername.equals(usernames.get(i)) && typedPassword.equals(passwords.get(i))) {
          setLoggedUsername(typedUsername);
          
          System.out.println("DEBUG: Username and Password are Correct and Matched");
          
          return true;
        }
        else {
          System.out.println("DEBUG: Username and Password are Incorrect and Mismatched");
          
          return false;
        }
      }
    }
    catch (ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    finally {
      if (conn != null) {
        System.out.println("DEBUG: Connection to database is successful!");
      }
    }
  
    return false;
  }
}
