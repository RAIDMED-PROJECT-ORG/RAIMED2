# RAIMED2
## Application de Diagnostic Virtuel pour les Étudiants en Médecine

Ce Proof of Concept (POC) représente une application novatrice conçue par un professeur de médecine, destinée à faciliter les diagnostics virtuels avec des patients. L'objectif principal de cette application est d'aider les étudiants en médecine à développer leurs compétences de réflexion dans le processus diagnostique.

# Documentation

## [Tout le monde] Vidéo tutoriels 

Nous avons mis en place deux vidéos (raimedApplication/vidéos tutoriels) afin de:
- comprendre comment lancer l'application raimed2
- créer un cas de patient virtuel

## [Developpeur] Stack technique

Nous avons developpé cette application en:
- Springboot (Java)
- VueJs
- Postgres

La version dev de l'application utilise docker (se reférer aux properties & docker compose file).

## [Developpeur] Datastructure

A des fins d'abstraction de la données, nous avons créer un fichier xsd définissant la données sous format XML (Nous avons aussi mis un example de fichier xml possible). Certains outils permettent de recevoir ce type xsd et de créer des formulaires. Cela pourrait eviter de coder une partie de l'application et de permettre une généralisation du type PatientVirtuel à une multitude d'applications.



