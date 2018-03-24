JAVAC=javac

all: compile jarServeur jarClient clean
	
compile : 
	$(JAVAC) chatroom/*.java

jarServeur:    
	@echo "Manifest-Version: 1.0" > manifest.txt
	@echo "Class-Path: ." >> manifest.txt
	@echo "Main-Class: chatroom.server.Server" >> manifest.txt
	@echo "" >> manifest.txt

	jar -cmf manifest.txt Server.jar chatroom/server/*.class
    
jarClient:    
	@echo "Manifest-Version: 1.0" > manifest.txt
	@echo "Class-Path: ." >> manifest.txt
	@echo "Main-Class: chatroom.client.Client" >> manifest.txt
	@echo "" >> manifest.txt
	
	jar -cmf manifest.txt Client.jar chatroom/client/*.class

clean:
	rm -rf *.class
	rm manifest.txt
