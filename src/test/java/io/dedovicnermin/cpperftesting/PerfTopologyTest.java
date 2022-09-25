package io.dedovicnermin.cpperftesting;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.test.TestRecord;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PerfTopologyTest {
    static final Properties properties = new Properties();
    static final String TEST_PROPS_FILE = "test.properties";
    static final String INPUT_TOPIC = "test-input";
    static final String OUTPUT_TOPIC = "test-output";

    final Serde<String> string = Serdes.String();
    TopologyTestDriver testDriver;
    TestInputTopic<String, String> inputTopic;
    TestOutputTopic<String, String> outputTopic;

    @BeforeAll
    static void loadProperties() {
        try (final InputStream inputStream = PerfTopologyTest.class.getClassLoader().getResourceAsStream(TEST_PROPS_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setup() {
        testDriver = new TopologyTestDriver(PerfTopology.topology(INPUT_TOPIC, OUTPUT_TOPIC), properties);
        inputTopic = testDriver.createInputTopic(INPUT_TOPIC, string.serializer(), string.serializer());
        outputTopic = testDriver.createOutputTopic(OUTPUT_TOPIC, string.deserializer(), string.deserializer());
    }

    @AfterEach
    void cleadup() {testDriver.close();}

    @Test
    void test() {
        inputTopic.pipeValueList(List.of("MSG_1", "MSG_2", "MSG_3"));
        final List<TestRecord<String, String>> testRecords = outputTopic.readRecordsToList();
        assertEquals(3, testRecords.size());
        System.out.println(testRecords);

    }

}