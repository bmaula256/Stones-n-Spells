package MapDesign;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import CharacterResources.*;

/**
 * A class which represents the upper UI of the game which displays basic info such as acquired items and current health.
 */
public class IndicatorComponent extends JComponent {

    private final ArrayList<Item> ITEMS = new ArrayList<Item>();
    private GamePlayComponent gameWorld;

    /**
     * Constructs IndicatorComponent with specified parameters.
     * @param inWidth The preferred width of the IndicatorComponent
     * @param inHeight The preferred height of the IndicatorComponent.
     * @param inGameplayComponent The GamePlayComponent which is initialized in the MainFrame.
     */
    public IndicatorComponent(int inWidth, int inHeight, GamePlayComponent inGameplayComponent)
    {
        setPreferredSize(new Dimension(inWidth,inHeight));
        setMinimumSize(new Dimension(inWidth,inHeight));

        gameWorld = inGameplayComponent;

        System.out.println("Indicator Component constructed");
        System.out.println("Current Height: " + getHeight());
        System.out.println("Current Width: " + getWidth());
    }

    /**
     * Calls super, then paints HP, items, etc.
     * If the player is dead, instead paints the "Game-Over" message.
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(gameWorld.getPlayer().isDead())
        {
            g.setColor(Color.RED);
            g.setFont(new Font("Wongus", Font.PLAIN, 25));
            g.drawString("GAME OVER",super.getWidth()*2/5,super.getHeight()*2/6 + super.getHeight()/10);
            return;
        }
        paintHP(g);
        paintItems(g);
        paintBorderDeco(g);
    }

    /**
     * Adds Item to Indicator component.
     * @param inItem The Item to be added.
     */
    public void addItem(Item inItem)
    {
        ITEMS.add(inItem);
    }

    private void paintBorderDeco(Graphics g)
    {
        g.setColor(new Color(0,0,128));
        g.fillRect(0,0,getWidth(),getHeight()/50);
        g.fillRect(getWidth()-getWidth()/200,0,getWidth()/200, getHeight());
        g.fillRect(0,0,getWidth()/200,getHeight());
        g.fillRect(0,getHeight()-getHeight()/50,getWidth(),getHeight()/50);
    }

    private void paintHP(Graphics g)
    {
        g.setColor(Color.RED);
        int currentPos = super.getWidth()*3/5;

        g.setFont(new Font("Wongus", Font.PLAIN, 25));
        g.drawString("HP:",currentPos,super.getHeight()/6 + super.getHeight()/10);

        for(int count = 0; count < gameWorld.getPlayer().getCurrentHP(); count++)
        {

            g.fillRect(currentPos + 50 + count*20, super.getHeight()/6, super.getWidth()/100, super.getHeight()/10);
        }
    }

    private void paintItems(Graphics g)
    {
        System.out.println("paintItems called");
        int x = 10;
        int y = 10;
        for(Item i : ITEMS)
        {
            System.out.println("Item drawn");
            i.drawItem(this,g,x,y);
            x += 55;
            if(x + 50 >= super.getWidth()*3/5)
            {
                y+= 10;
                x = 0;
            }
        }
    }
}
