package com.app.emsx.mappers;

import com.app.emsx.dtos.EventDTO;
import com.app.emsx.entities.Event;

public class EventMapper {

    public static EventDTO toDTO(Event e) {
        return new EventDTO(
                e.getId(),
                e.getName(),
                e.getDescription(),
                e.getStartDate(),
                e.getEndDate(),
                e.getLocation()
        );
    }

    public static Event toEntity(EventDTO dto) {
        Event e = new Event();
        e.setName(dto.getName());
        e.setDescription(dto.getDescription());
        e.setStartDate(dto.getStartDate());
        e.setEndDate(dto.getEndDate());
        e.setLocation(dto.getLocation());
        return e;
    }

    public static void updateEntity(Event e, EventDTO dto) {
        e.setName(dto.getName());
        e.setDescription(dto.getDescription());
        e.setStartDate(dto.getStartDate());
        e.setEndDate(dto.getEndDate());
        e.setLocation(dto.getLocation());
    }
}
