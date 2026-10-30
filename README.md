# Clean Architecture vs Architecture Hexagonale — le code de la vidéo

Le code de la vidéo **ItSkillsMaker** « Clean Architecture vs Architecture Hexagonale (Spring Boot) » :
👉 https://youtu.be/2Pm8YNc01iE

Le cas d'usage « déposer une candidature » écrit dans le vocabulaire de la Clean Architecture
(entities, use cases, input/output boundaries, gateways, interactor), en Java pur, sans framework.
La version hexagonale du même projet est celle de la vidéo Architecture Hexagonale : https://youtu.be/AzJciFr8I5o

## Structure

```text
src/com/itskillsmaker/hiring/
├── entities/            # JobApplication, JobOffer, ApplicationStatus, JobOfferClosed
├── usecases/submit/     # l'interactor, ses boundaries, ses gateways, ses erreurs metier
└── demo/Demo.java       # 4 scenarios executes
```

## Lancer (Java 21+, aucune dépendance)

```bash
javac -d out $(find src -name "*.java")
java -cp out com.itskillsmaker.hiring.demo.Demo
```

Sortie attendue :

```text
1. presented: submittedAt=2026-10-27T10:00:00Z pendingCount=1
2. same offer twice: OK AlreadyApplied
3. closed offer (thrown by the JobOffer entity): OK JobOfferClosed
4. eleventh pending application: OK TooManyPendingApplications
```
