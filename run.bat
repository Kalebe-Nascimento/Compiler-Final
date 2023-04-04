@echo off
echo "Feito por Adryan Freitas & Kalebe Nascimento"
pause
call java -jar jflex-full-1.9.0.jar linguagem.jflex
pause
call java -jar java-cup-11b.jar -parser Parser linguagem.cup
pause
call javac -Xlint -classpath .;java-cup-11b-runtime.jar *.java
pause
cls
call java -cp .;java-cup-11b-runtime.jar Main codigo_fonte.txt
pause
del *.class
del sym.*
del Parser.*
del scanner.*