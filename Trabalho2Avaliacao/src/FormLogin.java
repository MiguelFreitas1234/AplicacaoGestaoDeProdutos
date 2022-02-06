import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FormLogin {
    private JTextField textFieldUser;
    private JButton entrarButton;
    private JButton cancelarButton;
    private JPasswordField passwordField;
    private JPanel PanelLogin;

    Connection con;
    PreparedStatement pst;


    public static void main(String[] args)
    {
        JFrame frame=new JFrame("Gestão dos Produtos");
        frame.setContentPane(new FormLogin().PanelLogin);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void Connect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/bdprodutos", "root","1234");
            System.out.println("Success");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public FormLogin() {
        Connect();

        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String txtUser=textFieldUser.getText();
                String txtPass=String.valueOf(passwordField.getPassword());
                try
                {
                    pst = con.prepareStatement("Select id, user, password FROM loginusers");
                    ResultSet resSet= pst.executeQuery();
                    boolean b =false;
                    while (resSet.next())
                    {
                        String id=resSet.getString("id");
                        String user=resSet.getString("user");
                        String password=resSet.getString("password");

                        System.out.printf("%s - %s -%s",id , user, password);
                        //JOptionPane.showMessageDialog(null,nome+":"+user+"."+pass);
                        if(txtUser.equals(user) &&txtPass.equals(password))
                        {
                            new FormPrincipal().setVisible(true);
                            b=true;
                        }
                    }
                    if(!b)
                    {
                        JOptionPane.showMessageDialog(null,"Erro! Login incorreto! Password/Username errados");

                    }
                }
                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Erro" +ex.getMessage());
                }
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
