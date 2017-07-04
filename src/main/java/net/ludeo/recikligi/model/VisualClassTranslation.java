package net.ludeo.recikligi.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
public class VisualClassTranslation {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    private String text;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="visual_class_id")
    private VisualClass visualClass;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="translation_locale_id")
    private TranslationLocale translationLocale;
}
