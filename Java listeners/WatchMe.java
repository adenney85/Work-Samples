//Alex Denney
//CISS 111-300
//Programming Assignment 8 Chapter 14

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class WatchMe extends Applet
{
   public void init()
   {
      addMouseListener(new TheMouseListener());
      addMouseMotionListener(new TheMouseMotionListener());
   }
   final int eyeWidth = 60;
   final int eyeHeight = 60;
   final int pupilSize = 20;
   final int eye1X = 70;
   final int eye1Y = 40;
   final int eye2X = 150;
   final int eye2Y = 40;
   boolean Exited = false;
   enum eyeStatus {FORWARD, UP, DOWN, LEFT, RIGHT};
   eyeStatus currentStatus = eyeStatus.FORWARD;
   public void paint(Graphics g)
   {
      super.paint(g);
      g.setColor(Color.black);
      g.drawOval(eye1X, eye1Y, eyeWidth, eyeHeight);
      g.drawOval(eye2X, eye2Y, eyeWidth, eyeHeight);
      switch(currentStatus)
      {
         case FORWARD:
            lookForward(g);
         break;
         case UP:
            lookUp(g);
         break;
         case DOWN:
            lookDown(g);
         break;
         case LEFT:
            lookLeft(g);
         break;
         case RIGHT:
            lookRight(g);
         break;
      }
   }
   public void lookForward(Graphics g)
   {
      g.fillOval(eye1X + (eyeWidth/2)-(pupilSize/2), eye1Y+(eyeHeight/2)-(pupilSize/2), pupilSize, pupilSize);
      g.fillOval(eye2X + (eyeWidth/2)-(pupilSize/2), eye2Y+(eyeHeight/2)-(pupilSize/2), pupilSize, pupilSize);
   }
   public void lookUp(Graphics g)
   {
      g.fillOval(eye1X + (eyeWidth/2)-(pupilSize/2), eye1Y, pupilSize, pupilSize);
      g.fillOval(eye2X + (eyeWidth/2)-(pupilSize/2), eye2Y, pupilSize, pupilSize);
   }
   public void lookDown(Graphics g)
   {
      g.fillOval(eye1X + (eyeWidth/2)-(pupilSize/2), eye1Y + eyeHeight-pupilSize, pupilSize, pupilSize);
      g.fillOval(eye2X + (eyeWidth/2)-(pupilSize/2), eye2Y + eyeHeight-pupilSize, pupilSize, pupilSize);
   }
   public void lookLeft(Graphics g)
   {
      g.fillOval(eye1X, eye1Y+(eyeHeight/2)-(pupilSize/2), pupilSize, pupilSize);
      g.fillOval(eye2X, eye1Y+(eyeHeight/2)-(pupilSize/2), pupilSize, pupilSize);
   }
   public void lookRight(Graphics g)
   {
      g.fillOval(eye1X+eyeWidth-pupilSize, eye1Y+(eyeHeight/2)-(pupilSize/2), pupilSize, pupilSize);
      g.fillOval(eye2X+eyeWidth-pupilSize, eye1Y+(eyeHeight/2)-(pupilSize/2), pupilSize, pupilSize);
   }
   private class TheMouseListener implements MouseListener
   {
      public void mousePressed(MouseEvent e)
      {
      }
      public void mouseClicked(MouseEvent e)
      {
      }
      public void mouseReleased(MouseEvent e)
      {
      }
      public void mouseEntered(MouseEvent e)
      {
         Exited = false;
      }
      public void mouseExited(MouseEvent e)
      {
         currentStatus = eyeStatus.FORWARD;
         Exited = true;
         repaint();
      }
   }
   private class TheMouseMotionListener implements MouseMotionListener
   {
      public void mouseDragged(MouseEvent e)
      {
      }
      public void mouseMoved(MouseEvent e)
      {
         eyeStatus oldStatus = currentStatus;
         if(!Exited)
         {
            if(e.getY() < eye1Y)
               currentStatus = eyeStatus.UP;
            else if(e.getY() > eye1Y+eyeHeight)
               currentStatus = eyeStatus.DOWN;
            else if(e.getX() < eye1X)
               currentStatus = eyeStatus.LEFT;
            else if(e.getX() > eye2X + eyeWidth)
               currentStatus = eyeStatus.RIGHT;
            else
               currentStatus = eyeStatus.FORWARD;
         }
         else
         {
            currentStatus = eyeStatus.FORWARD;
         }
         if(oldStatus!=currentStatus)
         repaint();
      }
   }
}
