import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.*;
import java.util.Date;
import java.util.Calendar;


public class BorrowMaterial {
    private JTextField inputMaterialID;
    private JTextField inputUser;
    private JComboBox CopyCombo;
    private JButton verifyButton;
    private JButton cancelButton;
    private JButton acceptButton;
    private JTextField inputIssueDate;
    private JPanel borrowMaterial;
    private Date today = Calendar.getInstance().getTime();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String name;

    public BorrowMaterial(String name, final JPanel panel){

        this.name = name;
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean test = false;
                verifymaterial();
                //checkIfEmpty();

            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputMaterialID.setText("");
                inputUser.setText("");
                //inputIssueDate.setText("");
                CopyCombo.removeAllItems();

                panel.revalidate();
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "main");
            }
        });

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrowMaterial();
                CopyCombo.removeAllItems();


            }
        });
    }

    public JPanel getBorrowMaterial() {
        return borrowMaterial;
    }


    public void borrowMaterial(){


        today = Calendar.getInstance().getTime();
        int count = 0;
        int count1 = 0;
        String  toUser= "fe", toMaterialCopyID = "fe", toIssueDate = "fe",
                toDueDate = "fe";



        String material = inputMaterialID.getText();
        String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2://localhost:50001/testlib";
        String user = "User";
        String password = "ting970926";
        Connection conn = null;
        MessageDialog fail = new MessageDialog();
        Calendar c = Calendar.getInstance();

        try
        {

            Class.forName(jdbcClassName);
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("Creating statement...");
            Statement st = conn.createStatement();

            // Extract records in ascending order by first name.
            System.out.println("Fetching records in ascending order...");
            String sql = ("SELECT MB_ID from member where MB_NAME = '"+ inputUser.getText() +"'");
            ResultSet rs = st.executeQuery(sql);

            while(rs.next())
            {
                toUser = rs.getString(1);

            }

            String sql1 = ("SELECT count(*) from member where MB_ID = '"+ toUser +"'");
            ResultSet wo = st.executeQuery(sql1);

            while(wo.next()){
                count = wo.getInt(1);
            }

            System.out.println(count);



            PreparedStatement insertPass = null;
            String insertString = "INSERT INTO ISSUE (IS_MC_ID,IS_MB_ID,IS_ISSUE_DATE,IS_DUE_DATE) VALUES (?,?,?,?)";

            toMaterialCopyID = CopyCombo.getSelectedItem().toString();;
            //toUser = inputUser.getText();
            toIssueDate = dateFormat.format(today);
            c.setTime(today);
            c.add(Calendar.DATE, 14);
            today = c.getTime();
            toDueDate = dateFormat.format(today);

            insertPass = conn.prepareStatement(insertString);
            insertPass.setString(1, toMaterialCopyID);
            insertPass.setString(2, toUser);
//            updatePass.setInt(3, Integer.parseInt(toContact));
            insertPass.setString(3, toIssueDate);
            insertPass.setString(4, toDueDate);

            insertPass.executeUpdate();

            if(count == 1){
                PreparedStatement updatePass = null;
                String updateString = "UPDATE MATERIAL_COPY SET MC_STATUS = 'Borrowed' where MC_ID = ? ";

                updatePass = conn.prepareStatement(updateString);
                updatePass.setString(1, toMaterialCopyID);
                updatePass.executeUpdate();
            }





        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
            fail.setLocationRelativeTo(borrowMaterial);
            fail.setMessage1("Input Error");
            switch(e.getErrorCode()) {
                case (-180):
                    fail.setMessage1("SQL Error Code -180");
                    fail.setMessage2("Data Format is incorrect (YYYY/MM/DD)");
                    break;
                case (-420):
                    fail.setMessage1("SQL Error Code -420");
                    fail.setMessage2("A string is entered, when an integer is required.");
                    break;
                default:
                    fail.setMessage1("SQL Error Code " + e.getErrorCode());
                    fail.setMessage2("SQL Error");
                    break;
            }
            fail.pack();
            fail.show();

            e.printStackTrace();

        }
        finally
        {
            if(conn != null)
            {

                System.out.println("Connection success!");
            }
        }


    }

    public void verifymaterial(){
        CopyCombo.removeAllItems();
        String mahtext = "", mahtext2 = "", mahtext3 = "" ,
                mahtext4 = "", mahtext6 = "",
                mahtext8 = "", mahtext5 = "" , mahtext7 = "";

        int size = 0 ;

        String material = inputMaterialID.getText();
        String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2:testlib";
        Connection conn = null;

        try {
            Class.forName(jdbcClassName);
            conn = DriverManager.getConnection(url);

            System.out.println("Creating statement...");
            Statement st = conn.createStatement();

            // Extract records in ascending order by first name.
            System.out.println("Fetching records in ascending order...");
            String sql = ("SELECT COUNT(*) from material_copy where MC_MT_ID = '" + material + "'");
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                size = rs.getInt(1);
            }
            System.out.println(size);

            System.out.println("Creating statement...");
            st = conn.createStatement();

            // Extract records in ascending order by first name.
            System.out.println("Fetching records in ascending order...");
            String sql2 = ("SELECT MC_ID from material_copy where MC_MT_ID = '" + material + "' AND MC_STATUS ='Available'" );
            ResultSet wo = st.executeQuery(sql2);
            String toCopy = " ";
            //int count = 1;
            while (wo.next()){

                    toCopy = wo.getString(1);
                    CopyCombo.addItem(toCopy);
                    System.out.println(toCopy);
            }


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
    }
}
