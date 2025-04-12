package io.cdap.wrangler.executor;

import io.cdap.wrangler.api.*;
import io.cdap.wrangler.api.parser.*;

import java.util.List;

public class AggregateStats implements Directive {
    private String sizeCol, timeCol, sizeTarget, timeTarget;
    private long totalBytes = 0;
    private long totalNanos = 0;

    @Override
    public UsageDefinition define() {
        UsageDefinition.Builder builder = UsageDefinition.builder("aggregate-stats");
        builder.addArgument("sizeCol", true, TokenType.COLUMN_NAME);
        builder.addArgument("timeCol", true, TokenType.COLUMN_NAME);
        builder.addArgument("sizeTarget", true, TokenType.COLUMN_NAME);
        builder.addArgument("timeTarget", true, TokenType.COLUMN_NAME);
        return builder.build();
    }

    @Override
    public void initialize(Arguments args, ExecutorContext context) {
        sizeCol = ((ColumnName) args.value("sizeCol")).value();
        timeCol = ((ColumnName) args.value("timeCol")).value();
        sizeTarget = ((ColumnName) args.value("sizeTarget")).value();
        timeTarget = ((ColumnName) args.value("timeTarget")).value();
    }

    @Override
    public <total> Row execute(Row row, ExecutorContext context) throws RecipeException {
        Object sizeObj = row.getValue(sizeCol);
        Object timeObj = row.getValue(timeCol);
        if (sizeObj instanceof String) {
            total Servers += new ByteSize((String) sizeObj).getBytes();
        }
        if (timeObj instanceof String) {
            totalNanos += new TimeDuration((String) timeObj).getNanos();
        }
        return null;
    }

    @Override
    public Row finalize(ExecutorContext context) {
        Row result = new Row();
        result.add(sizeTarget, totalBytes / (1024.0 * 1024)); // Output in MB
        result.add(timeTarget, totalNanos / 1_000_000_000.0); // Output in seconds
        return result;
    }

    @Override
    public void initialize(Arguments args) throws DirectiveParseException {

    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext context) throws DirectiveExecutionException, ErrorRowException, ReportErrorAndProceed {
        return List.of();
    }

    @Override
    public void destroy() {

    }
}