# Run instructions
# sh docker/lab3docker.sh
if [ "$#" -ne 1 ]
then
	echo "There should be 1 argument for lab number"
elif [ "$1" = "2" ]
then
	mkdir -p docker/niso2-lxb550/src
	cp -r src/lab2 src/helper docker/niso2-lxb550/src/
	echo "Lab 2 files are ready for Docker"
elif [ "$1" = "3" ]
then
	mkdir -p docker/niso3-lxb550/src
	cp -r src/lab3 src/helper src/elFarolBar docker/niso3-lxb550/src/
	echo "Lab 3 files are ready for Docker"
else
	echo "Argument should be a lab number"
fi
