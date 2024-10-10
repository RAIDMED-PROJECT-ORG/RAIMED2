package fr.imt.raimed2.diagnostic.dto.response;

import fr.imt.raimed2.action.model.Action;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class EventDto {

    private UUID id;

    private Date date;

    private Action action;

}
