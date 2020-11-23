package com.maciej.checkflix.domain.omdb;

import lombok.Getter;

@Getter
public enum Type {
    MOVIE("movie"), SERIES("series"), EPISODE("episode"), GAME("game");

    public final String label;

    private Type(String label) {
        this.label = label;
    }

    public static Type from(String text) {
        for (Type type: Type.values()) {
            if (type.label.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }
}
