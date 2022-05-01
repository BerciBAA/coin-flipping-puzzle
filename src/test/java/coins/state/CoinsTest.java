package coins.state;

import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.apache.commons.math3.util.CombinatoricsUtils.binomialCoefficient;
import static org.junit.jupiter.api.Assertions.*;

class CoinsTest {

    private Coins state1 = new Coins(7, 3); // the original initial state

    private Coins state2; // the goal state
    {
        BitSet bs = new BitSet(7);
        bs.set(0, 7);
        state2 = new Coins(7, 3, bs);
    }


    @Test
    void testConstructor1_invalid() {
        assertThrows(IllegalArgumentException.class, () -> new Coins(0,0));
        assertThrows(IllegalArgumentException.class, () -> new Coins(1,0));
        assertThrows(IllegalArgumentException.class, () -> new Coins(0,1));
        assertThrows(IllegalArgumentException.class, () -> new Coins(1,2));
    }

    @Test
    void testConstructor2_invalid() {
        assertThrows(IllegalArgumentException.class, () -> new Coins(0,0, new BitSet(1)));
        assertThrows(IllegalArgumentException.class, () -> new Coins(1,0, new BitSet(1)));
        assertThrows(IllegalArgumentException.class, () -> new Coins(0,1, new BitSet(1)));
        assertThrows(IllegalArgumentException.class, () -> new Coins(1,2, new BitSet(1)));
        BitSet bitSet = new BitSet(3);
        bitSet.set(0,3);
        assertThrows(IllegalArgumentException.class, () -> new Coins(1,1, bitSet));
    }

    @Test
    void testIsGoal() {
        assertFalse(state1.isGoal());
        assertTrue(state2.isGoal());
    }

    @Test
    void testCanFlip() {
        BitSet bitSet1 = new BitSet(1);
        bitSet1.set(0,1);
        assertFalse(state1.canFlip(bitSet1));
        BitSet bitSet2 = new BitSet(5);
        bitSet2.set(0,5);
        assertFalse(state1.canFlip(bitSet2));
        BitSet bitSet3 = new BitSet(7);
        bitSet3.set(1,4);
        assertTrue(state1.canFlip(bitSet3));
        BitSet bitSet4 = new BitSet(8);
        bitSet4.set(5,8);
        assertFalse(state1.canFlip(bitSet4));
    }

    @Test
    void testFlip() {
        var copy = state1.clone();
        BitSet bitSet = new BitSet(7);
        bitSet.set(0,3);
        copy.flip(bitSet);
        assertTrue(bitSet.equals(copy.getCoins()));
        var copy1 = state1.clone();
        BitSet bitSet2 = new BitSet(7);
        bitSet2.set(4,7);
        copy1.flip(bitSet2);
        assertTrue(bitSet2.equals(copy1.getCoins()));
    }

    @Test
    void testGenerateFlips_invalid(){
        assertThrows(IllegalArgumentException.class, () -> Coins.generateFlips(0,0));
        assertThrows(IllegalArgumentException.class, () -> Coins.generateFlips(1,0));
        assertThrows(IllegalArgumentException.class, () -> Coins.generateFlips(0,1));
        assertThrows(IllegalArgumentException.class, () -> Coins.generateFlips(1,2));
    }

    @Test
    void testGenerateFlips(){
        assertEquals(binomialCoefficient(7,3),Coins.generateFlips(7,3).size());
        assertEquals(binomialCoefficient(10,2),Coins.generateFlips(10,2).size());
        assertNotEquals(binomialCoefficient(3,2),Coins.generateFlips(5,2).size());
    }

    @Test
    void testGetFlips(){
        assertEquals(binomialCoefficient(7,3),state1.getFlips().size());
        assertEquals(binomialCoefficient(7,3),state2.getFlips().size());
    }

    @Test
    void testEquals() {

        var clone = state2.clone();
        BitSet bitSet = new BitSet(7);
        bitSet.set(0,7);
        clone.flip(bitSet);
        assertFalse(clone.equals(state2));

        var clone1 = state2.clone();
        BitSet bitSet1 = new BitSet(7);
        bitSet1.set(4,7);
        clone.flip(bitSet1);

        assertFalse(clone.equals(state1));
        assertFalse(state1.equals(state2));
        assertFalse(state1.equals(null));
        assertFalse(state1.equals(3.14));
    }

    @Test
    void testHashCode() {
        assertTrue(state1.hashCode() == state1.clone().hashCode());
        assertTrue(state1.hashCode() == state1.hashCode());
    }

    @Test
    void testClone() {
        var clone = state1.clone();
        assertTrue(clone.equals(state1));
        assertNotSame(clone, state1);
    }

    @Test
    void testToString() {
        assertEquals("O|O|O|O|O|O|O", state1.toString());
        assertEquals("1|1|1|1|1|1|1", state2.toString());
    }

}