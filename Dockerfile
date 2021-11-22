FROM ubuntu:18.04

RUN apt-get update -y \
  && apt-get install -y curl zip unzip sudo tar tzdata openssh-server openssh-client \
  && apt-get clean \
  && rm -fr /var/lib/apt/lists/*

# install sdkman
WORKDIR /home/docker
RUN curl -s https://get.sdkman.io | bash
RUN /bin/bash -l -c "source /root/.sdkman/bin/sdkman-init.sh;sdk install java;sdk install kotlin;sdk install gradle"

USER root
WORKDIR /root/src

CMD ["true"]
