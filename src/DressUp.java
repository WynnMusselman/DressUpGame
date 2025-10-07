import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;



public class DressUp {

    int boardWidth = 800;
    int boardHeight = 700; 
    JFrame frame = new JFrame("Let's Play Dress Up!");

    //backgrounds
    Image gameBackgroundImg = new ImageIcon(getClass().getResource("/images/forest_background.png")).getImage();
	ImagePanel gameBackground = new ImagePanel(gameBackgroundImg);
    Image startGameBackgroundImg = new ImageIcon(getClass().getResource("/images/start game screen.png")).getImage();
    ImagePanel startGameBackground = new ImagePanel(startGameBackgroundImg);

    //bear
    JLabel bearLabel = new JLabel(); 
    
    //clothes on bear
    JLayeredPane clothesPane = new JLayeredPane();
    JLabel shirtLabel = new JLabel();
    JLabel pantsLabel = new JLabel();
    JLabel hatsLabel = new JLabel();

    //shirts
    Clothes noShirt = new Clothes(0, 0, 0, "/images/no clothing.png");
    Clothes fishShirt = new Clothes(0, 2, 3, "/images/fish shirt.png");    
    Clothes sunShirt = new Clothes(0, 2, 4, "/images/sun shirt.png");
    Clothes jbShirt = new Clothes(0, 1, 5, "/images/JB shirt.png");
    Clothes heartShirt = new Clothes(2, 5, 0, "/images/heart shirt.png");
    Clothes tuxShirt = new Clothes(5, 0, 2, "/images/tux shirt.png");

    //pants 
    Clothes noPants = new Clothes(0, 0, 0, "/images/no clothing.png");
    Clothes jeansPants = new Clothes(1, 0, 5, "/images/jeans pants.png");
    Clothes frillySkirt = new Clothes(4, 4, 2, "/images/frilly skirt.png");
    Clothes tutuSkirt = new Clothes(1, 5, 0, "/images/tutu skirt.png");
    Clothes greenShorts = new Clothes(1, 3, 2, "/images/green shorts.png");
    Clothes tuxPants = new Clothes(5, 0, 2, "/images/tux pants.png");

    //hats / accessories
    Clothes noHat = new Clothes(0, 0, 0, "/images/no clothing.png");
    Clothes ballCap = new Clothes(0, 1, 4, "/images/ball cap.png");
    Clothes catBeanie = new Clothes(0, 5, 2, "/images/cat beanie.png");
    Clothes glasses = new Clothes(4, 1, 3, "/images/glasses.png");
    Clothes bandana = new Clothes(0, 0, 5, "/images/bandana.png");
    Clothes topHat = new Clothes(5, 0, 2, "/images/tiny top hat.png");

    //shirt panel and buttons
    JPanel clothesButtonPanel = new JPanel();
    JButton[] clothesButtons = new JButton[6];

    //next and done buttons
    JButton[] optionsButtons = new JButton[4];
    ImageIcon shirtIcon = new ImageIcon(getClass().getResource("/images/shirt icon.png"));
    ImageIcon pantsIcon = new ImageIcon(getClass().getResource("/images/pants icon.png"));
    ImageIcon hatsIcon = new ImageIcon(getClass().getResource("/images/hat icon.png"));
    ImageIcon doneIcon = new ImageIcon(getClass().getResource("/images/all done icon.png"));

    //text panel
    JLabel speechLabel = new JLabel();
    ImageIcon speechBubble = new ImageIcon(getClass().getResource("/images/speech bubble.png"));
    ImageIcon gameOverSpeech = new ImageIcon(getClass().getResource("/images/speech bubble game over.png"));
    Font comicSansFont = new Font("Comic Sans MS", Font.PLAIN, 24);

    //game over text and buttons
    JLabel gameOverLabel = new JLabel();
    ImageIcon gameOverBubble = new ImageIcon(getClass().getResource("/images/win or lose bubble.png"));
    JButton playAgainButton = new JButton();
    ImageIcon playAgainImg = new ImageIcon(getClass().getResource("/images/play again bubble.png"));
    ImageIcon playAgainDarkerImg = new ImageIcon(getClass().getResource("/images/play again bubble darker.png"));

    //start game button
    JButton playButton = new JButton();
    ImageIcon playButtonImg = new ImageIcon(getClass().getResource("/images/play game button.png"));
    ImageIcon playButtonDarkerImg = new ImageIcon(getClass().getResource("/images/play game darker button.png"));

    //style type chosen
    String styleType;

    //clothing currently on bear
    Clothes activeShirt;
    Clothes activePants;
    Clothes activeHat;

    //final amount of fancy, cute, and cool points
    int fancyTotal;
    int cuteTotal;
    int coolTotal;

    //music
    Clip backgroundClip;


    DressUp(){
        frame.setSize(boardWidth, boardHeight);
		frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //x button closes program
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        startMenu();
        playBackgroundMusic();

    }

    void startMenu(){
        startGameBackground.setLayout(null);
        frame.add(startGameBackground);

        playButton.setIcon(playButtonImg);
        //sets background of button to clear
        playButton.setOpaque(false);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);

        playButton.setText("Let's Play!");
        playButton.setHorizontalTextPosition(JLabel.CENTER);
        playButton.setFont(comicSansFont);
        playButton.setBounds(490, 460, 260, 120);

        startGameBackground.add(playButton);

        buttonHoverEffect(playButton, playButtonDarkerImg, playButtonImg);
        
        for (ActionListener al : playButton.getActionListeners()) {
                playButton.removeActionListener(al);
            }
        playButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //System.out.println("test game over");
                    frame.remove(startGameBackground);
                    game();
                    shirtMenu();
                    frame.revalidate();
                    frame.repaint();
                }
            });
    }


    void gameOver(){
        gameBackground.remove(clothesButtonPanel);
        //gameBackground.remove(speechLabel);
        for (JButton button : optionsButtons){
            gameBackground.remove(button);
        }

        speechLabel.setBounds(120, 15, 360, 100);
        speechLabel.setIcon(gameOverSpeech);

        gameBackground.add(gameOverLabel);
        gameOverLabel.setBounds(380, 280, 360, 100);
        gameOverLabel.setIcon(gameOverBubble);
        gameOverLabel.setFont(comicSansFont);
        gameOverLabel.setHorizontalTextPosition(JLabel.CENTER); //allows text on top of image icon

        gameBackground.add(playAgainButton);
        playAgainButton.setIcon(playAgainImg);
        //sets background of button to clear
        playAgainButton.setOpaque(false);
        playAgainButton.setContentAreaFilled(false);
        playAgainButton.setBorderPainted(false);

        playAgainButton.setText("Play Again?");
        playAgainButton.setHorizontalTextPosition(JLabel.CENTER);
        playAgainButton.setFont(comicSansFont);
        playAgainButton.setBounds(480, 400, 260, 120);

        clothesPane.setLocation(70, 90);

        if(styleType == "FANCY"){
            fancyTotal = (activeShirt.fancyAmt + activePants.fancyAmt + activeHat.fancyAmt);
            //System.out.println(fancyTotal);
            if(fancyTotal > 8) {
                gameOverLabel.setText("very FANCY!");
                speechLabel.setText("i look so DAPPER!");
                }
            else{
                gameOverLabel.setText("not FANCY enough!");
                speechLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 19));
                speechLabel.setText("<html>i was supposed to go a wedding<br/> bro... MY WEDDING</html>");
            }
        }
        else if (styleType == "CUTE"){
            //System.out.println(cuteTotal);
            cuteTotal = (activeShirt.cuteAmt + activePants.cuteAmt + activeHat.cuteAmt);
            if(cuteTotal > 8) {
                gameOverLabel.setText("very CUTE!");
                speechLabel.setText("i feel so ADORABLE!!");
                }
            else {
                gameOverLabel.setText("not CUTE enough!");
                speechLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
                speechLabel.setText("<html>how tf am i supposed to go to<br/>my playdate looking like this</html>");                
            }
        }
        else{
            //System.out.println(coolTotal);
            coolTotal = (activeShirt.coolAmt + activePants.coolAmt + activeHat.coolAmt);
            if(coolTotal > 8) {
                gameOverLabel.setText("very COOL!");
                speechLabel.setText("i look so RAD");
                }
            else{ 
                gameOverLabel.setText("not COOL enough!");
                speechLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
                speechLabel.setText("<html>how will i ever fit in<br/>with the cool kids now :(</html>");
            }
        }

        //adds hovering effects to play again button
        buttonHoverEffect(playAgainButton, playAgainDarkerImg, playAgainImg);

        for (ActionListener al : playAgainButton.getActionListeners()) {
            playAgainButton.removeActionListener(al);
        }
        playAgainButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    gameBackground.remove(playAgainButton);
                    gameBackground.remove(gameOverLabel);
                    gameBackground.remove(clothesButtonPanel);
                    frame.remove(gameBackground);
                    startMenu();
                    frame.repaint();
                    frame.revalidate();
                }
            });
    }

    void game(){

        gameBackground.setLayout(null);

        resetGame();

        //chooses style type for the round
        styleType();

        //adds bear
        bearLabel.setIcon(new ImageIcon(getClass().getResource("/images/brown bear.png")));
        clothesPane.add(bearLabel, 0);
        Dimension size = bearLabel.getPreferredSize();
        bearLabel.setBounds(0, 0, size.width, size.height);

       //shirt label
        shirtLabel.setBounds(0, 0, size.width, size.height);
        shirtLabel.setOpaque(false);

        //pants label
        pantsLabel.setBounds(0, 0, size.width, size.height);
        pantsLabel.setOpaque(false);

        //hats label
        hatsLabel.setBounds(0, 0, size.width, size.height);
        hatsLabel.setOpaque(false);

        //shirts appear above pants
        clothesPane.add(shirtLabel, Integer.valueOf(2));
        clothesPane.add(pantsLabel, Integer.valueOf(1));
        clothesPane.add(hatsLabel, Integer.valueOf(2));

        clothesPane.setBounds(0, 0, size.width, size.height);  
        gameBackground.add(clothesPane);
        clothesPane.setLayout(null);

        frame.add(gameBackground);


        //adds text panel
        gameBackground.add(speechLabel);
        speechLabel.setIcon(speechBubble);
        speechLabel.setBounds(15, 550, 360, 100);
        speechLabel.setFont(comicSansFont);
        speechLabel.setText("make me look " + styleType);
        speechLabel.setHorizontalTextPosition(JLabel.CENTER); //allows text on top of image icon
        
        //add button panel
        clothesButtonPanel.setBounds(400, 10, 365, 540);
        gameBackground.add(clothesButtonPanel, BorderLayout.EAST);

        //adds clothes buttons
        clothesButtonPanel.setLayout(new GridLayout(3, 2));
        for (int i = 0; i < 6; i ++){
            JButton tile = new JButton();
            tile.setBackground(new Color(183, 203, 237));
            clothesButtons[i] = tile;
            clothesButtonPanel.add(tile);
            tile.setFocusable(false); //hides square around image when clicked
            tile.setOpaque(true);
        }

        //options and done buttons
        for (int i = 0; i < 4; i++){
            JButton tile = new JButton();
            tile.setBackground(new Color(200, 203, 250));
            optionsButtons[i] = tile;
            gameBackground.add(tile);
            tile.setBounds(435 + (i * 80), 580, 60, 45);
            tile.setFocusable(false); //hides square around image when clicked
            tile.setOpaque(true);
        }

        optionsButtons[0].setIcon(shirtIcon);
        optionsButtons[1].setIcon(pantsIcon);
        optionsButtons[2].setIcon(hatsIcon);
        optionsButtons[3].setIcon(doneIcon);

        //if shirts button is clicked
        for (ActionListener al : optionsButtons[0].getActionListeners()) {
            optionsButtons[0].removeActionListener(al);
        }
        optionsButtons[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    shirtMenu();
                }
            });

        //if pants button is clicked
        for (ActionListener al : optionsButtons[1].getActionListeners()) {
            optionsButtons[1].removeActionListener(al);
        }
        optionsButtons[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    pantsMenu();
                }
            });

        //if hats button is clicked
        for (ActionListener al : optionsButtons[2].getActionListeners()) {
            optionsButtons[2].removeActionListener(al);
        }
        optionsButtons[2].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    hatsMenu();
                }
            });

        //if done button is clicked
        for (ActionListener al : optionsButtons[3].getActionListeners()) {
            optionsButtons[3].removeActionListener(al);
        }
        optionsButtons[3].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (JButton button : optionsButtons) {
                        gameBackground.remove(button);
                    }
                    gameOver();
                    frame.revalidate();
                    frame.repaint();  
                }
            });


        //image icon of shirts on buttons
        noShirt.buttonImg("/images/no shirt icon.png");
        fishShirt.buttonImg("/images/fish shirt icon.png");        
        sunShirt.buttonImg("/images/sun shirt icon.png");
        jbShirt.buttonImg("/images/JB shirt icon.png");
        heartShirt.buttonImg("/images/heart shirt icon.png");
        tuxShirt.buttonImg("/images/tux shirt icon.png");

        //pants on buttons
        noPants.buttonImg("/images/no pants icon.png");
        jeansPants.buttonImg("/images/jeans pants icon.png");
        tuxPants.buttonImg("/images/tux pants icon.png");
        frillySkirt.buttonImg("/images/frilly skirt icon.png");
        tutuSkirt.buttonImg("/images/tutu skirt icon.png");
        greenShorts.buttonImg("/images/green shorts icon.png");

        //hats / accessories on buttons
        noHat.buttonImg("/images/no hats icon.png");
        topHat.buttonImg("/images/tiny top hat icon.png");
        ballCap.buttonImg("/images/ball cap icon.png");
        catBeanie.buttonImg("/images/cat beanie icon.png");
        bandana.buttonImg("/images/bandana icon.png");
        glasses.buttonImg("/images/glasses icon.png");
    }

    void resetGame(){
        //clears panel
        clothesButtonPanel.removeAll();

        //clears the clothes layered pane
        clothesPane.removeAll();
        clothesPane.revalidate();
        clothesPane.repaint();

        //clears clothes buttons
        clothesButtonPanel.removeAll();
        clothesButtonPanel.revalidate();
        clothesButtonPanel.repaint();

        //sets default clothing as nothing
        activeHat = noHat;
        activePants = noPants;
        activeShirt = noShirt;

        //sets bear to have no clothes from the start
        shirtLabel.setIcon(noShirt.clothesOnBear);
        pantsLabel.setIcon(noPants.clothesOnBear);
        hatsLabel.setIcon(noHat.clothesOnBear);

        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();

    }

    
    //if one of the shirts are clicked
    void shirtButtonPressed(JButton tile, Clothes shirt){
        //removes the old action listeners
        for (ActionListener al : tile.getActionListeners()) {
        tile.removeActionListener(al);
        }

        tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    activeShirt = shirt;
                    shirtLabel.setIcon(shirt.clothesOnBear);
                }
            });
        }

    //if one of the pants are clicked
    void pantsButtonPressed(JButton tile, Clothes pants){
        //removes the old action listeners
        for (ActionListener al : tile.getActionListeners()) {
        tile.removeActionListener(al);
        }

        tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    activePants = pants;
                    pantsLabel.setIcon(pants.clothesOnBear);
                }
            });
        }

    //if one of the hats are clicked
    void hatsButtonPressed(JButton tile, Clothes hat){
        //removes the old action listeners
        for (ActionListener al : tile.getActionListeners()) {
        tile.removeActionListener(al);
    }

        tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    activeHat = hat;
                    hatsLabel.setIcon(hat.clothesOnBear);
                }
            });

        
        }

        
    void shirtMenu(){
        //adds images to each button
        clothesButtons[0].setIcon(noShirt.clothesIcon);
        clothesButtons[1].setIcon(fishShirt.clothesIcon);
        clothesButtons[2].setIcon(sunShirt.clothesIcon);
        clothesButtons[3].setIcon(jbShirt.clothesIcon);
        clothesButtons[4].setIcon(heartShirt.clothesIcon);
        clothesButtons[5].setIcon(tuxShirt.clothesIcon);

        shirtButtonPressed(clothesButtons[0], noShirt);
        shirtButtonPressed(clothesButtons[1], fishShirt);
        shirtButtonPressed(clothesButtons[2], sunShirt);
        shirtButtonPressed(clothesButtons[3], jbShirt);
        shirtButtonPressed(clothesButtons[4], heartShirt);
        shirtButtonPressed(clothesButtons[5], tuxShirt);
    }

    void pantsMenu(){
        clothesButtons[0].setIcon(noPants.clothesIcon);
        clothesButtons[1].setIcon(jeansPants.clothesIcon);
        clothesButtons[2].setIcon(frillySkirt.clothesIcon);
        clothesButtons[3].setIcon(tutuSkirt.clothesIcon);
        clothesButtons[4].setIcon(greenShorts.clothesIcon);
        clothesButtons[5].setIcon(tuxPants.clothesIcon);

        pantsButtonPressed(clothesButtons[0], noPants);
        pantsButtonPressed(clothesButtons[1], jeansPants);
        pantsButtonPressed(clothesButtons[2], frillySkirt);
        pantsButtonPressed(clothesButtons[3], tutuSkirt);
        pantsButtonPressed(clothesButtons[4], greenShorts);
        pantsButtonPressed(clothesButtons[5], tuxPants);
    }

    void hatsMenu(){
        clothesButtons[0].setIcon(noHat.clothesIcon);
        clothesButtons[1].setIcon(ballCap.clothesIcon);
        clothesButtons[2].setIcon(catBeanie.clothesIcon);
        clothesButtons[3].setIcon(glasses.clothesIcon);
        clothesButtons[4].setIcon(bandana.clothesIcon);
        clothesButtons[5].setIcon(topHat.clothesIcon);

        hatsButtonPressed(clothesButtons[0], noHat);
        hatsButtonPressed(clothesButtons[1], ballCap);
        hatsButtonPressed(clothesButtons[2], catBeanie);
        hatsButtonPressed(clothesButtons[3], glasses);
        hatsButtonPressed(clothesButtons[4], bandana);
        hatsButtonPressed(clothesButtons[5], topHat);
    }

    //sets style type 
    public String styleType(){
        double randInt = Math.floor(Math.random() * 3);

        if(randInt == 0) return styleType = "FANCY";
        else if (randInt == 1) return styleType = "CUTE";
        else return styleType = "COOL";
    }

    //adds mouse hovering effects to buttons
    void buttonHoverEffect(JButton button, ImageIcon mouseOnImg, ImageIcon mouseOffImg){
        //when the mouse overs a button, it changes color slightly
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(mouseOnImg);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(mouseOffImg);
            }
        });
    }

     void playBackgroundMusic(){
        try{
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/I can only dream.wav"));
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioStream);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop music 
            
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
       new DressUp();
    }

}

//background image
class ImagePanel extends JPanel {
    private Image backgroundImage;

    public ImagePanel(Image image){
        this.backgroundImage = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

    }
}
