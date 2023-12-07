package Login;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.border.LineBorder;

public class UserLogin {

    private JFrame frmLogin;
    private JTextField txtUsername;
    private JPasswordField txtPassword;


    private static UserRegistration registrationFormInstance;
    public static UserLogin loginFormInstance;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    loginFormInstance = new UserLogin();  // Initialize the loginFormInstance
                    loginFormInstance.frmLogin.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UserLogin() {
        initialize();
    }
  
    public void showLoginForm() {
    	
        frmLogin.setVisible(true);
    }

    private boolean checkLogin(String username, String password) {
        try {
            // Establish database connection
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tomatogame?user=postgres&password=password");

            // Prepare SQL query
            String sql = "SELECT * FROM t_users WHERE username = ? AND password = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute query
            ResultSet resultSet = statement.executeQuery();

            // Check if a record with the provided username and password exists
            boolean loginSuccessful = resultSet.next();
            if(loginSuccessful) {
            	new UserProfile(username);
            }
            
            // Close resources
            resultSet.close();
            statement.close();
            con.close();

            return loginSuccessful;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private void initialize() {
        frmLogin = new JFrame();
        frmLogin.setIconImage(Toolkit.getDefaultToolkit().getImage(UserLogin.class.getResource("/Images/cuteTomato.jpg")));
        frmLogin.getContentPane().setBackground(new Color(255, 255, 0));
        frmLogin.setTitle("Tomato Game");
        frmLogin.setBounds(100, 100, 596, 481);
        frmLogin.setSize(658, 657);
        frmLogin.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmLogin.setResizable(false);
        frmLogin.getContentPane().setLayout(null);
        frmLogin.setLocation(400, 50);
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(0, 0, 580, 442);
        panel.setLocation(400,50);
        panel.setSize(658, 657);
        frmLogin.getContentPane().add(panel);

        JLabel lblNewLabel = new JLabel("Password:");
        lblNewLabel.setBounds(151, 507, 95, 27);
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));

        JLabel lblNewLabel_1 = new JLabel("Username:");
        lblNewLabel_1.setBounds(151, 453, 95, 27);
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));

        txtUsername = new JTextField();
        txtUsername.setBounds(270, 456, 225, 25);
        txtUsername.setFont(new Font("Times New Roman", Font.BOLD, 20));
        txtUsername.setColumns(10);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(270, 509, 225, 27);
        txtPassword.setFont(new Font("Times New Roman", Font.BOLD, 20));

        JButton btnLogin = new JButton("Log in");
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.setBounds(151, 567, 134, 27);
        btnLogin.setFocusable(false);
        btnLogin.setBackground(new Color(0, 128, 64));
        btnLogin.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {        	
            	 String username = txtUsername.getText();
                 String password = new String(txtPassword.getPassword());

                 // Call a method to check login credentials in the database
                 if (checkLogin(username, password)) {
                	 
                	// Initialize the game GUI with the user profile
                     GameGUI theGame = new GameGUI(username);
                     theGame.setVisible(true);
                     frmLogin.dispose();
                	 
                 } else {
                     JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                     txtUsername.setText(null);
                     txtPassword.setText(null);
                 }
            }
        });

        JButton btnSignup = new JButton("Sign up");
        btnSignup.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignup.setBounds(361, 567, 134, 27);
        btnSignup.setFocusable(false);
        btnSignup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (registrationFormInstance == null) {
                    registrationFormInstance = new UserRegistration();
                }
                registrationFormInstance.showRegistrationForm();
                frmLogin.dispose();
            }
        });
        btnSignup.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnSignup.setBackground(new Color(128, 64, 64));
        panel.setLayout(null);
        panel.add(lblNewLabel_1);
        panel.add(txtUsername);
        panel.add(lblNewLabel);
        panel.add(txtPassword);
        panel.add(btnLogin);
        panel.add(btnSignup);
        
        JLabel lblLoginImage = new JLabel("");
        lblLoginImage.setBackground(new Color(255, 255, 0));
        lblLoginImage.setHorizontalTextPosition(SwingConstants.CENTER);
        lblLoginImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblLoginImage.setSize(658, 657);
        lblLoginImage.setIcon(new ImageIcon(UserLogin.class.getResource("/Images/tomato5-removebg_1.png")));
        panel.add(lblLoginImage);
        
        JLabel lblUsername = new JLabel("Login to play the game...");
        lblUsername.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblUsername.setBounds(400, 11, 350, 40);
        frmLogin.getContentPane().add(lblUsername);
    }

}
