package io.cdap.wrangler.executor;

import io.cdap.wrangler.TestingRig;
import io.cdap.wrangler.api.Row;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class AggregateStatsTest {
    @Test
    public void testAggregation() throws Exception {
        String[] recipe = {
                "aggregate-stats :size :time total_size total_time"
        };
        List<Row> rows = new ArrayList<>();
        rows.add(new Row("size", "10KB").add("time", "100ms"));
        rows.add(new Row("size", "1MB").add("time", "1s"));

        List<Row> results = TestingRig.execute(recipe, rows);
        Assert.assertEquals(1, results.size());
        double expectedSize = (10 * 1024 + 1 * 1024 * 1024) / (1024.0 * 1024); // MB
        double expectedTime = (100 * 1_000_000 + 1 * 1_000_000_000) / 1_000_000_000.0; // seconds
        Assert.assertEquals(expectedSize, results.get(0).getValue("total_size"), 0.001);
        Assert.assertEquals(expectedTime, results.get(0).getValue("total_time"), 0.001);
    }
}