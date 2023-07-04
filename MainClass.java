package test_code;

import java.util.ArrayList;
import java.util.Arrays;

public class MainClass {

    public static void main(String[] args) {

        // Creating the Net
        Net net = new Net();

        // Creating the Events
        Event i = new Event();
        i.name = "i";
        Event j = new Event();
        j.name = "j";
        Event x = new Event();
        x.name = "x";
        Event y = new Event();
        y.name = "y";
        Event a = new Event();
        a.name = "a";
        Event t = new Event();
        t.name = "t";
        Event e = new Event();
        e.name = "e";
        
        // Add the events to the net
        for (Event event: new Event[] {i,j,x,y,a}) {
            net.add_event(event);
        }

        // Set the starting event
        net.start_event = a;

        // This Petri net represents the picture accompanying the code
        i.odds_previous_events.put(a, 0.5f);
        j.odds_previous_events.put(a, 0.5f);

        x.odds_previous_events.put(i, 0.4f);
        y.odds_previous_events.put(i, 0.4f);
        t.odds_previous_events.put(i, 0.2f);

        x.odds_previous_events.put(j, 0.25f);
        y.odds_previous_events.put(j, 0.75f);

        t.odds_previous_events.put(x, 0.7f);
        e.odds_previous_events.put(x, 0.3f);

        e.odds_previous_events.put(y, 1f);
        
        // Various tests and wether they are correct
        System.out.println("odds x given i:"); // correct
        System.out.println(net.simple_conditional(i, x));
        System.out.println("");

        System.out.println("odds x given e:"); // correct
        System.out.println(net.simple_conditional(e, x));
        System.out.println("");

        System.out.println("odds x given t:"); // correct
        System.out.println(net.simple_conditional(t, x));
        System.out.println("");

        System.out.println("odds i given t:"); // correct
        System.out.println(net.simple_conditional(t, i));
        System.out.println("");

        System.out.println("odds x given i or j:"); // correct
        System.out.println(net.two_before(i, j, x));
        System.out.println("");

        System.out.println("odds e given x or y:"); // correct
        System.out.println(net.two_before(x, y, e));
        System.out.println("");

        System.out.println("odds x given i or j:"); // correct
        System.out.println(net.two_known(i, j, x));
        System.out.println("");

        System.out.println("odds e given x or y:"); //correct 
        System.out.println(net.two_known(x, y, e));
        System.out.println("");

        System.out.println("odds j given i:"); // correct
        System.out.println(net.simple_conditional(i, j));
        System.out.println("");

        System.out.println("odds j given y or i:"); //correct
        System.out.println(net.two_known(y, i, j));
        System.out.println("");

        System.out.println("odds i given x or e:"); //correct
        System.out.println(net.two_known(x, e, i));
        System.out.println("");
    }
}