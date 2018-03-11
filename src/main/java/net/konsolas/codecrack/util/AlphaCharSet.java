package net.konsolas.codecrack.util;

/**
 * High-performance set of characters between 'A' and 'Z'
 */
public class AlphaCharSet {
    private int bitfield;

    /**
     * Construct a new {@link AlphaCharSet} with no contents
     */
    public AlphaCharSet() {
        bitfield = 0;
    }

    /**
     * Add a character to the set
     *
     * @param ch character to add
     */
    public void add(char ch) {
        bitfield |= 1 << (calcShift(ch));
    }

    /**
     * Check if the set contains a character
     *
     * @param ch character to check
     */
    public boolean contains(char ch) {
        return (bitfield & (1 << calcShift(ch))) != 0;
    }

    /**
     * Remove a character from the set
     *
     * @param ch character to remove
     */
    public void remove(char ch) {
        bitfield &= ~(1 << calcShift(ch));
    }

    /**
     * Get the size of this set
     *
     * @return size
     */
    public int size() {
        return Integer.bitCount(bitfield);
    }

    private int calcShift(char ch) {
        assert ch >= 'A' && ch <= 'Z';
        return ch - 'A';
    }
}
