package net.ludeo.recikligi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class UsageHistory extends AbstractEntityWithId {

    private LocalDate dateOfRequest;
}
