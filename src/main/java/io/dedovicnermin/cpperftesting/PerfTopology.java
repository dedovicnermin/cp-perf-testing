package io.dedovicnermin.cpperftesting;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;

import java.util.UUID;

public final class PerfTopology {
    private PerfTopology(){}

    public static Topology topology(final String input, final String output) {
        final StreamsBuilder builder = new StreamsBuilder();
        builder.stream(input, Consumed.with(Serdes.String(), Serdes.String()))
                .map((k, v) -> KeyValue.pair(UUID.randomUUID().toString(), v))
                .peek((k,v) -> System.out.println(k+":"+v))
                .to(output);
        return builder.build();
    }

}
