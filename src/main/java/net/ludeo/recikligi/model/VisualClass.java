package net.ludeo.recikligi.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
class VisualClass extends AbstractEntityWithIdName {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recyclable_status_id")
    private RecyclableStatus recyclableStatus;
}
