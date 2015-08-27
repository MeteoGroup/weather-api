# weather-api

**Work in Progress** - MeteoGroup's public weather API documentation

# GET /weather?location={latitude,longitude}

Retrieve weather observation and forecast by a given *latitude* and *longitude*.

+ Parameters
    + latitude: `52.13` (number, required) - latitude
    + longitude: `13.2` (number, required) - longitude

+ Request

    + Headers

            X-Authentication: <API-Key>

+ Response 200 (application/json)

    + Headers

            X-Request-Calls: 4711

    + Body

            {
                "location" {
                    "latitude": 53.12,
                    "longitude": 13.2
                    "timezone": "+02:00"
                },
                "observation": {
                    "sourceStation" : {
                        "latitude":123,
                        "longitude":-56,
                        "id": 4711, // MG station id
                        "wmoId": 1231, // if known
                        "sourceType" : "DWD" // do we know this?
                    },
                    "observedAt": "2015-08-25T13:00:00Z" // or prefer to use unix time stamp format
                    "airTemperature": 29,
                    "airPressureInHpa": 1012.9,
                    "windSpeed": 7.48  // mean, last 10 minutes
                    "windGust" : 21.6,
                    "windDirectionInDegree": 274
                    "dewPointTemperature": 15.8,
                    "precipitationLastHour": 0,
                    "relativeHumidityInPercent100based": 63,
                    "totalCloudCoverInOcta": 3,
                    "presentWeather": {
                        "code": 28, // wmo code
                        "literal": "FOG" // do we need this?
                    },
                    "weatherSymbol": 1199999 // clarify: do we need this. makes only sense with the symbol
                },
                "forecast": {
                    "relevantStation" : {
                        "latitude":123,
                        "longitude":-56,
                        "id": 0815, // MG station id
                        "sourceType" : "MOS_VIRTUAL"  // MOS or MOS_VIRTUAL to distinguish real stations to virtual ones
                    },
                    "interval_1h":[],     // do we need this?? vs. pricing?
                    "interval_3h":[],     // do we need this?? vs. pricing?
                    "interval_6h":[],     // do we need this?? vs. pricing?
                    "interval_12h":[],    // is this useful?
                    "hourly": [
                        {
                            "validUntil": "2015-08-25T14:00:00Z", // or prefer to use unix time stamp format
                            "airTemperature": 29,
                            "airPressureInHpa": 1012.9,
                            "sunshineDurationInMinutes": 23,
                            "precipitation": 0, // mm
                            "precipitationPropabilityInPercent100based": 0, // percent
                            "windSpeed": 7.48,  // mean
                            "windGust" : 21.6,
                            "windDirectionInDegree": 274
                            "dewPointTemperature": 15.8,
                            "totalCloudCoverInOcta": 3,
                            "presentWeather": {
                                "code": 00, // wmo code
                                "literal": "CLEAR_SKY" // do we need this?
                            },
                            "weatherSymbol": 1199999 // clarify: do we need this. makes only sense with the symbol
                        },
                        {
                            // ...
                        }
                    ],
                    "daily" : [          // what is meant by daily?
                        {
                            "validUntil": "2015-08-25T23:59:59Z", // or prefer to use unix time stamp format
                            "minAirTemperature": 25,
                            "maxAirTemperature": 31,
                            "sunshineDurationInHours":7,
                            "precipitation": 0, // mm
                            "precipitationPropabilityInPercent100based":0, // percent
                            "windSpeed": 7.48,   // mean
                            "windGust" : 21.6,
                            "windDirectionInDegree": 274
                            "ultraVioletIndex": {
                                "clearSky": 4,     // clarify: (1) name (2) WMO compliant
                                "cloudy": 1         // clarify: (1) name (2) WMO compliant
                            },
                            "sunrise": "2015-08-25T05:13:00Z",
                            "sunset": "2015-08-25T18:28:00Z",
                            "totalCloudCoverInOcta": 3,   // octa???
                            "presentWeather": {      // clarify (1) Day or Night
                                "code": 21, // wmo code
                                "literal": "RAIN" // do we need this?
                            },
                            "weatherSymbol": 1199999 // clarify: (1) do we need this. (2) Day or Night
                        },
                        {
                            // ...
                        }
                    ]
                }
            }


# Data Structures

## Units

    Parameter | unit
    ---------------|-----
    minAirTemperature       | Degree or Fahrenheit
    maxAirTemperature       | Degree or Fahrenheit
    airTemperature          | Degree or Fahrenheit
    dewPointTemperature     | Degree or Fahrenheit
    precipitation           | mm or inch            // !! clarify!
    precipitationLastHour   | mm or inch            // !! clarify!
    windSpeed               | m/s or km/h or m/h or BFT
    windGust                | in Knots or m/h or km/h


## PresentWeather (object)

The hourly weather code (WMO akronym 'ww') is an enumeration of certain types.
These types are based on WMO's SYNOP Format (FM-12) specification.

+ code (number)
    + Sample
        + 00
+ literal (string)
    + Sample
        + CLEAR_SKIES

```
Code | Enumeration literal
-----|--------------------
00 | CLEAR_SKIES
01 | CLOUDS_DISSOLVING
02 | STATE_OF_SKY_UNCHANGED
03 | CLOUDS_DEVELOPING
04 | VISIBILITY_REDUCED_BY_SMOKE
05 | HAZE
06 | WIDESPREAD_DUST_IN_SUSPENSION_NOT_RAISED_BY_WIND
07 | DUST_OR_SAND_RAISED_BY_WIND
08 | WELL_DEVELOPED_DUST_OR_SAND_WHIRLS
09 | DUST_OR_SAND_STORM_WITHIN_SIGHT_BUT_NOT_AT_STATION
10 | MIST
11 | PATCHES_OF_SHALLOW_FOG
12 | CONTINUOUS_SHALLOW_FOG
13 | LIGHTNING_VISIBLE__NO_THUNDER_HEARD
14 | PRECIPITATION_WITHIN_SIGHT_BUT_NOT_HITTING_GROUND
15 | DISTANT_PRECIPITATION_BUT_NOT_FALLING_AT_STATION
16 | NEARBY_PRECIPITATION_BUT_NOT_FALLING_AT_STATION
17 | THUNDERSTORM_BUT_NO_PRECIPITATION_FALLING_AT_STATION
18 | SQUALLS_WITHIN_SIGHT_BUT_NO_PRECIPITATION_FALLING_AT_STATION
19 | FUNNEL_CLOUDS_WITHIN_SIGHT
20 | DRIZZLE
21 | RAIN
22 | SNOW
23 | RAIN_AND_SNOW
24 | FREEZING_RAIN
25 | RAIN_SHOWERS
26 | SNOW_SHOWERS
27 | HAIL_SHOWERS
28 | FOG
29 | THUNDERSTORMS
30 | SLIGHT_TO_MODERATE_DUSTSTORM__DECREASING_IN_INTENSITY
31 | SLIGHT_TO_MODERATE_DUSTSTORM__NO_CHANGE
32 | SLIGHT_TO_MODERATE_DUSTSTORM__INCREASING_IN_INTENSITY
33 | SEVERE_DUSTSTORM__DECREASING_IN_INTENSITY
34 | SEVERE_DUSTSTORM__NO_CHANGE
35 | SEVERE_DUSTSTORM__INCREASING_IN_INTENSITY
36 | SLIGHT_TO_MODERATE_DRIFTING_SNOW__BELOW_EYE_LEVEL
37 | HEAVY_DRIFTING_SNOW__BELOW_EYE_LEVEL
38 | SLIGHT_TO_MODERATE_DRIFTING_SNOW__ABOVE_EYE_LEVEL
39 | HEAVY_DRIFTING_SNOW__ABOVE_EYE_LEVEL
40 | FOG_AT_A_DISTANCE
41 | PATCHES_OF_FOG
42 | FOG__SKY_VISIBLE__THINNING
43 | FOG__SKY_NOT_VISIBLE__THINNING
44 | FOG__SKY_VISIBLE__NO_CHANGE
45 | FOG__SKY_NOT_VISIBLE__NO_CHANGE
46 | FOG__SKY_VISIBLE__BECOMING_THICKER
47 | FOG__SKY_NOT_VISIBLE__BECOMING_THICKER
48 | FOG__DEPOSITING_RIME__SKY_VISIBLE
49 | FOG__DEPOSITING_RIME__SKY_NOT_VISIBLE
50 | INTERMITTENT_LIGHT_DRIZZLE
51 | CONTINUOUS_LIGHT_DRIZZLE
52 | INTERMITTENT_MODERATE_DRIZZLE
53 | CONTINUOUS_MODERATE_DRIZZLE
54 | INTERMITTENT_HEAVY_DRIZZLE
55 | CONTINUOUS_HEAVY_DRIZZLE
56 | LIGHT_FREEZING_DRIZZLE
57 | MODERATE_TO_HEAVY_FREEZING_DRIZZLE
58 | LIGHT_DRIZZLE_AND_RAIN
59 | MODERATE_TO_HEAVY_DRIZZLE_AND_RAIN
60 | INTERMITTENT_LIGHT_RAIN
61 | CONTINUOUS_LIGHT_RAIN
62 | INTERMITTENT_MODERATE_RAIN
63 | CONTINUOUS_MODERATE_RAIN
64 | INTERMITTENT_HEAVY_RAIN
65 | CONTINUOUS_HEAVY_RAIN
66 | LIGHT_FREEZING_RAIN
67 | MODERATE_TO_HEAVY_FREEZING_RAIN
68 | LIGHT_RAIN_AND_SNOW
69 | MODERATE_TO_HEAVY_RAIN_AND_SNOW
70 | INTERMITTENT_LIGHT_SNOW
71 | CONTINUOUS_LIGHT_SNOW
72 | INTERMITTENT_MODERATE_SNOW
73 | CONTINUOUS_MODERATE_SNOW
74 | INTERMITTENT_HEAVY_SNOW
75 | CONTINUOUS_HEAVY_SNOW
76 | DIAMOND_DUST
77 | SNOW_GRAINS
78 | SNOW_CRYSTALS
79 | ICE_PELLETS
80 | LIGHT_RAIN_SHOWERS
81 | MODERATE_TO_HEAVY_RAIN_SHOWERS
82 | VIOLENT_RAIN_SHOWERS
83 | LIGHT_RAIN_AND_SNOW_SHOWERS
84 | MODERATE_TO_HEAVY_RAIN_AND_SNOW_SHOWERS
85 | LIGHT_SNOW_SHOWERS
86 | MODERATE_TO_HEAVY_SNOW_SHOWERS
87 | LIGHT_SNOW_AND_ICE_PELLET_SHOWERS
88 | MODERATE_TO_HEAVY_SNOW_AND_ICE_PELLET_SHOWERS
89 | LIGHT_HAIL_SHOWERS
90 | MODERATE_TO_HEAVY_HAIL_SHOWERS
91 | THUNDERSTORM_IN_PAST_HOUR__CURRENTLY_ONLY_LIGHT_RAIN
92 | THUNDERSTORM_IN_PAST_HOUR__CURRENTLY_ONLY_MODERATE_TO_HEAVY_RAIN
93 | THUNDERSTORM_IN_PAST_HOUR__CURRENTLY_ONLY_LIGHT_SNOW_OR_RAIN_AND_SNOW_MIX
94 | THUNDERSTORM_IN_PAST_HOUR__CURRENTLY_ONLY_MODERATE_TO_HEAVY_SNOW_OR_RAIN_AND_SNOW_MIX
95 | LIGHT_TO_MODERATE_THUNDERSTORM
96 | LIGHT_TO_MODERATE_THUNDERSTORM_WITH_HAIL
97 | HEAVY_THUNDERSTORM
98 | HEAVY_THUNDERSTORM_WITH_DUSTSTORM
99 | HEAVY_THUNDERSTORM_WITH_HAIL
```

Source/details of FM-12 can be found at http://weather.unisys.com/wxp/Appendices/Formats/SYNOP.html


## Total Cloud Cover (number)

This symbolic letter shall embrace the total fraction of the celestial dome covered by clouds irrespective of their genus.
(WMO akronym 'N')

    Value | Meaning
    ------|---------
    0 | 0
    1 | 1 okta or less, but not zero
    2 | 2 oktas
    3 | 3 oktas
    4 | 4 oktas
    5 | 5 oktas
    6 | 6 oktas
    7 | 7 oktas
    8 | 8 oktas
    9 | Sky obscured by fog and/or other meteorological phenomena

