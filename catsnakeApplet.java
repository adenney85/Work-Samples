package catsnake;
//Alex Denney
//CISS 241-300
//Catsnake Assignment

/*
 * The purpose of this program is to draw a gradient sky, grass, a tree with leaves, sun and catsnake in an applet. All drawing will be 2D
 * objects with the exception of catsnake, which is a linepath. Catsnake move by looping the repaint method that will cover the old
 * catsnake with grass and then draw the new catsnake a few pixels over. Flicker is reduced by making the thread sleep for a very short
 * period. Catsnake will stop just short of the other side of the scene. This applet runs in an applet viewer.
 */
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class catsnakeApplet extends JApplet
{ 
	int x = 0;
	boolean stopflag = false;
	public void init()
	{
		setSize(1000, 600);
	}
    public void update(Graphics g)
    {
    	paint(g);
    }
    public void paint(Graphics g)//draw scene and catsnake, calls to redraw catsnake for animation
    {
    	Graphics2D g2 = (Graphics2D)(g);
        Color color1 = Color.cyan;
        Color color2= Color.white; 
        float x1 = 0;
        float y1 = 0;
        float x2 = 1000;
        float y2 = 400;
        boolean cyclic = true;
    	GradientPaint gp = new GradientPaint(x1, y1, color1, x2, y2, color2, cyclic);//Makes Sky Gradient
    	Rectangle2D sky = new Rectangle2D.Float(0, 0, 1000, 400);
    	Rectangle2D grass = new Rectangle2D.Float(0, 400, 1000, 200);
    	if (stopflag == false)
    	{
    		super.paint(g);
    		g2.setPaint(gp);
    		g2.fill(sky);//draw sky
    		g2.setPaint(Color.darkGray);
    		g2.setStroke(new BasicStroke(30));
    		g2.draw(new Line2D.Float(700, 265, 700, 385));//draw tree trunk
    		g2.setPaint(Color.yellow);
    		g2.fill(new Ellipse2D.Double(120, 120, 120, 120));//draw sun
    		g2.setPaint(Color.green);
    		g2.fill(new Arc2D.Double(625, 210, 150, 120, 0, 180, Arc2D.CHORD));//draw leaves
    		stopflag = true;
    	}
    	g2.setPaint(Color.green);
    	g2.fill(grass);//draw grass
        GeneralPath catsnake = new GeneralPath();//make catsnake shape object
        catsnake.moveTo(50 + x, 450);
        catsnake.lineTo(50 + x, 520);
        catsnake.lineTo(120 + x, 520);
        catsnake.lineTo(120 + x, 450);
        catsnake.lineTo(105 + x, 460);
        catsnake.lineTo(65 + x, 460);
        catsnake.closePath();
        g2.setStroke(new BasicStroke(7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));//makes catsnake rounded 
        g2.setPaint(Color.red);
        g2.draw(catsnake);//draw cat snake
        g2.setPaint(Color.blue);
        g2.fill(new Ellipse2D.Double(95 + x, 480, 13, 13));//draw eyes
        g2.fill(new Ellipse2D.Double(65 + x, 480, 13, 13));
        try
        {
            Thread.sleep(100);//slows catsnake so it is visible, reduces flicker
        }
        catch(InterruptedException e)//need to catch interrupted exception
        {
        	
        }
        if(x <= (getSize().width)-200)
        {
        x += 9;//increments catsnakes coordinates.
        repaint();//draws the new catsnake
        }
    }
}