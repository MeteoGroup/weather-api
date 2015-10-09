FORMAT: 1A

(WiP) Weather API
=============================

**Work in Progress** - MeteoGroup's public weather API documentation

Do you want to send [feedback](https://meteogroup.zendesk.com/hc/en-gb/requests/new?ticket_form_id=64951)?

# Group Observation

## Retrieve weather observation [/observation?location={latitudeInDegree,longitudeInDegree}{&speedUnit}{&temperatureUnit}{&precipitationUnit}]

### Observed weather for a given *latitude* and *longitude* [GET]

This resource provides data from existing weather observation stations. These are the latest values/parameters they provide.
For any requested location a relevant station is computed.
For the relevance the stations distance, height difference and available parameters are taken into concern. 

+ Parameters
    + latitudeInDegree: `52.13` (number, required)        - latitude in degree numeric format and in range <-90,90> eg. -85.541, 5.32
    + longitudeInDegree: `13.2` (number, required)        - longitude in degree numeric format and in range <-180,180> eg. -123.454, 179.864
    + speedUnit: `meters_per_second` (string, optional)   - select which unit for windSpeed, windGust, etc.; valid values: meters_per_second, miles_per_hour, kilometers_per_hour, beaufort
    + temperatureUnit: `degree` (string, optional)        - select which unit for airTemperature, etc.; valid values: degree, fahrenheit
    + precipitationUnit: `millimeter` (string, optional)  - select which unit for precipitation, etc.; valid values: millimeter, inch

+ Request

    + Headers

            X-Authentication: <API-Key>
            X-TraceId: <Trace-Id>

+ Response 200 (application/json)

    + Headers

            X-Request-Calls-Per-Interval: 4711
            E-Tag: "x234dff"
            Cache-Control: max-age=3628800

    + Body

            {
                "location" : {
                    "latitude": 52.5337,
                    "longitude": 13.37788,
                    "timeZoneName" : "Europe/Berlin"
                },
                relevantStation : {
                    "latitude": 123.43,
                    "longitude": -56.23,
                    "heightInMeters": -12.43,
                    "id": 4711,
                    "wmoId": 1231,
                    "sourceType" : "DWD"
                    "name" : "Berlin Tegel"
                },
                "observedAt": "2015-08-25T13:00:00+02:00"
                "airTemperature": {
                    "value" : 29.3,
                    "unit" : "DEGREE_CELSIUS"
                },
                "airPressureInHpa": 1012.9,
                "windSpeed": {
                    "value" : 7.4,
                    "unit" : "METER_PER_SECOND"
                    "timeIntervalInMinutes" : -60
                },
                "windGust" : {
                    "value" : 21.6,
                    "unit" : "METER_PER_SECOND"
                    "timeIntervalInMinutes" : -60
                },
                "windDirectionInDegree": 274,
                "dewPointTemperature": {
                    "value" : 15.8,
                    "unit" : "DEGREE_CELSIUS"
                },
                "precipitation": {
                    "value" : 0.2,
                    "unit" : "MILLIMETER",
                    "timeIntervalInMinutes" : -60
                },
                "relativeHumidityInPercent100based": 63,
                "effectiveCloudCoverInOcta": 3,
                "visibility": {
                    "value" : 0.2,
                    "unit" : "KILOMETER"
                }
            }


# Group Forecast

## Retrieve weather forecast [/forecast?location={latitudeInDegree,longitudeInDegree}{&speedUnit}{&temperatureUnit}{&precipitationUnit}]

### Forecasted weather for a given *latitude* and *longitude* [GET]

Weather forecast for the next 7 days.

+ Parameters
    + latitudeInDegree: `52.13` (number, required)                - latitude in degree numeric format and in range <-90,90> eg. -85.541, 5.32
    + longitudeInDegree: `13.2` (number, required)                - longitude in degree numeric format and in range <-180,180> eg. -123.454, 179.864
    + speedUnit: `meters_per_second` (string, optional)           - select which unit for windSpeed, windGust, etc.; valid values: meters_per_second, miles_per_hour, kilometers_per_hour, beaufort
    + temperatureUnit: `degree` (string, optional)                - select which unit for airTemperature, etc.; valid values: degree, fahrenheit
    + precipitationUnit: `millimeter` (string, optional)          - select which unit for precipitation, etc.; valid values: millimeter, inch

+ Request

    + Headers

            X-Authentication: <API-Key>
            X-TraceId: <Trace-Id>

+ Response 200 (application/json)

    + Headers

            X-Request-Calls-Per-Interval: 4712
            E-Tag: "a987dff"
            Cache-Control: max-age=600

    + Body

            {
                "forecasts": {
                    "hourly" : [
                        {
                            "validAt": "2015-08-25T14:00:00+02:00",
                            "airTemperature": {                        
                                "value" : 29,
                                "unit" : "DEGREE_CELSIUS"
                            },
                            "dewPointTemperature": {
                                "value" : 15.8,
                                "unit" : "DEGREE_CELSIUS"
                            },
                            "airPressureInHpa": 1012.9,
                            "sunshineDurationPastIntervalInMinutes": 23,
                            "precipitationPastInterval": {
                                "value" : 0,
                                "unit" : "MILLIMETER"
                            },
                            "precipitationProbabilityInPercent100based": 0,
                            "averageWindSpeed": {
                                "value" : 7.48,
                                "unit" : "METER_PER_SECOND",
                                "timeIntervalInMinutes" : -60  
                            },
                            "windGust" : {                     
                                "value" : 21.6,
                                "unit" : "METER_PER_SECOND",
                                "timeIntervalInMinutes" : -60    
                            },
                            "windDirectionInDegree": 274,
                            "effectiveCloudCoverInOcta": 3
                        }
                    ],
                    "interval6hours" : [
                        {
                            "validFrom": "2015-08-25T12:00:00+02:00",
                            "validUntil": "2015-08-25T18:00:00+02:00",
                            "minimumAirTemperature": {
                                "value" : 21,
                                "unit" : "DEGREE_CELSIUS"
                            },
                            "maximumAirTemperature": {
                                "value" : 29,
                                "unit" : "DEGREE_CELSIUS"
                            },
                            "airPressureInHpa": 1012.9,
                            "sunshineDurationInMinutes": 120,
                            "precipitation": {
                                "value" : 0,
                                "unit" : "MILLIMETER"
                            },
                            "precipitationProbabilityInPercent100based": 0,
                            "averageWindSpeed": {                                      
                                "value" : 7.48,
                                "unit" : "METER_PER_SECOND"
                            },
                            "windGust" : {                                     
                                "value" : 21.6,
                                "unit" : "METER_PER_SECOND"
                            },
                            "dominantWindDirectionInDegree": 274,   
                            "effectiveCloudCoverInOcta": 3           
                        }
                    ],
                    "interval12hours" : [                           
                        {
                            "validFrom": "2015-08-25T06:00:00+02:00",
                            "validUntil": "2015-08-25T18:00:00+02:00"
                            "minimumAirTemperature": {
                                "value" : 21,
                                "unit" : "DEGREE_CELSIUS"
                            },
                            "maximumAirTemperature": {
                                "value" : 29,
                                "unit" : "DEGREE_CELSIUS"
                            },
                            "airPressureInHpa": 1012.9,
                            "sunshineDurationInMinutes": 240,
                            "precipitation": {
                                "value" : 0,
                                "unit" : "MILLIMETER"
                            },
                            "precipitationProbabilityInPercent100based": 0,
                            "averageWindSpeed": {
                                "value" : 7.48,
                                "unit" : "METER_PER_SECOND"
                            },
                            "windGust" : {
                                "value" : 21.6,
                                "unit" : "METER_PER_SECOND"
                            },
                            "effectiveCloudCoverInOcta": 3,
                            "dominantWindDirectionInDegree": 274
                        }
                    ],
                    "daily" : [
                        {
                            "validFrom": "2015-08-25T00:00:00+02:00",
                            "validUntil": "2015-08-25T24:00:00+02:00",
                            "effectiveCloudCoverInOcta": 3,    
                            "sunshineDurationInHours": 7,
                            "sunrise" : "2015-08-25T06:23:00+02:00",
                            "sunset"  : "2015-08-25T19:34:00+02:00",
                            "minimumAirTemperature": {
                                "value" : 21,
                                "unit" : "DEGREE_CELSIUS"
                            },
                            "maximumAirTemperature": {              
                                "value" : 29,
                                "unit" : "DEGREE_CELSIUS"
                            },
                            "precipitation": {
                                "value" : 0,
                                "unit" : "MILLIMETER"
                            },
                            "dominantWindDirectionInDegree": 274,
                            "precipitationProbabilityInPercent100based": 0,
                            "averageWindSpeed": { 
                                "value" : 7.48,
                                "unit" : "METER_PER_SECOND"
                            },
                            "windGust" : {                      
                                "value" : 21.6,
                                "unit" : "METER_PER_SECOND"
                            },
                            "ultraVioletIndex": {              
                                "clearSky": 4              
                            }
                        }
                    ]
                }
            }


# Data Structures

## Backward compatibility

Weather API may add more parameters in the future.
Clients should irgnore unknown parameters.
Only when parameters will fundamently change, a new version might be introduced.

## Forecast Intervals

The response contains different time intervals which contain forecast in for different time spans.

Interval name  | Time interval  | Time span
---------------|----------------|--------------------------------
hourly         | 1 hour         | using time zone from requested location, today from 00:00 until 24:00, plus 1 day ahead, means 48 hours in total
interval6hours | 6 hours        | using time zone from requested location, today from 00:00 until 24:00, plus 6 days ahead, means 28 intervals in total
halfDaily      | 12 hours       | using time zone from requested location, today from 00:00 until 24:00, plus 6 days ahead, means 14 intervals in total
daily          | 24 hours       | using time zone from requested location, today from 00:00 until 24:00, plus 6 days ahead, means 7 days in total


## Units

Parameter | unit
---------------|-----
minAirTemperature                         | Degree or Fahrenheit
maxAirTemperature                         | Degree or Fahrenheit
airTemperature                            | Degree or Fahrenheit
dewPointTemperature                       | Degree or Fahrenheit
precipitation                             | mm or inch
precipitationLastHour                     | mm or inch
windSpeed                                 | m/s or km/h or m/h or BFT
windGust                                  | in Knots or m/h or km/h
date, time, time offset                   | timestamps including date, time and a zime offset are encoded in [ISO 8601 format](https://en.wikipedia.org/wiki/ISO_8601).
precipitationProbabilityInPercent100based | a percentage which refers to the amount of precipitation probability


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
