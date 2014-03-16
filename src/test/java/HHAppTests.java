import com.gopivotal.poc.hh.dao.NPayload;
import com.gopivotal.poc.hh.dao.Payload;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

/**
 * Created by cq on 2/26/14.
 */
public class HHAppTests {

    @Test
    public void testGeneration(){
        final int SIZE = 50000;
        Payload[] payloads = Payload.generatePayloads(SIZE);
        HashSet<String> hs = new HashSet<String>(SIZE);
        int i = 0;
        for (Payload p : payloads){
            if(hs.add((String) p.data()[1])) i++;
        }

        Assert.assertEquals(SIZE,i);

    }

    @Test
    public void testRandomStrings(){
        final int SIZE = 5000;
        NPayload[] payloads = NPayload.generatePayloads(SIZE);
        for(NPayload payload : payloads){
            Assert.assertNotNull(payload.data()[1].toString());

            Assert.assertEquals(50, payload.data()[1].toString().length());

            Assert.assertNotNull(payload.data()[4].toString());

            Assert.assertEquals(255, payload.data()[4].toString().length());


        }
    }
}
