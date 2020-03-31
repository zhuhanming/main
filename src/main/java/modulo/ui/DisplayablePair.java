package modulo.ui;

import modulo.model.Displayable;

/**
 * A pair class to contain displayables and is a displayable itself.
 *
 * @param <S> Type of displayable element.
 * @param <T> Type of associated element.
 */
public class DisplayablePair<S extends Displayable, T> implements Displayable {
    private S first;
    private T second;

    public DisplayablePair(S first, T second) {
        this.first = first;
        this.second = second;
    }

    public S getFirst() {
        return this.first;
    }

    public T getSecond() {
        return this.second;
    }

    @Override
    public String findCommandString() {
        return null;
    }
}
