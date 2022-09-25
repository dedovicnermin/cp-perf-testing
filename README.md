### ENV

- K8 cluster with 8-10 nodes
- 2 vCPU 4GB each
- run on digital ocean


1. A zookeeper (3x) / kafka (4x) (deployments)
    1. kafka listening on port 9092 for plaintext / 9093 for mtls
2. Producers (deployment)
    1. producers continously sending string serialized messages to the secured port
    2. Depending on the # of requests we can get per second, scale to get 120K/s
    3. ** might have to reconsider language to get a better understanding of how many messages are being sent per client. Simple task. 
3. A streaming application (POD)
    1. Consuming on the secure port -> simple map (give record a unique UUID for key) -> peek (print message) -> producing on the secure port
    2. transactions v2 activated
    3. 24 stream threads
    4. side car with datadog with APM metrics getting tracked to view the amount of time function calls will be taking and track stream-related metrics
    5. topic:
        1. has 120 partitions
        2. replication factor = 2 
