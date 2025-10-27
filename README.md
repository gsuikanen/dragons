# AdventureBot

This is an assignment for [Dragons of Mugloar](https://dragonsofmugloar.com) challenge.

## How to run locally

I have deployed my app to Heroku, app can be reached at https://dragons-api-af95da256b0f.herokuapp.com (available until 02.11.2025).

For running locally, please refer to the following instructions

### Requirements
- Java 21+
- Maven 3.8+
- Internet connection (as we rely on [Dragons of Mugloar API](https://dragonsofmugloar.com/doc/))

### Start the app
```bash
mvn spring-boot:run
```
App will be available at http://localhost:8080

## Endpoints
| Endpoint                   | Method | Description                                                      | Sample response                              |
|----------------------------|--------|------------------------------------------------------------------|----------------------------------------------|
| /game/start                | POST   | Starts a new game asynchroniously                                | {"gameId": "RIrNZiSw",  "status": "started”} |
| /game/status/{gameId}      | GET    | Game status polling                                              | {"status": "running"}                        |
| /game/stats/last?limit=*n* | GET    | Retrieve last *n* game results (limit is optional, by default 5) | See below                                    |
| /game/stats/best?limit=n   | GET    | Retrieve best *n* game results (limit is optional, by default 5) | See below                                    |

### Sample response of 5 last and/or best results
```json
[
  {"timestamp":"2025-10-26T12:56:41.279785856","gameState":{"gameId":"l9iwVPVd","lives":0,"gold":49,"level":205,"score":31899,"highScore":0,"turn":275}},
  {"timestamp":"2025-10-26T13:36:09.058270787","gameState":{"gameId":"LxoBPDch","lives":0,"gold":18,"level":202,"score":31568,"highScore":0,"turn":269}},
  {"timestamp":"2025-10-26T13:38:20.899169318","gameState":{"gameId":"pSHuxKEA","lives":0,"gold":4,"level":190,"score":29304,"highScore":0,"turn":261}},
  {"timestamp":"2025-10-26T13:48:43.129901566","gameState":{"gameId":"oNC8VS2X","lives":0,"gold":7,"level":177,"score":27207,"highScore":0,"turn":242}},
  {"timestamp":"2025-10-26T12:56:22.759978959","gameState":{"gameId":"OfrXukz6","lives":0,"gold":7,"level":139,"score":21057,"highScore":0,"turn":199}}
]
```

## Testing results
While testing my solution, I have started 41 games and 40 of them had score over 1000 points (ranging from 1008 to 31899). Only one run had score which is lower than 1000 benchmark: 955 points. 