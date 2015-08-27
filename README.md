# weather-api

**Work in Progress** - MeteoGroup's public weather API documentation

# GET /weather?location={latitude,longitude}

Retrieve weather observation and forecast by a given *latitude* and *longitude*.

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
                    "timezone": "+02:00"
                },
                "observation": {
                    "relevantStation" : {
                        // clarify: relevant?
                        "latitude":123,
                        "longitude":456
                    },
                    "utcTimestamp": 1440594000 // or prefer to use ISO 8601 time stamp format
                    "temperature": 29,
                    "windSpeed": 7.48  // mean, last 10 minutes
                    "windGust" : 21.6,
                    "windDirection": 274
                    "dewPoint":15.8,
                    "precipitationLastHour":0, // mm
                    "relativeHumidity":0.63, // percent??
                    "totalCloudCover":3,   // octa???
                    "currentWeather": {
                        "code": 99, // wmo code
                        "text": "cloudy" // do we need this?
                    },
                    "weatherSymbol": 1199999 // clarify: do we need this. makes only sense with the symbol
                    "airPressure":1012.9 // hpa??
                },
                "forecast": {
                    "relevantStation" : {
                        // clarify: relevant?
                        "latitude":123,
                        "longitude":456
                    },
                    "interval_1h":[],     // do we need this?? vs. pricing?
                    "interval_3h":[],     // do we need this?? vs. pricing?
                    "interval_6h":[],     // do we need this?? vs. pricing?
                    "interval_12h":[],    // is this useful?
                    "daily" : [          // what is meant by daily?
                        {
                            "utcTimestamp": 1440594000, // or prefer to use ISO 8601 time stamp format
                            "minTemperature": 25,
                            "maxTemperature": 31,
                            "sunshineDurationHours":7,
                            "precipitation":0, // mm
                            "precipitationPropability":0, // percent
                            "windSpeed": 7.48,   // mean
                            "windGust" : 21.6,
                            "windDirection": 274
                            "ultraVioletIndex": {
                                "clearSky": 4,     // clarify: (1) name (2) WMO compliant
                                "cloudy":1         // clarify: (1) name (2) WMO compliant
                            },
                            "sunrise": 1440598000,
                            "sunset": 1440641200,
                            "totalCloudCover":3,   // octa???
                            "currentWeather": {      // clarify (1) Day or Night
                                "code": 99, // wmo code
                                "text": "cloudy" // do we need this?
                            },
                            "weatherSymbol": 1199999 // clarify: (1) do we need this. (2) Day or Night
                        }
                    ],
                    "hourly": [
                        {
                            "utcTimestamp": 1440594000, // or prefer to use ISO 8601 time stamp format
                            "temperature": 29,
                            "sunshineDurationMinutes":23,
                            "precipitation":0, // mm
                            "precipitationPropability":0, // percent
                            "windSpeed": 7.48,  // mean
                            "windGust" : 21.6,
                            "windDirection": 274
                            "dewPoint":15.8,
                            "totalCloudCover":3,   // octa???
                            "currentWeather": {
                                "code": 99, // wmo code
                                "text": "cloudy" // do we need this?
                            },
                            "weatherSymbol": 1199999 // clarify: do we need this. makes only sense with the symbol
                            "airPressure":1012.9 // hpa??
                        },
                        {
                            "utcTimestamp": 1440594000, // or prefer to use ISO 8601 time stamp format
                            "temperature": 27,
                            "windSpeed": 4.9,
                            "windDirection": 263
                        },
                    ]
                }
            }

