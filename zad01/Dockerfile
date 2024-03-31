FROM ubuntu:latest

RUN sed -i 's/http:\/\/archive.ubuntu.com\/ubuntu\//http:\/\/archive.ubuntu.com\/ubuntu\//g' /etc/apt/sources.list

RUN apt-get update && \
    apt-get install -y fpc

COPY zad_01.pas /app/

RUN fpc /app/zad_01.pas

CMD ["/app/zad_01", "10", "50", "5"]
