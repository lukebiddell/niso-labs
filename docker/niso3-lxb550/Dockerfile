FROM pklehre/niso-lab3
#
ADD src /src
#
RUN apt-get update
RUN apt-get -y install default-jre default-jdk
#
RUN javac -d bin $(find src -name *.java)
#
CMD ["-username", "lxb550", "-submission" , "java -cp bin lab3.main.Main"]
