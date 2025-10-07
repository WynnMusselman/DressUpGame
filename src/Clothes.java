import java.awt.Image;

import javax.swing.ImageIcon;

//new class that deals with clothes and their attributes
public class Clothes {
    int fancyAmt;
    int cuteAmt;
    int coolAmt;

    ImageIcon clothesOnBear;

    ImageIcon clothesIcon;

    public Clothes(int fancy, int cute, int cool, String clothesImg){
        //each item can be rated on a scale 0-5
        fancyAmt = fancy;
        cuteAmt = cute;
        coolAmt = cool;
        clothesOnBear = new ImageIcon(getClass().getResource(clothesImg));

        clothesIcon = null;
    }

    //the image that will go on the button
    public void buttonImg(String fileName){
        Image shirtImg = new ImageIcon(getClass().getResource(fileName)).getImage();
        //resizes image to fit onto button
        clothesIcon = new ImageIcon(shirtImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));
    }

}
