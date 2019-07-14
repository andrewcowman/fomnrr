package com.cowman.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cowman.fomnrr.R;
import com.cowman.models.Event;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsView> {
    private ArrayList<Event> events;
    private LayoutInflater layoutInflater;

    public static class EventsView extends RecyclerView.ViewHolder {
        public TextView eventHeader;
        public TextView eventDesc;
        public TextView eventDate;
        public TextView eventStartLocation;
        public TextView eventEndLocation;
        public TextView eventRoute;

        public EventsView(@NonNull View view) {
            super(view);
            eventHeader = view.findViewById(R.id.event_header);
            eventDesc = view.findViewById(R.id.event_description);
            eventDate = view.findViewById(R.id.event_date);
            eventRoute = view.findViewById(R.id.event_route);
        }
    }

    public EventsAdapter(Context context, ArrayList<Event> events) {
        this.events = events;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public EventsView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.view_event, viewGroup, false);
        return new EventsView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsView eventsView, int i) {
        eventsView.eventHeader.setText(events.get(i).getHeader());
        eventsView.eventDesc.setText(events.get(i).getDescription());
        eventsView.eventDate.setText(events.get(i).getDate().toString("MM/dd/yyyy HH:mm"));
        eventsView.eventRoute.setText(events.get(i).getStartLocation() + " to " + events.get(i).getEndLocation());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
