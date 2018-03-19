FROM pklehre/niso-lab4
#
ADD src /src
ADD jars /jars
#
RUN apt-get update
RUN apt-get -y install default-jre default-jdk
#
RUN javac -d bin -sourcepath src -cp bin:jars/*.jar src/lab4/main/Main.java
#
CMD ["-username", "lxb550", "-submission" , "java -cp bin lab4.main.Main"]
