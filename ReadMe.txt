# Projet du Module ACS du Master 1 Informatique de l'université de Poitiers
# Membres du groupe : Romain CHARPENTIER et Etienne WEHOWSKI
# Date : 07/04/2018

##########################################################################################
######							PRESENTATION										######
##########################################################################################

Ce projet consiste à développer une salle de discussion en Java RMI. L'interface graphique est réalisée en Swing.

Ce projet comprend un côté serveur (Server.java) et un côté client (Client.java) qui prend en paramètre l'adresse du serveur.

##########################################################################################
######							FONCTIONNALITES								    	######
##########################################################################################

Ces fonctionnalités sont du côté client. Le serveur peut uniquement être lancé.

- Créer un salon de discussion :
        Une fois le programme lancé, il faut cliquer sur l'onglet "Create" et remplir les champs "Nickname" et "Chatroom" au minimum
        qui sont le pseudo et le nom du salon. Il est également possible de changer la valeur du champ "Max users" (nombre maximum
        d'utilisateurs dans le futur salon) et aussi d'ajouter un mot de passe pour s'y connecter. Une fois les champs rempli, il faut cliquer sur "Confirm".

- Se connecter :
        Une fois le programme lancé, il faut cliquer sur l'onglet "Join". Une liste apparait alors contenant la liste des salons en ligne.
        Il est possible d'actualiser cette liste avec le bouton "Actualize". Il faut donc sélectionner un salon dans cette liste et remplir
        le champ "Nickname" correspondant au pseudo puis appuyer sur le bouton "Confirm" pour se connecter.

- Se déconnecter :
        Il y a plusieurs façons de se déconnecter, la plus simple étant de simplement fermer la fenêtre (cela déconnectera le client).
        Il est ensuite possible de faire un click droit sur l'onglet du salon de discussion ce qui fermera l'onglet et donc déconnectera
        le client. Cette seconde méthode laisse le client connecter sur les autres salons de discussions où il est présent.

- Changer de salon :
        Il est possible de se connecter sur plusieurs salons en rajoutant un onglet en haut de la fenêtre. Il faut appuyer sur le bouton "+".
        Un nouvel onglet apparait alors qui propose de se connecter. Il est possible de naviguer entre les onglets ouverts en effectuant un click
        gauche sur ces derniers.

- Envoi d'un message :
        Une fois connecté à un salon, un champ textuel apparaît en bas de la fenêtre, le message doit être écrit ici. Ensuite
        l'utilisateur peut appuyer sur la touche "Entrée" de son clavier ou sur le bouton "Send" pour envoyer le message.

- Envoi d'image :
        Une fois connecté à un salon, l'utilisateur doit cliquer sur le bouton "Img" qui va ouvrir une fenêtre de sélection de fichier.
        Il faut ici choisir l'image à envoyer en valider. L'image est envoyé automatiquement. Il est possible de cliquer sur l'image envoyée
        pour l'afficher en taille réelle.

- Envoi d'une musique :
        Une fois connecté à un salon, l'utilisateur doit cliquer sur le bouton "Sound" qui va ouvrir une fenêtre de sélection de fichier.
        Il faut ici choisir la musique à envoyer en valider (en format Wav). La musique est envoyée automatiquement par l'intermédiaire
        d'un bouton "Play" qui permet de lancer et arrêter la musique.

- Envoi d'un message privé :
        Pour envoyer un message privé (n'importe quel message, image et musique inclus), il faut selectionner un membre parmi la liste des
        utilisateurs du salon. Tant qu'un membre est sélectionné, tous les messages envoyés seront uniquement pour celui-ci. Il est possible
        de déselectionner avec un click droit n'importe où sur la liste des utilisateurs.


##########################################################################################
######								MAKEFILE										######
##########################################################################################

Le paquet java est nécessaire pour la compilation du projet.

Compilation du projet : commande "make"

A la fin de la compilation, 2 fichiers jar sont créés et peuvent être lancés par la commande "java -jar".

##########################################################################################
######							EXEMPLES D'UTILISATION								######
##########################################################################################

Les commandes du terminal 1 doivent se faire en premier, suivi des commandes du terminal 2. Les commentaires sont marqués entre parenthèses. Avant de lancer l'exemple, il faut se placer dans le répertoire contenant du projet contenant le makefile.

Le client doit préciser le hostname (nom du server) en argument de la commande pour le lancer.