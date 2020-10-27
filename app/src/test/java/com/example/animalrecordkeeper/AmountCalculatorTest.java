package com.example.animalrecordkeeper;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AmountCalculatorTest {

    //Test weight between 0 - 40 (should return 5% of weight)
    @Test
    public void calculate_Weight_Under_40() {assertEquals(1.0, FeedingActivity.calculateAmount(20), 0);}

    //Test weight == 40 (should return 5% of weight)
    @Test
    public void calculate_Weight_Equals_40() {assertEquals(2.0, FeedingActivity.calculateAmount(40), 0);}

    //Test weight between 40 - 90 (should return 6% of weight)
    @Test
    public void calculate_Weight_Under_90() {assertEquals(3.6, FeedingActivity.calculateAmount(60), 0.1);}

    //Test weight == 90 (should return 6% of weight)
    @Test
    public void calculate_Weight_Equals_90() {assertEquals(5.4, FeedingActivity.calculateAmount(90), 0.1);}

    //Test weight between 90 - 150 (should return 7% of weight)
    @Test
    public void calculate_Weight_Under_150() {assertEquals(7.7, FeedingActivity.calculateAmount(110), 0.1);}

    //Test weight == 150 (should return 7% of weight)
    @Test
    public void calculate_Weight_Equals_150() {assertEquals(10.5, FeedingActivity.calculateAmount(150), 0.1);}

    //Test weight above 150 (should return -1, meaning to free feed animal)
    @Test
    public void calculate_Weight_Above_150() {assertEquals(-1, FeedingActivity.calculateAmount(200), 0);}
}