default: run

run: FrontendFD.class
	java FrontendFD

clean:
	rm *.class


runTests: runDataWranglerTests runAlgorithmEngineerTests runBackendDeveloperTests runFrontendDeveloperTests

#FD Makefile below
runFrontendDeveloperTests: FrontendDeveloperTests.class DijkstraBackendBD.class DotReaderDW.class AlgorithmExecutionAE.class FrontendInterfaceFD.class FrontendFD.class
	java -jar junit5.jar -cp . --select-class=FrontendDeveloperTests

FrontendDeveloperTests.class: FrontendDeveloperTests.java
	javac -cp .:junit5.jar FrontendDeveloperTests.java

DijkstraBackendBD.class: DijkstraBackendBD.java
	javac DijkstraBackendBD.java

DotReaderDW.class: DotReaderDW.java DotReaderInterfaceDW.java
	javac DotReaderDW.java

AlgorithmExecutionAE.class: AlgorithmExecutionAE.java
	javac AlgorithmExecutionAE.java

FrontendInterfaceFD.class: FrontendInterfaceFD.java
	javac FrontendInterfaceFD.java

FrontendFD.class: FrontendFD.java
	javac FrontendFD.java

#BD Makefile below
runBackendDeveloperTests: BackendDeveloperTests.class
	java -jar junit5.jar -cp . --select-class=BackendDeveloperTests

%.class : %.java
	javac $< -cp .:junit5.jar

#DW Makefile below
junit5.jar:
	wget http://pages.cs.wisc.edu/~cs400/junit5.jar

runDataWranglerTests: junit5.jar DotReaderDW.class DataWranglerTests.java
	javac -cp .:junit5.jar DataWranglerTests.java
	java -jar junit5.jar -cp . --select-class DataWranglerTests

#AE Makefile below

runAlgorithmEngineerTests: AlgorithmEngineerTests.class
	java -jar  junit5.jar -cp . --select-class=AlgorithmEngineerTests

AlgorithmEngineerTests.class: DijkstraGraphAE.class AlgorithmExecutionAE.class AlgorithmEngineerTests.java
	javac -cp .:junit5.jar AlgorithmEngineerTests.java

DijkstraGraphAE.class: BaseGraph.class GraphADT.class DijkstraGraphAE.java
	javac DijkstraGraphAE.java

BaseGraph.class: BaseGraph.java
	javac BaseGraph.java

GraphADT.class: GraphADT.java
	javac GraphADT.java

AlgorithmExecutionInterfaceAE.class: AlgorithmExecutionInterfaceAE.java
	javac AlgorithmExecutionInterfaceAE.java

