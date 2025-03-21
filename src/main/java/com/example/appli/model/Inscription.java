package com.example.appli.model;

public class Inscription {
    private Participant participant;
    private Event event;

    public Inscription(Participant participant, Event event) {
        this.participant = participant;
        this.event = event;
    }

    // Getters
    public Participant getParticipant() {
        return participant;
    }

    public Event getEvent() {
        return event;
    }
}