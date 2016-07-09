import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;

public class Example01 extends Frame {
    /**
     * Instantiates an Example01 object.
     **/
    public static void main(String args[]) {
        new Example01();
    }

    /**
     * Our Example01 constructor sets the frame's size, adds the
     * visual components, and then makes them visible to the user.
     * It uses an adapter class to deal with the user closing
     * the frame.
     **/
    public Example01() {
        //Title our frame.
        super("path-finder");

        //Set the size for the frame.
        setSize(100,100);

        //We need to turn on the visibility of our frame
        //by setting the Visible parameter to true.
        setVisible(true);

        //Now, we want to be sure we properly dispose of resources
        //this frame is using when the window is closed.  We use
        //an anonymous inner class adapter for this.
        addWindowListener(new WindowAdapter()
                          {public void windowClosing(WindowEvent e)
                          {dispose(); System.exit(0);}
                          }
        );
    }

    /**
     * The paint method provides the real magic.  Here we
     * cast the Graphics object to Graphics2D to illustrate
     * that we may use the same old graphics capabilities with
     * Graphics2D that we are used to using with Graphics.
     **/
    public void paint(Graphics g) {
        //Here is how we used to draw a square with width
        //of 200, height of 200, and starting at x=50, y=50.

//        g.setColor(Color.red);
//        g.drawRect(50,50,200,200);

        //Let's set the Color to blue and then use the Graphics2D
        //object to draw a rectangle, offset from the square.
        //So far, we've not done anything using Graphics2D that
        //we could not also do using Graphics.  (We are actually
        //using Graphics2D methods inherited from Graphics.)
//        Graphics2D g2d = (Graphics2D)g;
//        g2d.setColor(Color.blue);
//        g2d.drawRect(75,75,300,200);
        g.setColor(Color.red);
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(Color.red);
        g.drawLine(50, 50, 50, 50);
    }
}