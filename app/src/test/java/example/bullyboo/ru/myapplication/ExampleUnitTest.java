package example.bullyboo.ru.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private int minValue = 70;
    private int maxValue = 100;
    private int value = 80;

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    private float degrees(float value){
        float oneDegree = 360 / (float) (maxValue - minValue);

        return (value - minValue) * oneDegree;
    }

    private int value(float degree){
        float oneDegree = 360 / (float) (maxValue - minValue);

        float tmp = 120 / oneDegree;

        int result;

        if(tmp % 1 >= 0.5f){
            result = (int) (tmp + 1);
        } else {
            result = (int) tmp;
        }

        value = minValue + result;

        return value;
    }

}