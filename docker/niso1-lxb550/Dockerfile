# Do not change the following line . It specifies the base image which
# will be downloaded when you build your image . We will release a new
# base image for each lab .
FROM pklehre/niso-lab1
#
#
#
#
#
#Add all the files you need for your submission into the Docker image ,
#e . g . source code , Java bytecode , etc . In this example , we assume your
#program is the Python code in the file abc123 . py . For simplicity , we
#copy the file to the / bin directory in the Docker image . You can add
#multiple files if needed .
ADD BitString.class /bin
ADD Population.class /bin
ADD niso_lab1.class /bin
ADD BitString.java /src
ADD Population.java /src
ADD niso_lab1.java /src
#
#
#
#
#Install all the software required to run your code . The Docker image
#is derived from the Debian Linux distribution . You therefore need to
#use the apt - get package manager to install software . You can install
#e . g . java , python , ghc or whatever you need . You can also
# compile your code if needed .
# In this example , we install Python and Numpy . Code to install Java
# is commented out .
RUN apt-get update
RUN apt-get -y install default-jre default-jdk
#RUN apt-get -y openjdk-8-jdk
#run -ti java:8-jdk
#RUN apt - get -y install python
#RUN apt - get -y install python - numpy
# The final line specifies your username and how to start your program .
# Replace abc123 with your real username and python / bin / abc123 . py
# with what is required to start your program .
CMD ["-username", "lxb550", "-submission" , "java -cp /bin/ niso_lab1"]
