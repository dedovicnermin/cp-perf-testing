#!/usr/bin/env python

from confluent_kafka import Producer
import socket
import argparse


def main(args):
    conf = {
        "bootstrap.servers": args.bootstrap_servers,
        "client.id": socket.gethostname(),
        "security.protocol": "ssl",
        "ssl.ca.location": args.ca_cert_location,
        "ssl.certificate.location": args.client_cert_location,
        "ssl.key.location": args.client_key_location,
        "ssl.key.password": args.ssl_password
    }
    print(conf)
    producer = Producer(conf)
    i = 0
    while True:
        producer.produce(args.input_topic, "Transaction #{0} - sent by client #{1}".format(i, socket.gethostname()))
        i += 1
        producer.flush()


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description="Auditlog events test producer")
    parser.add_argument(
        '-b',
        dest="bootstrap_servers",
        required=False,
        default="kafka-service:9092",
        help="Bootstrap endpoint that producer will send events to"
    )
    parser.add_argument(
        '-ca',
        dest="ca_cert_location",
        required=True,
        help="Location of CA cert"
    )
    parser.add_argument(
        '-crt',
        dest="client_cert_location",
        required=True,
        help="Client tls cert location"
    )
    parser.add_argument(
        '-key',
        dest="client_key_location",
        required=True,
        help="Client tls key location"
    )
    parser.add_argument(
        '-pass',
        dest="ssl_password",
        required=True,
        default="secret",
        help="The password for the key AND truststore (ie. a requirement)"
    )
    parser.add_argument(
        '-in',
        dest="input_topic",
        required=True,
        default="test-input",
        help="Topic name for the producer to send msgs to"
    )
    main(parser.parse_args())
