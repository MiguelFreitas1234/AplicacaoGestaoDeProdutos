import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class    FormPrincipal {
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField textFieldProductID;
    private JTextField textFieldName;
    private JTextField textFieldPrice;
    private JTextField textFieldQuantity;
    private JButton searchButton;
    private JPanel PanelPrincipal;

    Connection con;
    PreparedStatement pst;

    public static void setVisible(boolean b)
    {
        JFrame frame=new JFrame("Gestao dos Jogadores");
        frame.setContentPane(new FormPrincipal().PanelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650,425);
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

    public FormPrincipal() {
        Connect();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String name, price, qty;

                name = textFieldName.getText();
                price = textFieldPrice.getText();
                qty = textFieldQuantity.getText();
                try
                {
                    pst = con.prepareStatement("insert into produtos(nome,preço,quantidade)values(?,?,?)");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Added!");
                    textFieldName.setText("");
                    textFieldPrice.setText("");
                    textFieldQuantity.setText("");
                    textFieldProductID.requestFocus();
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {

                    String pid = textFieldProductID.getText();
                    pst = con.prepareStatement("select nome,preço,quantidade from produtos where id = ?");
                    pst.setString(1, pid);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);

                        textFieldName.setText(name);
                        textFieldPrice.setText(price);
                        textFieldQuantity.setText(qty);
                    }
                    else
                    {
                        textFieldName.setText("");
                        textFieldPrice.setText("");
                        textFieldQuantity.setText("");
                        JOptionPane.showMessageDialog(null,"Este ID não existe");

                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pid,name,price,qty;

                name = textFieldName.getText();
                price = textFieldPrice.getText();
                qty = textFieldQuantity.getText();
                pid = textFieldProductID.getText();

                try {

                    pst = con.prepareStatement("update produtos set nome = ?,preço = ?,quantidade = ? where id = ?");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.setString(4, pid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Veiculo atualizado!");

                    textFieldName.setText("");
                    textFieldPrice.setText("");
                    textFieldQuantity.setText("");
                    textFieldName.requestFocus();
                    textFieldProductID.setText("");
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String produtoId;
                produtoId = textFieldProductID.getText();

                try {
                    pst = con.prepareStatement("delete from produtos where id = ?");
                    pst.setString(1, produtoId);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted!");

                    textFieldName.setText("");
                    textFieldPrice.setText("");
                    textFieldQuantity.setText("");
                    textFieldName.requestFocus();
                    textFieldProductID.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
    }
}





