JAVAC=javac

all: compile jarServeur jarClient clean
	
compile : 
	$(JAVAC) distant_calculator/*.java

jarServeur:    
	@echo "Manifest-Version: 1.0" > manifest.txt
	@echo "Class-Path: ." >> manifest.txt
	@echo "Main-Class: distant_calculator.Server" >> manifest.txt
	@echo "" >> manifest.txt

	jar -cmf manifest.txt Server.jar distant_calculator/*.class
    
jarClient:    
	@echo "Manifest-Version: 1.0" > manifest.txt
	@echo "Class-Path: ." >> manifest.txt
	@echo "Main-Class: distant_calculator.Client" >> manifest.txt
	@echo "" >> manifest.txt
	
	jar -cmf manifest.txt Client.jar distant_calculator/*.class

clean:
	rm -rf *.class
	rm manifest.txt
