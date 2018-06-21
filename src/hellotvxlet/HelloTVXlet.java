/************************************
 * -----------22/06/2018------------*
 * EINDOPDRACHT DIGITAL BROADCAST   *
 * DEVIL OF THE MATCH vote carrousel*
 * -------------------------------- *
 * Docenten: Soontjens / Adriaensen *
 * -------------------------------- *
 * Jentl Van Gossem   2MTa A/V      *
 * Ronny Beeckmans    2MTa A/V      *
 * **********************************/

package hellotvxlet;

import java.awt.Color;
import javax.tv.xlet.*;

import org.bluray.ui.event.HRcEvent;
import org.davic.resources.ResourceClient;
import org.davic.resources.ResourceProxy;
import org.havi.ui.*;
import org.dvb.event.*;
import org.havi.ui.event.HBackgroundImageEvent;
import org.havi.ui.event.HBackgroundImageListener;


public class HelloTVXlet implements Xlet, ResourceClient, HBackgroundImageListener,UserEventListener {
    
        
    private HScreen screen;
    private HBackgroundDevice bgDevice;
    private HStillImageBackgroundConfiguration bgConfiguration;
    private int imageload;
    private HStaticText text;
    private HScene scene;
    private HBackgroundImage[] image=new HBackgroundImage [20]; //("devil0.png - devil19.png")
    private int current = 1;
    
    public void initXlet(XletContext ctx) throws XletStateChangeException
    {
        screen=HScreen.getDefaultHScreen(); //720*576
        bgDevice=screen.getDefaultHBackgroundDevice(); 
        if (bgDevice.reserveDevice(this))  //control display in Output
        {
            System.out.println("Background O.K.!");
        }
        
        HBackgroundConfigTemplate bgTemplate=new HBackgroundConfigTemplate();
        bgTemplate.setPreference(HBackgroundConfigTemplate.STILL_IMAGE, HBackgroundConfigTemplate.REQUIRED);
         bgConfiguration=(HStillImageBackgroundConfiguration) bgDevice.getBestConfiguration(bgTemplate);
    
        try 
        {
            bgDevice.setBackgroundConfiguration(bgConfiguration);
        }
        
        catch (java.lang.Exception e) 
            {
                System.out.println(e.toString());
            }
         
            for(int i=0;i<image.length;i++)
            {
                image[i]=new HBackgroundImage("devil" + i + ".png"); //Load array with images (devil0.png, devil1.png, ...)
            }
         
            for(int i=0;i<image.length;i++)
            { 
                image[i].load(this); // add interface HBackgroundImageListener to Xlet
            }
         
       UserEventRepository repo = new UserEventRepository ("name");
       repo.addAllArrowKeys();
       EventManager.getInstance().addUserEventListener((UserEventListener) this,repo);
       scene = HSceneFactory.getInstance().getDefaultHScene();
       text = new HStaticText("VOTE your DEVIL \n Enter to vote\n", 250, 30, 250, 130);  // ("text", x, y, W, H)
       text.setForeground(Color.YELLOW);     // text color
       
       scene.add(text);
       scene.validate();
       scene.setVisible(true);
    } 
       
    public void userEventReceived(UserEvent e)
    {
        if (e.getType()==HRcEvent.KEY_PRESSED)
        {
            if (e.getCode()==HRcEvent.VK_RIGHT)
            {
                text.setTextContent("Enter to vote ",HState.NORMAL_STATE);
                current++;
                if (current>image.length) current = 1;
            }
            if (e.getCode()==HRcEvent.VK_LEFT)
            {
                text.setTextContent("Enter to vote ",HState.NORMAL_STATE);
                current--;
                if (current<1) current = image.length;
            }
            if (e.getCode()==HRcEvent.VK_UP)
            {
                text.setTextContent("Enter to vote ",HState.NORMAL_STATE);
                current++;
            if (current>image.length) current = 1;
            }
            if (e.getCode()==HRcEvent.VK_DOWN)
            {
                text.setTextContent("Enter to vote ",HState.NORMAL_STATE);
                current--;
            if (current<1) current = image.length;
            }
            
            if (e.getCode()==HRcEvent.VK_ENTER)
            {
                System.out.println("ENTER");
                
                text.setTextContent(" ",HState.NORMAL_STATE);
                String player = "You vote Devil nr. " + (current -1) + "\n Thanks for voting!\n";
                if (current==1) player = "INVALID VOTE!\n Sellect Devil with arrows\n";
                text.setTextContent(text.getTextContent(HVisible.NORMAL_STATE)+ player, HVisible.NORMAL_STATE);                
            }
            try 
            {
                bgConfiguration.displayImage(image[current-1]);
            }  
            catch (Exception ex)
            {
                ex.printStackTrace();
                System.out.println(ex);
            }                         
        }
    }
    
    public void imageLoaded(HBackgroundImageEvent e) 
    {
       System.out.println("Image: " + imageload + " loaded");
       imageload++;
     if (imageload==19)
     {
       try 
       {
            bgConfiguration.displayImage(image[0]);
       } 
       catch (Exception ex) 
       {
            ex.printStackTrace();
       }
     }
    }
    public void pauseXlet()
    {       
    }

    public void startXlet() throws XletStateChangeException 
    {
        EventManager manager = EventManager.getInstance();
        UserEventRepository repository = new UserEventRepository("Example");
        
        repository.addKey(org.havi.ui.event.HRcEvent.VK_ENTER);
        
        manager.addUserEventListener(this, repository);
    }

    public boolean requestRelease(ResourceProxy proxy, Object requestData) {
       return false;
    }

    public void release(ResourceProxy proxy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void notifyRelease(ResourceProxy proxy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void imageLoadFailed(HBackgroundImageEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

  
    public void destroyXlet(boolean unconditional) throws XletStateChangeException {
      
    }

    private HStaticText setTextContent(int NORMAL_STATE) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    

}