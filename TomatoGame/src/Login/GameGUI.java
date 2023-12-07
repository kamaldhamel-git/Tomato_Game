package Login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;

import Game.GameEngine;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.GridLayout;

/**
 * A Simple Graphical User Interface for the Six Equation Game.
 * 
 * @author Marc Conrad
 */
public class GameGUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = -107785653906635L;

    private JLabel questArea = null;
    private GameEngine myGame = null;
    private BufferedImage currentGame = null;
    
    /**
     * Method that is called when a button has been pressed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int solution = Integer.parseInt(e.getActionCommand());
        boolean correct = myGame.checkSolution(solution);
        processAnswer(correct);

        //Checking if the game is completed
        if (myGame.isGameCompleted()) {
            myGame.showGameResults();
            resetGame();
        }
    }
    
    
//    To ensure that the results are displayed 
//    after the last question is answered
    public void processAnswer(boolean correct) {
        if (correct) {
            System.out.println("Correct solution entered!");
            currentGame = myGame.nextGame();
            ImageIcon ii = new ImageIcon(currentGame);
            questArea.setIcon(ii);
        } else {
            System.out.println("Not Correct");
            currentGame = myGame.nextGame();
            ImageIcon ii = new ImageIcon(currentGame);
            questArea.setIcon(ii);
        }

        // Check if the game is completed after updating the UI
        if (myGame.isGameCompleted()) {
            myGame.showGameResults();  // Call the showGameResults method
           // resetGame(); 
        }
    }

    /**
     * Resets the game for a new session.
     */
    private void resetGame() {
        myGame = new GameEngine(null);
        currentGame = myGame.nextGame();
        ImageIcon ii = new ImageIcon(currentGame);
        questArea.setIcon(ii);
    }

	/**
     * Initializes the game.
     *
     * @param player
     */
    private void initGame(String username) {
        setSize(690, 500);
        setLocation(350, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tomato Game");
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(192, 192, 192), 2));
        panel.setBackground(UIManager.getColor("Button.background"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        myGame = new GameEngine(username);
        currentGame = myGame.nextGame();

        ImageIcon ii = new ImageIcon(currentGame);
        panel.setLayout(new BorderLayout(0, 0));
        questArea = new JLabel(ii);
        questArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
        questArea.setBackground(new Color(255, 255, 0));
        questArea.setSize(330, 600);

        
        JScrollPane questPane = new JScrollPane(questArea);
        questPane.setBorder(new LineBorder(new Color(240, 240, 240), 3));
        questPane.setBackground(UIManager.getColor("Button.background"));
        questPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        questPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(questPane);

        getContentPane().add(panel);
        
        JPanel panel2 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel2.getLayout();
        flowLayout.setVgap(20);
        flowLayout.setHgap(0);
        panel2.setBackground(new Color(255, 153, 0));
        getContentPane().add(panel2, BorderLayout.NORTH);
        
        JLabel lblLevelTitle = new JLabel("Welcome to Tomato Game");
        lblLevelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblLevelTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
        panel2.add(lblLevelTitle);
        
        JPanel infoPanel = new JPanel();
        FlowLayout fl_infoPanel = (FlowLayout) infoPanel.getLayout();
        fl_infoPanel.setHgap(0);
        fl_infoPanel.setVgap(40);
        infoPanel.setBackground(new Color(255, 255, 0));
        
        // Adding real-time date and time 
        JLabel lblDateTime = new JLabel("Date & Time");
		lblDateTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblDateTime.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDateTime.setBackground(new Color(0, 0, 153));
		infoPanel.add(lblDateTime);
		
		// Creating ActionListener to update date and time
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDateTime(lblDateTime);
            }
        });
        timer.start();
        
        JPanel panel4 = new JPanel();
        panel4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel4.setBackground(UIManager.getColor("Button.background"));
        FlowLayout flowLayout_2 = (FlowLayout) panel4.getLayout();
        flowLayout_2.setVgap(20);
        panel.add(panel4, BorderLayout.SOUTH);
        
        JLabel lblImage1 = new JLabel("");
        lblImage1.setHorizontalAlignment(SwingConstants.LEFT);
        lblImage1.setBackground(Color.YELLOW);
        lblImage1.setIcon(new ImageIcon(GameGUI.class.getResource("/Images/happy_2.png")));
        panel.add(lblImage1, BorderLayout.WEST);
        
        JLabel lblImage2 = new JLabel("");
        lblImage2.setIcon(new ImageIcon(GameGUI.class.getResource("/Images/right_1.png")));
        lblImage2.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblImage2, BorderLayout.EAST);
        
        JPanel panel5 = new JPanel();
        panel.add(panel5, BorderLayout.NORTH);
     	panel5.setLayout(new GridLayout(1, 1, 0, 0));
        
     // Initialize lblUserProfile only once
     	JLabel lblUserProfile = new JLabel(username.toUpperCase()  ); //change to uppercare
     	lblUserProfile.setFocusable(false);
     	lblUserProfile.setIcon(new ImageIcon(GameGUI.class.getResource("/Images/user_logo_2.png")));
     	lblUserProfile.setBackground(new Color(0, 0, 153));
     	lblUserProfile.setFont(new Font("Tahoma", Font.BOLD, 18));
     	lblUserProfile.setHorizontalAlignment(SwingConstants.LEFT);
        panel5.add(lblUserProfile);
        
        JLabel lblUserLogout = new JLabel("Log Out   ");
		// To make the cursor pointer
		lblUserLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		//Adding mouseClicked event to logout the user.
		lblUserLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				UserLogin.loginFormInstance.showLoginForm();
				
			}
		});
		lblUserLogout.setFocusable(false);
		lblUserLogout.setIcon(new ImageIcon(GameGUI.class.getResource("/Images/Logout_1_1.png")));
		lblUserLogout.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUserLogout.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel5.add(lblUserLogout);

        
        for (int i = 0; i < 10; i++) {
            JButton btn = new JButton(String.valueOf(i));
            panel4.add(btn);
            btn.addActionListener(this);
        }
        
        getContentPane().add(infoPanel, BorderLayout.SOUTH);
        panel.repaint();
    }

    // Use this to start GUI, e.g., after login.  
    // @param username  
    public GameGUI(String username) {
    	super();
        setIconImage(Toolkit.getDefaultToolkit().getImage(GameGUI.class.getResource("/Images/cuteTomato.jpg")));
        initGame(username);
        // setting frame visible after initialization.
     	setVisible(true);
    }
    
    //Updating date and time automatically
  	private void updateDateTime(JLabel label) {
  		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  		String formattedDateTime = dateFormat.format(new Date());
  		label.setText("Today's Time:  " + formattedDateTime);
  	}
}
