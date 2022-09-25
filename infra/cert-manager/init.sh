# configure kubernetes
#doctl kubernetes cluster kubeconfig save fbf58048-ab31-4051-9f59-4d2f54528ef2



helm repo add jetstack https://charts.jetstack.io
helm repo update
helm install certs jetstack/cert-manager --namespace cert-manager --values cert-manager-values.yml --create-namespace


# certs
kubectl apply -f ./k8s

# 3 zk nodes / 3 svcs
kubectl apply -f 0-zookeeper-deployment.yml

# 4 bk nodes
kubectl apply -f 1-kafka-deployment.yml

kubectl exec kafka-1 -- kafka-topics --bootstrap-server kafka-service-1:9092 --create --topic test-input --partitions 120 --replication-factor 2
kubectl exec kafka-1 -- kafka-topics --bootstrap-server kafka-service-1:9092 --create --topic test-output --partitions 120 --replication-factor 2

#30 producers producing messages on tls port
kubectl apply -f 2-proucer-deployment.yml

# 24 stream thread transactional streams
kubectl apply -f 3-application-deployment.yml










