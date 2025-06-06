package Rooms;

import CharacterResources.Enemies.GolemBoss;
import GUIDesign.GamePlayComponent;

import java.awt.*;

/**
 * A room which contains the boss at the end of the adventure. Also handles anything room-specific to the boss.
 */
public class BossRoom extends Room {

    private GolemBoss golemBoss;

    /**
     * Contructs BossRoom object with specified parameters.
     * @param parentComponent The GamePlayComponent to which the Room is drawn to.
     * @param stairID The stairID which this room is associated with.
     */
    public BossRoom(GamePlayComponent parentComponent, int stairID)
    {
        super(parentComponent,stairID);
        golemBoss = new GolemBoss(6,6, parentComponent);
        addEnemy(golemBoss);
    }

    /**
     * Draws the Room. Draws win screen once boss is dead.
     * @param g The graphics context that the room is drawn to.
     */
    @Override
    public void drawRoom(Graphics g)
    {
        super.drawRoom(g);
        if(golemBoss.getCurrentHP() <= 0)
        {
            g.setColor(Color.GREEN);
            g.setFont(new Font("BOSS!", Font.PLAIN, 50));
            g.drawString("YOU WIN!", parentComponent.getPreferredSize().width*6/16,parentComponent.getPreferredSize().height*6/12);
        }
        else {
            drawBossBar(g);
        }
    }

    private void drawBossBar(Graphics g)
    {
        int barX = parentComponent.getPreferredSize().width*5/16;
        int barY = parentComponent.getPreferredSize().height/32;
        int barLength = parentComponent.getPreferredSize().width*6/16;
        int currentBarLength = (int)((((double)golemBoss.getCurrentHP())/golemBoss.getMaxHP()) * barLength);

        g.setColor(Color.RED);
        g.fillRect(barX, barY, currentBarLength,
                parentComponent.getPreferredSize().height/40);
        g.setColor(Color.BLACK);
        g.drawRect(barX, barY, currentBarLength,
                parentComponent.getPreferredSize().height/40);

        g.setColor(Color.GREEN);
        g.setFont(new Font("BOSS!", Font.PLAIN, parentComponent.getPreferredSize().height/40));
        g.drawString("EVIL GOLEM", barX, barY);
    }

}
