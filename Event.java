package test_code;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

public class Event {
    String name;

    // How this event relates to the preceding events
    public Map<Event, Float> odds_previous_events = new HashMap<Event, Float>();

    // How the odds are from this event to future events
    public Map<Event, Float> odds_to_event = generate_odds_to_event();
    public Map<Event, Float> generate_odds_to_event() {
        Map<Event, Float> odds_to_event = new HashMap<Event, Float>();
        odds_to_event.put(this, 1f);
        return odds_to_event;
    }

    // Gives either odds from this event to the given event
    // 0 if the given event does not show up
    public float odds_to(Event event) {
        return this.odds_to_event.getOrDefault(event, 0f);
    }

    // Calculates the odds of ending up at the current event from all events that ly before it
    public void calculate_backwards() {
        LinkedList<Event[]> queue = new LinkedList<>();
        LinkedList<Event> events_added_to_queue = new LinkedList<>();
        

        for (Event event : this.odds_previous_events.keySet()) {
            queue.add(new Event[]{this, event});
        }
        events_added_to_queue.add(this);

        while (queue.size() != 0) {
            Event[] current_entry = queue.remove(0);
            Event subsequent_event = current_entry[0];
            Event current_event = current_entry[1];

            float value1 = subsequent_event.odds_previous_events.get(current_event);
            float value2 = subsequent_event.odds_to_event.get(this);

            float additional_odds_current_event = value1*value2;


            if (current_event.odds_to_event.containsKey(this)) {
                float old_odds = current_event.odds_to_event.get(this);
                current_event.odds_to_event.put(this, additional_odds_current_event + old_odds);
            }

            else {
                current_event.odds_to_event.put(this, additional_odds_current_event);
            }
            
            if (!events_added_to_queue.contains(current_event)) {
                for (Event event : current_event.odds_previous_events.keySet()) {
                    queue.add(new Event[]{current_event, event});
                }
                events_added_to_queue.add(current_event);
            }
        }
    }

    public String toString() {
        return this.name;
    }
}


