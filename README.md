# weather-api
MeteoGroup's public weather API documentation - Work in Progress

# Weather observation and forecast [/weather]

## Retrieve weather [GET]

+ Request

    + Headers

            X-Authentication: <API-Key>

+ Response 200 (application/json)

    + Headers

            X-Request-Number: 4711

    + Body

            {
                "location" {
                    "latitude": 53.12,
                    "longitude": 13.2
                },
                "time": {
                    "local": "2015-08-24T16:48:04",
                    "timezone": "+02:00"
                    "universal": "2015-08-24T14:48:04Z"
                },
                "observation": {
                    "temperature": 29,
                    "windSpeed": 7.48,
                    "windDirection": 274
                },
                "forecast":[
                    {
                        "time": {
                            "local": "2015-08-24T17:00:00",
                            "timezone": "+02:00"
                            "universal": "2015-08-24T15:00:00Z"
                        },
                        "temperature": 28,
                        "windSpeed": 5.23,
                        "windDirection": 269
                    },
                    {
                        "time": {
                            "local": "2015-08-24T18:00:00",
                            "timezone": "+02:00"
                            "universal": "2015-08-24T16:00:00Z"
                        },
                        "temperature": 27,
                        "windSpeed": 4.9,
                        "windDirection": 263
                    },
                ]
            }

