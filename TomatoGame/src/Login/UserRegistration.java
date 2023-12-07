package Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UserRegistration {

    public JFrame frmSignup;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConPassword;

    
    private static UserRegistration registrationFormInstance;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    registrationFormInstance = new UserRegistration();  // Initialize the registrationFormInstance
                    registrationFormInstance.frmSignup.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UserRegistration() {
        initialize();
    }
    
//  For PostgreSql connection
    Connection con;
    PreparedStatement pstm;

    public void showRegistrationForm() {
        frmSignup.setVisible(true);
    }
    
    private void initialize() {
        frmSignup = new JFrame();
        frmSignup.setResizable(false);
        frmSignup.getContentPane().setBackground(new Color(255, 255, 0));
        frmSignup.setIconImage(Toolkit.getDefaultToolkit().getImage(UserRegistration.class.getResource("/Images/cuteTomato.jpg")));
        frmSignup.setBackground(new Color(255, 255, 0));
        frmSignup.setTitle("Tomato Game");
        frmSignup.setBounds(100, 100, 649, 442);
        frmSignup.setSize(658, 657);
        frmSignup.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frmSignup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmSignup.getContentPane().setLayout(null);

//      Creating panel
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(""));
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(0, 0, 633, 403);
        panel.setLocation(400,150);
        panel.setSize(650, 400);
        frmSignup.getContentPane().add(panel);
        panel.setLayout(null);

//      Creating label for username
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblUsername.setBounds(270, 130, 130, 22);
        panel.add(lblUsername);

//      Creating label for password
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblPassword.setBounds(270, 174, 130, 22);
        panel.add(lblPassword);

//      Creating label for Confirm Password
        JLabel lblConPassword = new JLabel("Confirm Password:");
        lblConPassword.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblConPassword.setBounds(270, 219, 155, 22);
        panel.add(lblConPassword);

//      Creating TextField for username
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Times New Roman", Font.BOLD, 18));
        txtUsername.setColumns(10);
        txtUsername.setBounds(432, 129, 176, 24);
        panel.add(txtUsername);
        
//      Creating PasswordField for password
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Times New Roman", Font.BOLD, 18));
        txtPassword.setBounds(432, 173, 176, 24);
        panel.add(txtPassword);
      
//      Creating PasswordField for Confirm password
        txtConPassword = new JPasswordField();
        txtConPassword.setFont(new Font("Times New Roman", Font.BOLD, 18));
        txtConPassword.setBounds(432, 218, 176, 24);
        panel.add(txtConPassword);
        
//      Adding a label for displaying image
        JLabel lblSignupImage = new JLabel("");
        lblSignupImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblSignupImage.setIcon(new ImageIcon(UserRegistration.class.getResource("/Images/signup-bg.png")));
        lblSignupImage.setBounds(0, 0, 407, 403);
        panel.add(lblSignupImage);

//      Button for registering user 
        JButton btnSignup = new JButton("Sign Up");
        btnSignup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	try {
            		String username = txtUsername.getText();
            		@SuppressWarnings("deprecation")
					String password = txtPassword.getText();
            		@SuppressWarnings("deprecation")
					String conPassword = txtConPassword.getText();
            		

                    // Checking if the password and confirm password match
                    if (!password.equals(conPassword)) {
                        JOptionPane.showMessageDialog(null, "Password and Confirm Password do not match", "Register Error", JOptionPane.ERROR_MESSAGE);
                        txtUsername.setText("");
                		txtPassword.setText("");
                		txtConPassword.setText("");
                		txtUsername.requestFocus();
                        return; // Don't proceed with update
                        
                    }
            		
                    // Connecting to postgreSql
            		Class.forName("org.postgresql.Driver");
            		con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tomatogame?user=postgres&password=password");
            		
            		//Inserting user data into database
            		String sql = "INSERT INTO t_users(username, password, conPassword) VALUES(?,?,?)";
            		PreparedStatement statement = con.prepareStatement(sql);
            		statement.setString(1,username);
            		statement.setString(2,password);
            		statement.setString(3,conPassword);
            		statement.executeUpdate();
            		JOptionPane.showMessageDialog(null, "Signup successfull");
            		txtUsername.setText("");
            		txtPassword.setText("");
            		txtConPassword.setText("");
            		txtUsername.requestFocus();
            		
            	}
            	catch(Exception e1) {
            		
            		System.out.println(e1.toString());
            	}
            }
        });
        btnSignup.setBackground(new Color(0, 128, 64));
        btnSignup.setFont(new Font("Times New Roman", Font.BOLD, 18));
        btnSignup.setBounds(328, 274, 225, 29);
        panel.add(btnSignup);
        
        JLabel lblRegister = new JLabel("Register yourself here...");
        lblRegister.setFont(new Font("Tahoma", Font.BOLD, 26));
        lblRegister.setBounds(400, 108, 355, 42);
        frmSignup.getContentPane().add(lblRegister);

     // Adding a window listener to handle the closing of the registration
        frmSignup.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
            	if (UserLogin.loginFormInstance != null) {
            		// Notify the login form that the registration form is closed
                    UserLogin.loginFormInstance.showLoginForm();
                } else {
                    // Handle the case where LoginForm.loginFormInstance is null
                }
            	
            }
        });
       
    }
}
