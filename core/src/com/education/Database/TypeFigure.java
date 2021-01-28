package com.education.Database;

@SuppressWarnings("unused")
public enum TypeFigure {
    EPANALEPSE("épanalepse"),
    EPANADIPLOSE("épanadiplose"),
    PARALLELISME("parallélisme"),
    ANAPHORE("anaphore"),
    GRADATION("gradation"),
    ACCUMULATION("accumulation"),
    HYPERBOLE("hyperbole"),
    ANTONOMASE("antonomase"),
    HYPALLAGE("hypallage"),
    SYMBOLE("symbole"),
    PERIPHRASE("périphrase"),
    SYNECDOQUE("synecdoque"),
    METONYMIE("métonymie"),
    IMGLITERRAIRE("image littéraire"),
    ALLEGORIE("allégorie"),
    PERSONIFICATION("personification"),
    METAPHORE("métaphore"),
    COMPARAISON("comparaison");

    private String value;

    TypeFigure(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
