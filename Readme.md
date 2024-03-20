#Instalação do docker

```plaintext
curl -fsSL https://get.docker.com -o get-docker.sh
sh get-docker.sh
```

#Instalação do Kind

```plaintext
curl -Lo ./kind https://kind.sigs.k8s.io/dl/v0.22.0/kind-linux-amd64
chmod +x ./kind
sudo mv ./kind /usr/local/bin/kind
```

#Instalação do Kind

```plaintext
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin/kubectl
```

#Criar o cluster no Kind
```plaintext
kind create cluster --name kind-k8s --config kind.yml 
```

#Comandos de teste
```plaintext
kind get clusters
kind delete cluster --name kind-k8s

kubectl config get-contexts
kubectl get nodes
kubectl get pods

```

#Aplicar os manifestos para implantar o min.io

```plaintext
kubectl apply -f pv.yml
kubectl get PersistentVolume 

kubectl apply -f pvc.yml 
kubectl get PersistentVolumeClaim 

kubectl apply -f deploy.yml 
kubectl get pods
kubectl logs minio-6d9cd468cb-5279f
```

#Expor a porta de acesso a API
- Verificar no log `kubectl logs minio-6d9cd468cb-5279f` qual porta está expondo a UI.
- Editar o manifesto svc.yml e alterar o número da porta de minio-port-randon de acordo com o retornado no log: WebUI: http://10.244.1.2:43069 http://127.0.0.1:43069
- Expor usando port-forward as portas para fora do ambiente kubernetes:

```plaintext
kubectl apply -f svc.yml 
kubectl port-forward svc/minio --address 0.0.0.0 9000:9000 & \
kubectl port-forward svc/minio --address 0.0.0.0 38579:38579
```