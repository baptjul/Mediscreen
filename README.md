# Mediscreen

## Projet Java & React

Ce projet utilise Java 17 et React 18.

## Prérequis

- Installer Docker.

## Mise en route du projet

1. Exécutez `mvn clean verify` sur l'ensemble des microservices (à l'exception de "app").
2. À la racine du projet, exécutez `docker-compose up` pour lancer tous les projets.
3. Le site sera accessible à l'adresse [http://localhost:3000/](http://localhost:3000/).

### Si vous utilisez Docker Desktop :
1. Exécutez `docker-compose build` pour construire le projet.
2. Ensuite, exécutez `docker-compose create` pour créer les conteneurs.

## Premier ajout de données

Vous pouvez exécuter les requêtes CURL suivantes pour ajouter des données :

### Ajout d'utilisateur :
curl -H 'Content-Type: application/json' -d '{
      "username": "john_doe",
      "password": "password123"
}' -X POST http://localhost:8083/signup

### Ajout de patient   :
curl -H "Content-Type: application/json" -d '{
    "family": "Ferguson",
    "given": "Lucas",
    "dob": "1968-06-22",
    "sex": "M",
    "address": "2 Warren Street",
    "phone": "387-866-1399"
}' -X POST http://localhost:8081/patient/add

curl -H "Content-Type: application/json" -d '{
    "family": "Rees",
    "given": "Pippa",
    "dob": "1952-09-27",
    "sex": "F",
    "address": "745 West Valley Farms Drive",
    "phone": "628-423-0993"
}' -X POST http://localhost:8081/patient/add

curl -H "Content-Type: application/json" -d '{
    "family": "Arnold",
    "given": "Edward",
    "dob": "1952-11-11",
    "sex": "M",
    "address": "599 East Garden Ave",
    "phone": "123-727-2779"
}' -X POST http://localhost:8081/patient/add

### Ajout d'historique :
curl -H "Content-Type: application/json" -d '{
    "patId": 1,
    "note": "Le patient s'est plaint de maux de tête sévères. Prescription de 500 mg d'ibuprofène"
}' -X POST http://localhost:8082/patHistory/add

curl -H "Content-Type: application/json" -d '{
    "patId": 2,
    "note": "Bilan annuel. La tension artérielle et les niveaux de cholestérol sont normaux. Aucune action supplémentaire requise."
}' -X POST http://localhost:8082/patHistory/add

curl -H "Content-Type: application/json" -d '{
    "patId": 3,
    "note": "Patient diagnostiqué avec un diabète de type 2. Changements alimentaires recommandés et prescription de Metformine."
}' -X POST http://localhost:8082/patHistory/add

curl -H "Content-Type: application/json" -d '{
    "patId": 3,
    "note": "Suite au test, Hémoglobine A1C s'avère anormal."
}' -X POST http://localhost:8082/patHistory/add

curl -H "Content-Type: application/json" -d '{
    "patId": 3,
    "note": "Les niveaux de cholestérol sont élevés, et le patient signale des épisodes récents de vertige"
}' -X POST http://localhost:8082/patHistory/add

curl -H "Content-Type: application/json" -d '{
    "patId": 5,
    "note": "Le patient a signalé des douleurs constantes dans le bas du dos. Orienté vers un kinésithérapeute pour une évaluation approfondie."
}' -X POST http://localhost:8082/patHistory/add

curl -H "Content-Type: application/json" -d '{
    "patId": 5,
    "note": "il y a eu une rechute après le test des anticorps. Cependant, les niveaux de Microalbumine restent dans la norme."
}' -X POST http://localhost:8082/patHistory/add

curl -H "Content-Type: application/json" -d '{
    "patId": 5,
    "note": "Après nouveau le test, une réaction a été observée. Des anticorps ont été détectés. A suivre."
}' -X POST http://localhost:8082/patHistory/add

## Connexion au site

Après l'ajout de données la connexion au site peut être faite avec:
  - username : john_doe
  - password : password123
