package test_code;

import java.util.List;
import java.util.ArrayList;

public class Net {
    public List<Event> events = new ArrayList<Event>();
    public Event start_event;

    public void add_event(Event event){
        events.add(event);
        this.reset_events();
    }

    // Reset the point to point probability calculations
    public void reset_events(){
        for (Event event : this.events) {
            event.odds_to_event = event.generate_odds_to_event();
        }
    }


    // Computes the probability of s given k
    public float simple_conditional(Event k, Event s) {
        // Reset and (re)calculate the point-to-point probabilities
        // The resetting is not required in practice, but it does make testing easier
        this.reset_events();
        k.calculate_backwards();
        s.calculate_backwards();

        Event a = this.start_event;
        
        //p(k→s) + ((p(a→s)*p(s→k))/(p(a→k)
        float conditional_odds = k.odds_to(s) + (a.odds_to(s) * s.odds_to(k))/a.odds_to(k);
        return conditional_odds;        
    }

    // Computes the probability of s given either k1 or k2
    public float two_known(Event k1, Event k2, Event s) {
        // Reset and (re)calculate the point-to-point probabilities
        // The resetting is not required in practice, but it does make testing easier
        this.reset_events();
        k1.calculate_backwards();
        k2.calculate_backwards();
        s.calculate_backwards();

        Event a = this.start_event;

        float odds_above1 = a.odds_to(s) * (s.odds_to(k1) * (1 - k1.odds_to(k2) + s.odds_to(k2) * (1 - k2.odds_to(k1))));
        float odds_above2 = a.odds_to(k1) * (k1.odds_to(s) * (1 - s.odds_to(k2)) - k1.odds_to(k2) * k2.odds_to(s));
        float odds_above3 = a.odds_to(k2) * (k2.odds_to(s) * (1 - s.odds_to(k1)) - k2.odds_to(k1) * k1.odds_to(s));
        float odds_below = a.odds_to(k1)*(1-k1.odds_to(k2)) + a.odds_to(k2)*(1-k2.odds_to(k1));
        float conditional_odds = (odds_above1 + odds_above2 + odds_above3)/odds_below;
        return conditional_odds;
    }
}

