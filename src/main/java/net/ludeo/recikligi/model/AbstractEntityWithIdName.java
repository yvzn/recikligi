package net.ludeo.recikligi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
class AbstractEntityWithIdName extends AbstractEntityWithId {

    private String name;
}
