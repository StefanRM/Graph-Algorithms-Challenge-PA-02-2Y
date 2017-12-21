all: build

build:
	javac Permutari.java
	javac Patrula.java

run-p1:
	java Permutari

run-p3:
	java Patrula

clean:
	rm *.class
