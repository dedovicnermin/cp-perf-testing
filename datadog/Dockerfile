FROM datadog/agent:7.27.0-jmx
# Transfer the JMX integration configuration file to jmx.d
COPY conf.d/jmx.yaml /etc/datadog-agent/conf.d/jmx.d/conf.yaml
COPY conf.d/metrics.yaml /etc/datadog-agent/conf.d/jmx.d/metrics.yaml

ENV DD_API_KEY=${DD_API_KEY}
ENV DD_LOGS_ENABLED=false
ENV DD_TAGS='service:perf-test-agent'
ENV DD_HOSTNAME=datadog
ENV DD_CONTAINER_EXCLUDE="name:datadog"
ENV DD_JMXFETCH_KAFKA_ENABLED="true"
