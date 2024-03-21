
---

# Trabalho em Grupo - Arquitetura de Microsserviços e Containers @IFMT/Pós-graduação-Eng.DevOps/2024-03

- **Instituição de Ensino**: [Instituto Federal Mato Grosso](https://ifmt.edu.br/)
- **Campus**: [Cuiabá - Cel. Octayde Jorge da Silva](https://cba.ifmt.edu.br/inicio/)
- **Curso**: [Pós Graduação Lato Sensu em Engenharia DEVOPS](https://cba.ifmt.edu.br/conteudo/noticia/profissionais-de-ti-participam-de-aula-inaugural-da-pos-em-engenharia-devops/)
- **Disciplina**: Arquitetura de Microsserviços e Containers
- **Professor**: [Joao Paulo Delgado Preti](https://github.com/jppreti)
- **Integrantes**:
  - Marcelo Barcelar Ricardo
  - Marcos Roberto de Avila
  - [Néftis Caroline Araujo da Silva](https://github.com/neftissilva)
  - [Wellington de Carvalho Batista](https://github.com/w3ll1ngt0n)

- **Objetivo Trabalho**: Apresentar um Protótipo que contenha containner, podendo incorparar ferramentas exemplificadas no site [Cloud Native Landscap](https://landscape.cncf.io/), utilizando o Projeto apresentado na disciplina ou cenário funcional. Dessa maneira optamos por utilizar as seguintes ferramentas:
<div align="center">

| MinIO | Kubernetes| Docker | Kind | Quarkus |
|:------:|:-----:|:-----:|:-----:|:-----:|
[<img width="120" height="100" alt="MinIO" src="https://landscape.cncf.io/logos/da1f6718a1f3fb1815a7121a6c4a415ce2aa676812a781d0554ef452fbd83ba4.svg">](https://landscape.cncf.io/?item=runtime--cloud-native-storage--minio) | [<img width="120" height="100" alt="kubernetes" src="https://landscape.cncf.io/logos/e0303fdc381c96c1b4461ad1a2437c8f050cfb856fcb8710c9104367ca60f316.svg">](https://landscape.cncf.io/?item=orchestration-management--scheduling-orchestration--kubernetes) | [<img width="120" height="100" alt="docker" src="https://landscape.cncf.io/logos/ed44d795485baf780b56be5d2e56e72a057381f6bde73c0dea402e70f48e4cba.svg">](https://landscape.cncf.io/?item=app-definition-and-development--application-definition-image-build--docker-compose) | [<img width="120" height="100" alt="kind" src="https://landscape.cncf.io/logos/09c21e3114167cadc5a7eb40895d3262c1d2c369ba858b973b5e4455c04888b3.svg">](https://landscape.cncf.io/?item=platform--certified-kubernetes-installer--kind) | [<img width="120" height="100" alt="quarkus" src="https://landscape.cncf.io/logos/33d16185b53fd52b4eb7dfa2377ef9297bceb308356afb879f05790e24c7a4a0.svg">](https://landscape.cncf.io/?item=app-definition-and-development--application-definition-image-build--quarkus) |
|  |  |  |  |  |

</div>

<br> 

- **Objetivo Específico**: Demonstrar o uso da ferramenta [MinIo](https://min.io/) como uma solução de armazenamento de objetos associada a plataforma [Kubernetes](https://kubernetes.io/), com a otimização e verificação e testes pela ferramenta [Quarkus](https://quarkus.io/).

- Os exemplos e passos demonstrados a seguir foram utilizados em um ambiente [Linux Mint](https://linuxmint.com/).

## 1. Kubernetes

O [**Kubernetes**](https://kubernetes.io/) é uma engine de orquestração de contêineres Open Source utilizado para automatizar a implantação, dimensionamento e gerenciamento de aplicativos em contêiner.

### 1.1 Kubectl

O [**kubectl**](https://kubernetes.io/pt-br/docs/tasks/tools/#kubectl) é a ferramenta de linha de comando do Kubernetes, permite que execute comandos nos clusters Kubernetes. Pode usar o kubectl para instalar aplicações, inspecionar e gerenciar recursos de cluster e visualizar os logs.

#### - [Instalação e configuração do kubectl no Linux](https://kubernetes.io/pt-br/docs/tasks/tools/install-kubectl-linux/)

Utilizaremos para instalação o [gerenciador de pacotes nativo do Kubectl](https://kubernetes.io/pt-br/docs/tasks/tools/install-kubectl-linux/#instale-usando-o-gerenciador-de-pacotes-nativo) com as informações [atualizadas dos respositórios](https://kubernetes.io/blog/2023/08/15/pkgs-k8s-io-introduction/)

Atualize o índice do apt e instale os pacotes necessários para utilizar o repositório apt do Kubernetes:

```
sudo apt update
```
```
sudo apt install -y ca-certificates curl
```

Faça download da chave de assinatura pública para os repositórios de pacotes do Kubernetes. A mesma chave de assinatura é usada para todos os repositórios, então pode ignorar a versão no URL.

```
curl -fsSL https://pkgs.k8s.io/core:/stable:/v1.28/deb/Release.key | sudo gpg --dearmor -o /etc/apt/keyrings/kubernetes-apt-keyring.gpg
```

Adicione o repositório apt do Kubernetes:

```
echo "deb [signed-by=/etc/apt/keyrings/kubernetes-apt-keyring.gpg] https://pkgs.k8s.io/core:/stable:/v1.28/deb/ /" | sudo tee /etc/apt/sources.list.d/kubernetes.list
```

Atualize o índice do apt com o novo repositório e instale o kubectl:

```
sudo apt-get update
```
```
sudo apt-get install -y kubectl
```

Teste para garantir que a versão instalada esteja atualizada:

```
kubectl version --client
```

ou use o códico seguinte para visualizar mais detalhes da versão:

```
kubectl version --client --output=yaml
```

### 1.2 Docker

Para utilizar o Kubernetes localmente precisaremos criar cluster Kubernetes, para criar um ambiente de gerenciamento de contêiner utilizaremos o Docker.

O [**Docker**](https://www.docker.com/) pode ser instalado seguindo as instruções disponíveis no site oficial ou usando o gerenciador de pacotes do seu sistema, sendo essa forma que será demonstrada.

 #### - [Instalação utilizando o repositório apt](https://docs.docker.com/engine/install/ubuntu/#install-using-the-repository)

Atualize o índice do apt e adicione a chave GPG Oficial Docker:
```
sudo apt-get update
```
```
sudo apt-get install ca-certificates curl
```
```
sudo install -m 0755 -d /etc/apt/keyrings
```
```
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
```
```
sudo chmod a+r /etc/apt/keyrings/docker.asc
```

Adicione o repositório às fontes do Apt:
```
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$UBUNTU_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
```


Atualize o repositório:
```
sudo apt-get update
```

Para instalar a versão mais recente do Docker, execute:
```
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

Verifique se a instalação do Docker Engine foi bem-sucedida executando a imagem `hello-world`:
```
sudo docker run hello-world
```

Este comando baixa uma imagem de teste e a executa em um contêiner. Quando o contêiner é executado, ele imprime uma mensagem de confirmação e sai.

Isso confirma que foi instalado e iniciado com sucesso o Docker Engine.


#### - [Gerencie o Docker como um usuário não root](https://docs.docker.com/engine/install/linux-postinstall/#manage-docker-as-a-non-root-user):
```
sudo groupadd docker
```
```
sudo usermod -aG docker $USER
```
** Reinicie o terminal ou o Sistema Operacional se necessário.

Verifique se pode executar comandos do Docker sem precisar usar sudo:
```
docker run hello-world
```

#### - [Configurar o Docker para iniciar na inicialização com systemd](https://docs.docker.com/engine/install/linux-postinstall/#configure-docker-to-start-on-boot-with-systemd)

Muitas distribuições Linux modernas usam o systemd para gerenciar quais serviços iniciam quando o sistema é inicializado. No Debian e no Ubuntu, o serviço Docker inicia na inicialização por padrão. Para iniciar automaticamente o Docker e o containerd na inicialização para outras distribuições Linux que usam o systemd, execute os seguintes comandos:
```
sudo systemctl enable docker.service
```
```
sudo systemctl enable containerd.service
```

Para interromper esse comportamento, use _"disable"_ em vez disso.
```
sudo systemctl disable docker.service
```
```
sudo systemctl disable containerd.service
```

### 1.2 Kind

[**Kind**](https://kind.sigs.k8s.io/) é uma ferramenta para executar clusters Kubernetes locais usando contêineres Docker  em nós _(nodes)_. Kind foi projetado principalmente para testar o próprio Kubernetes, mas pode ser usado para desenvolvimento local ou integração contínua (CI).

#### - [Instalando a partir dos binários de lançamento pelo Linux](https://kind.sigs.k8s.io/docs/user/quick-start#installing-from-release-binaries)

```
curl -Lo ./kind https://kind.sigs.k8s.io/dl/v0.22.0/kind-linux-amd64
```
```
chmod +x ./kind
```
```
sudo mv ./kind /usr/local/bin/kind
```

Para verificar se o Kind foi instalado, execute o seguinte comando, no qual aparecerá a versão instalada:
```
kind version
```

#### - [Criar e Configurar cluster Kind](https://kind.sigs.k8s.io/docs/user/configuration/#getting-started)

Para criar e configurar o cluster Kind do projeto será necessário que na pasta do Projeto tenha o arquivo [kind.yml](https://github.com/neftissilva/ifmt_minio/blob/eadb49dfaad18b6b5d2a5c502218c3af497d640b/projeto_ifmt/kind.yml) com as respectivas configurações:

No diretório da pasta do projeto, onde se encontra o arquivo [kind.yml](https://github.com/neftissilva/ifmt_minio/blob/eadb49dfaad18b6b5d2a5c502218c3af497d640b/projeto_ifmt/kind.yml) execute o seguinte comando para criar e configurar o cluster kind:

```
kind create cluster --name kind-k8s --config kind.yml 
```

Para realizar testes execute os comandos abaixo e análise os resultados:
```
kind get clusters
```
```
kubectl config get-contexts
```
```
kubectl get nodes
```

Caso seja necessário apagar o cluster criado o comando será o seguinte:
```
kind delete cluster --name kind-k8s
```

### 1.3 Krew

[Krew](https://krew.sigs.k8s.io/) é um gerenciador de plugins para a ferramenta de linha de comando kubectl. Sua instalação é necessária para o passo seguinte que será a instalação do MinIO Kubernetes Plugin.

#### - [Instalação do GIT](https://git-scm.com/download/linux)

Para utilizar o Krew será necessário que tenha instalado o GIT. Caso não tenha seguir os próximos comandos:

Atualizar o repositório e dar o comando para instalar o Git:
```
sudo apt update
```
```
sudo apt install git -y
```

Para verificar a versão instalada utilize o comando:
```
git –version
```

#### - [Instalação do Krew](https://krew.sigs.k8s.io/docs/user-guide/setup/install/)

Execute este comando para baixar e instalar o krew:
```
(
  set -x; cd "$(mktemp -d)" &&
  OS="$(uname | tr '[:upper:]' '[:lower:]')" &&
  ARCH="$(uname -m | sed -e 's/x86_64/amd64/' -e 's/\(arm\)\(64\)\?.*/\1\2/' -e 's/aarch64$/arm64/')" &&
  KREW="krew-${OS}_${ARCH}" &&
  curl -fsSLO "https://github.com/kubernetes-sigs/krew/releases/latest/download/${KREW}.tar.gz" &&
  tar zxvf "${KREW}.tar.gz" &&
  ./"${KREW}" install krew
)
```

Adicione o diretório $HOME/.krew/bin ao seu PATH variável de ambiente. Para fazer isso, atualize o seu arquivo .bashrc e adicione a seguinte linha ao final do arquivo:

Abra um terminal e execute o seguinte comando para editar o arquivo .bashrc usando o editor de texto padrão (geralmente o nano):
```
nano ~/.bashrc
```

Vá para o final do arquivo usando as teclas de seta. E adicione a linha a seguir ao final do arquivo:
```
export PATH="${KREW_ROOT:-$HOME/.krew}/bin:$PATH"
```
\*Depois de salvar o arquivo, feche e reabra o terminal para que as alterações tenham efeito.


Execute o comando a seguir para verificar a instalação.
```
kubectl krew version
```


## 2. MinIO

[MinIO](https://min.io/) é um servidor de armazenamento de objetos distribuídos, definido por software e executado em qualquer nuvem ou infraestrutura local. Servidor adequado para armazenar dados não estruturados, como fotos, vídeos, arquivos de log, backups e contêineres.


### 2.1 MinIO Object Storage para Kubernetes

#### - [**Instalação do MinIO Kubernetes Plugin**](https://min.io/docs/minio/kubernetes/upstream/reference/kubectl-minio-plugin.html)

O Plugin Kubernetes do MinIO fornece um comando para inicializar o MinIO Operator.

Você pode instalar o plugin MinIO usando o gerenciador de plugins Kubernetes Krew:

```
kubectl krew update
```
```
kubectl krew install minio
```

Para verificar a versão:
```
kubectl minio version
```


#### - [**Manifestos YAML para implantar o MinIO no Kubernetes**]()


Para implantar o MinIO no Kubernetes precisamos de alguns manifestos YAML que definem alguns recursos necessários, como [PersistentVolume](https://github.com/neftissilva/ifmt_minio/blob/eadb49dfaad18b6b5d2a5c502218c3af497d640b/projeto_ifmt/pv.yml), [PersistentVolumeClaim](https://github.com/neftissilva/ifmt_minio/blob/eadb49dfaad18b6b5d2a5c502218c3af497d640b/projeto_ifmt/pvc.yml), [Deployment](https://github.com/neftissilva/ifmt_minio/blob/eadb49dfaad18b6b5d2a5c502218c3af497d640b/projeto_ifmt/deploy.yml) e [Service](https://github.com/neftissilva/ifmt_minio/blob/eadb49dfaad18b6b5d2a5c502218c3af497d640b/projeto_ifmt/svc.yml). Esses manifestos se encontram disponível no respositório do Projeto, no caso salve-os na pasta do seu [Projeto](https://github.com/neftissilva/ifmt_minio/tree/eadb49dfaad18b6b5d2a5c502218c3af497d640b/projeto_ifmt). E realize os próximos comandos e alterações necessárias nos arquivos.

Com o  cluster Kind criado e realizado os testes apresentados anteriormente, realize os seguintes comandos:

Criar e setar/pegar o [PersistentVolume](https://github.com/neftissilva/ifmt_minio/blob/eadb49dfaad18b6b5d2a5c502218c3af497d640b/projeto_ifmt/pv.yml):
```
kubectl apply -f pv.yml
```
```
kubectl get PersistentVolume
```

Criar e setar/pegar o [PersistentVolumeClaim](https://github.com/neftissilva/ifmt_minio/blob/eadb49dfaad18b6b5d2a5c502218c3af497d640b/projeto_ifmt/pvc.yml):

```
kubectl apply -f pvc.yml
```
```
kubectl get PersistentVolumeClaim 
```


Criar o [Deployment](https://github.com/neftissilva/ifmt_minio/blob/eadb49dfaad18b6b5d2a5c502218c3af497d640b/projeto_ifmt/deploy.yml) e setar o `pods`:
```
kubectl apply -f deploy.yml
```
```
kubectl get pods
```

O resultado do último comando será necessário análisar:<br>![Resultado-kubectl get pods](https://github.com/neftissilva/ifmt_minio/blob/8a71269e59a600ae83af37a03c21bb727f8d2c39/diversos/image-1.png)<br>

**NAME**: Nesse campo após escrito `minio-` a identificação a seguir necessária para consultar o log, no qual deverá ser informado todo no nome do container, ex: `minio-75b47687fc-xkxst` <br>
**STATUS**: `ContainerCreating` o pod do MinIO está em processo de criação e ainda não está pronto para uso. No entanto, pode acessar os logs do pod usando `kubectl logs` para verificar o que está acontecendo. Exemplo de consultar o log nesse caso: `kubectl logs minio-75b47687fc-xkxst`. Quando o STATUS mudar para `Running` signfica que foi criado o containner e está funcionando. <br>
**READY**: `0/1` indica que o conteiner ainda não está pronto. Indicará quando pronto quando estiver `1/1`.

--

Verificar no log a porta de acesso da API em WebUI. Exemplo da verificação do log no container citado anteriormente:

```
kubectl logs minio-75b47687fc-xkxst
```
Resultado:<br>![Resultado-Log](https://github.com/neftissilva/ifmt_minio/blob/8a71269e59a600ae83af37a03c21bb727f8d2c39/diversos/image-2.png)

Com a identificação das portas de acordo com a informação do log, acesse o arquivo [svc.yml (Manifesto Service)](https://github.com/neftissilva/ifmt_minio/blob/eadb49dfaad18b6b5d2a5c502218c3af497d640b/projeto_ifmt/svc.yml) e altere a `port` e `targetPort` em `minio-port-randon`.

```
[...]
  - protocol: TCP
      port: 41129
      targetPort: 41129
    name: minio-port-randon
```

Salve o arquivo. E pelo terminal aplique as modificações com o comando:
```
kubectl apply -f svc.yml
```

E agora deverá exportar as portas para fora do ambiente Kubernetes usando o comando `port-forward`:
 
```
kubectl port-forward svc/minio --address 0.0.0.0 9000:9000 & kubectl port-forward svc/minio --address 0.0.0.0 41129:41129
```

Após o container estará rodando no terminal e será possível acessar pelo navegador. No caso acessamos a seguinte url disponibilizada ao executar o comando `kubectl logs minio-75b47687fc-xkxst`: http://127.0.0.1:41129/login

Usuário e senha estão definidos no arquivo [deploy.yml (Manifesto Deployment)](https://github.com/neftissilva/ifmt_minio/blob/eadb49dfaad18b6b5d2a5c502218c3af497d640b/projeto_ifmt/deploy.yml)

```
[...]
        env:
        - name: MINIO_ROOT_USER
          value: "admin"
        - name: MINIO_ROOT_PASSWORD
          value: "12345678"
```


## 3. Quarkus

[Quarkus](https://pt.quarkus.io/) é um framework Java nativo do Kubernetes de stack completo que foi desenvolvido para máquinas virtuais Java (JVMs) e compilação nativa. Ele otimiza essa linguagem especificamente para containers, fazendo com que essa tecnologia seja uma plataforma eficaz para ambientes serverless, de nuvem e Kubernetes.

#### - [Instalação do Java](https://www.linuxcapable.com/how-to-install-oracle-java-17-on-linux-mint/)

Para utilizar o Quarkus será necessário que tenha instalado o JAVA. Caso não tenha seguir os próximos comandos:

Adicionar o Repositório PPA no Linux Mint:
```
sudo add-apt-repository ppa:linuxuprising/java -y
```

Atualizar a Lista de Pacotes APT para o Oracle Java no Linux Mint:
```
sudo apt update
```

Instalar o Oracle Java 17 no Linux Mint:
```
sudo apt install oracle-java17-installer oracle-java17-set-default
```

Para verificar a versão instalada utilize o comando:
```
java --version
```

#### - [Instalação do Quarkus](https://quarkus.io/get-started/)

Instalar via Interface de Linha de Comando. Usaremos o JBang para instalar a CLI do Quarkus:
```
curl -Ls https://sh.jbang.dev | bash -s - trust add https://repo1.maven.org/maven2/io/quarkus/quarkus-cli/
```


```
curl -Ls https://sh.jbang.dev | bash -s - app install --fresh --force quarkus@quarkusio
```

Se for a primeira vez que você está instalando, será necessário reiniciar o seu terminal/shell.

--

Para criar uma aplicação de início rápido e verificar se ocorreu tudo certo com o processo de instalação:
```
quarkus create && cd code-with-quarkus
```

Para executar a aplicação de início rápido:
```
quarkus dev
```

Pronto! Sua aplicação Quarkus está sendo executada em: [localhost:8080](http://localhost:8080/)

--

Para que as alterações no código seja visualizada imediatamento, será necessário realizar a seguinte modificação, no arquivo: `src/main/java/org/acme/GreetingResource.java` altere "Hello from RESTEasy Reactive" para "Hello RESTEasy". Em seguida, atualize o navegador e veja a mudança:


```
@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }
}
```

### 3.1 Projeto Quarkus e o MinIO


Faça o Download Projeto Quarkus localizado na pasta do repositório [code-with-quarkus](https://github.com/neftissilva/ifmt_minio/tree/e1ef20ef32a62eb8cca0844e425817709d60c3b9/code-with-quarkus) e salve na mesma pasta do Projeto do MinIO.

Abra o Projeto [code-with-quarkus](https://github.com/neftissilva/ifmt_minio/tree/e1ef20ef32a62eb8cca0844e425817709d60c3b9/code-with-quarkus) com seu editor de Projetos (IDE), no caso utilizaremos o [IntelliJ IDEA Community Edition](https://www.jetbrains.com/products/compare/?product=idea&product=idea-ce)


Pode ser que apareça determinados erros, devido a configuração do Projeto. Realize o "packaged" da aplicação com o comando `./mvnw package`, mas para isso acesse a pasta do projeto pelo terminal da aplicação e execute esse comando:
```
cd /caminho/para/o/seu/projeto/quarkus
```
```
./mvnw package
```

--

Caso aconteça o erro que informa: 
```
[...]
  Error: JAVA_HOME is not defined correctly.
  We cannot execute
```

No menu direito escolha o ícone do Maven, clique na opção `clean` no qual exibirá a tela `Project Struture > Projec` com a informação se o Java SDK está selecionado ou atribuído, se necessário utilize a opção de Download do Java pelo próprio IntelliJ. Após o Download clique em `Ok`.<br>
![ProjectStruture](https://github.com/neftissilva/ifmt_minio/blob/8a71269e59a600ae83af37a03c21bb727f8d2c39/diversos/image-3.png)

<br>

Ainda na opção do Maven aberta clique em `compile` conforme indicado na imagem a seguir:<br>
![IntelliJMaven](https://github.com/neftissilva/ifmt_minio/blob/8a71269e59a600ae83af37a03c21bb727f8d2c39/diversos/image-4.jpg)

--

Com tudo configurado execute novamente o "packaged" da aplicação com o comando `./mvnw package`, verifique que no terminal a pasta do projeto que deve ser execute esse comando:

```
./mvnw package
```

Os próximos passos estão na documentação gerada pelo Quarkus presente na pasta [Arquivo: ../code-with-quarkus/README.md](https://github.com/neftissilva/ifmt_minio/blob/8a71269e59a600ae83af37a03c21bb727f8d2c39/code-with-quarkus/README.md)

## 4. Referências

-  https://min.io/ <br>
-  https://kubernetes.io/ <br>
-  https://pt.quarkus.io/ <br>
-  https://linuxmint.com/ <br>
- https://www.docker.com/  <br>
- https://git-scm.com/download/linux <br>
- https://krew.sigs.k8s.io/docs/user-guide/setup/install/ <br>
- https://www.redhat.com/pt-br/topics/cloud-native-apps/what-is-quarkus <br>
- https://kind.sigs.k8s.io/docs/user/quick-start#installing-from-release-binaries <br>
- https://www.linuxcapable.com/how-to-install-oracle-java-17-on-linux-mint/ <br>

