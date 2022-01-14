# Simple script pour compiler les .java et supprimer les .class

alias compiler="javac linda/*.java linda/*/*.java; echo 'Compilation effectuée !'"
alias supprimer="rm linda/*.class linda/*/*.class; echo 'Suppression des fichiers de compilation effectuée !'"