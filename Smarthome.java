
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

interface Observer{
    public void update(String p);
}

class Homepage extends JFrame implements Observer{
    private JToggleButton btnonoff;
    private JToggleButton btnsettings;
    private JLabel lbltime;

    public Homepage(){
        setSize(450,260);
        setTitle("Switch");
        setLocation(20,20);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel1=new JPanel(new GridLayout(3,1));

        btnonoff=new JToggleButton("ON");
        btnonoff.setFont(new Font("",1,18));
        btnonoff.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){

                Observable.notifyobjects(btnonoff.getText());

                if(btnonoff.getText()=="ON"){
                    btnonoff.setText("OFF");
                }else{
                    btnonoff.setText("ON");
                }



            }
        });

        panel1.add(btnonoff);

        btnsettings=new JToggleButton("Settings");
        btnsettings.setFont(new Font("",1,18));
        btnsettings.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                new Controller().setVisible(true);


            }
        });
        panel1.add(btnsettings);
        //----------------------------------

        lbltime=new JLabel();
        lbltime.setFont(new Font("Arial", Font.PLAIN, 24));
        panel1.add(lbltime);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();

        add("Center",panel1);


    }

    private void updateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
        String currentTime = dateFormat.format(new Date());
        lbltime.setText(currentTime);
    }


    public void update(String p){}
}


class Controller extends JFrame{
    private JList <String> list1;

    public Controller(){
        setSize(300,300);
        setTitle("Controller");
        setLocation(500,20);
        list1=new JList<>(new String[]{"Tv living room", "Speaker living room", "window living room","Tv dining room"});
        add(list1);

        ListSelectionListener selectionListener = new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedValue = list1.getSelectedValue();
                    if (selectedValue.equals("Tv living room")) {
                        new TVLivingRoom().setVisible(true);
                    }else if(selectedValue.equals("Speaker living room")){
                        //new TVdining().setVisible(true);
                    }
                }
            }
        };
        list1.addListSelectionListener(selectionListener);




    }
}

class TVLivingRoom extends JFrame{
    private JLabel lblstart;
    private JLabel lblmin;
    private JLabel lblend;
    private JLabel lblendmin;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JSpinner spinner3;
    private JSpinner spinner4;

    public TVLivingRoom(){
        setSize(500,200);
        setTitle("Tv Living Room");
        setLocation(500,330);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        int initialValue = 00;
        int minValue = 00;
        int maxValue = 23;
        int step = 1;
        SpinnerModel spinnerModel = new SpinnerNumberModel(initialValue, minValue, maxValue, step);

        spinner1 = new JSpinner(new SpinnerNumberModel(00,0,24,1));
        spinner2=new JSpinner(new SpinnerNumberModel(00,0,60,1));
        spinner3=new JSpinner(new SpinnerNumberModel(00,0,24,1));
        spinner4=new JSpinner(new SpinnerNumberModel(00,0,60,1));
        lblstart=new JLabel("Start Hour:");
        lblmin=new JLabel("Minute:");
        lblend=new JLabel("End Hour:");
        lblendmin=new JLabel("Minute:");


        JPanel panel = new JPanel();
        panel.add(lblstart);
        panel.add(spinner1);
        panel.add(lblmin);
        panel.add(spinner2);
        panel.add(lblend);
        panel.add(spinner3);
        panel.add(lblendmin);
        panel.add(spinner4);

        add("South",panel);
    }

}


class Tvroom extends JFrame implements Observer{
    public JLabel lbltv;

    public Tvroom(){
        setSize(200,130);
        setTitle("Tv Living Room");
        setLocation(250,440);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        lbltv=new JLabel("OFF");

        add(lbltv);
    }


    public void update(String p){
        lbltv.setText(p);
    }

}

class Speakerroom extends JFrame implements Observer{
    private JLabel lblspeaker;

    public Speakerroom(){
        setSize(200,130);
        setTitle("Speaker Living Room");
        setLocation(250,610);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        lblspeaker=new JLabel("OFF");
        add(lblspeaker);

    }
    public void update(String p){
        lblspeaker.setText(p);
    }

}

class Window extends JFrame implements Observer{
    private JLabel lblWindow;

    public Window(){
        setSize(200,130);
        setTitle("Window Living Room");
        setLocation(20,440);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        lblWindow=new JLabel("OFF");
        add(lblWindow);

    }
    public void update(String p){
        lblWindow.setText(p);
    }
}

class TVdining extends JFrame implements Observer{
    private JLabel lbltvd;

    public TVdining(){
        setSize(200,130);
        setTitle("TV dining Room");
        setLocation(20,610);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        lbltvd=new JLabel("OFF");
        add(lbltvd);

    }
    public void update(String p){
        lbltvd.setText(p);
    }

}




class Observable{
    public static Observer[] observerArray=new Observer[0];

    private void extendsArray(){
        Observer[] tempArray=new Observer[observerArray.length+1];
        for (int i = 0; i < observerArray.length; i++){
            tempArray[i]=observerArray[i];
        }
        observerArray=tempArray;
    }



    public void addobserver(Observer ob){
        extendsArray();
        observerArray[observerArray.length-1]=ob;
        //notifyobjects(String p);
    }

    public static void notifyobjects(String p){
        for(Observer v1: observerArray){
            v1.update(p);
        }
    }
}


class Smarthome{
    public static void main(String[] args){
        Observable observable=new Observable();
        observable.addobserver(new Homepage());
        observable.addobserver(new Tvroom());
        observable.addobserver(new Speakerroom());
        observable.addobserver(new Window());
        observable.addobserver(new TVdining());

    }
}
