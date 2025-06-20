package GUIDesign;

import javax.swing.*;
import java.awt.*;

/**
 *  Represents the environment for running a Stones n Spells game
 */
public class MainFrame extends JFrame
{
    /**
     * The default width of the JFrame.
     */
    public static final int WIDTH = 1200;
    /**
     * The default Height of the JFrame.
     */
    public static final int HEIGHT = 900;
    private JPanel panel;
    private IndicatorComponent topUI;
    private GamePlayComponent gameWorld;
    private GridBagLayout compOrg;

    /**
     * Constructs a new MainFrame object, initializes JFrame and establishes Component hierarchy.
     * @param compOrg A GridBagLayout parameter, it organizes the two Components within the JFrame.
     */
    public MainFrame(GridBagLayout compOrg)
    {
        this.compOrg = compOrg;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(WIDTH, HEIGHT));
        //setBackground(Color.BLACK);

        panel = new JPanel(compOrg);
        panel.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        panel.setBackground(Color.BLACK);


        //Dealing with GridBag

        //Beginning of constructing GamePlayComponent
        gameWorld = new GamePlayComponent(800, 600, this);
        GridBagConstraints gameConstraints = new GridBagConstraints();

        addToPanel(gameWorld,panel, compOrg, gameConstraints, 0,1,1,1);
        System.out.println("Preferred Size of Component gameWorld" + gameWorld.getPreferredSize());

        GridBagConstraints UIConstraints = new GridBagConstraints();
        topUI = new IndicatorComponent(800, 200, gameWorld);

        addToPanel(topUI, panel, compOrg, UIConstraints,0,0,1,1);

        System.out.println("Preferred Size of Component topUI: " + topUI.getPreferredSize());

        //topUI.setBounds(0,0,WIDTH, HEIGHT/6);

        System.out.println("Panel Width: " + panel.getWidth());





        //System.out.println(panel.getComponents());
        add(panel);

        //Does as it says.
        pack();
        revalidate();
        setVisible(true);
    }

    /**
     * Returns the IndicatorComponent object which is painted at the top of the JPanel and used to display basic player info.
     * @return The aforementioned IndicatorComponent.
     */
    public IndicatorComponent getTopUI()
    {
        return topUI;
    }

    /**
     * Resets the game.
     * Calls resetGame methods in associated IndicatorComponent and GamePlayComponent objects.
     */
    public void resetFrame()
    {
        //System.out.println("RESET CALLED");
        gameWorld.resetGame();
        topUI.resetGame();

        revalidate();
        repaint();
        gameWorld.requestFocus();
    }

    /**
     * Launches the Stones n Spells game
     * @param args not used
     */
    public static void main(String[] args)
    {
        MainFrame run = new MainFrame(new GridBagLayout());
    }

    //Nice helper method adapted off of something found on StackOverflow
    private void addToPanel(Component component, JPanel myJPanel, GridBagLayout layout, GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight)
    {


        gbc.gridx = gridx;
        gbc.gridy = gridy;

        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;

        layout.setConstraints(component, gbc);
        myJPanel.add(component);

    }

}
