FROM python:3.9

WORKDIR /app

ENV BOOTSTRAP_SERVERS=${BOOTSTRAP_SERVERS}

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY perf-producer.py .

ENTRYPOINT ./perf-producer.py -b "$BOOTSTRAP_SERVERS" -ca "$CA_CRT_LOCATION" -crt "$CLIENT_CRT_LOCATION" -key "$CLIENT_KEY_LOCATION" -pass "$SSL_PASSWORD" -in "$INPUT_TOPIC"
