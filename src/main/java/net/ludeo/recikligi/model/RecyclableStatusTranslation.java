package net.ludeo.recikligi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class RecyclableStatusTranslation extends AbstractEntityWithId {

    private String text;

    private String description;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="recyclable_status_id")
    private RecyclableStatus recyclableStatus;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="translation_locale_id")
    private TranslationLocale translationLocale;
}
