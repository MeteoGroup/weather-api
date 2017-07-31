# Authentication

<!-- TOC -->
* [Authentication](#authentication)
    * [Overview](#overview)
    * [Authorization Flow](#authorization-flow)
           - [The following grapic visualises the basic flow.](#the-following-grapic-visualises-the-basic-flow)
           - [Sample HTTP Request sequence](#sample-http-request-sequence)
    * [Java Code Sample](#java-code-sample)
    * [NodeJS Code Sample](#nodejs-code-sample)
    * [Python Code Sample](#python-code-sample)
    * [Postman Sample](#postman-sample)
<!-- /TOC -->


## Overview
Every resource of the Weather API is secured via OAuth 2.0. Each request requires a valid token from our central authorisation server. These tokens have a validity period of one hour.

The Required client credentials for this authentication can be requested via this [form](https://meteogroup.zendesk.com/hc/en-gb/requests/new?ticket_form_id=64951).

## Authorization Flow

Authorisation is obtained using OAuth 2.0 client_credentials grant type (see also [RFC6749: "The OAuth 2.0 Authorization Framework" section 4.4](https://tools.ietf.org/html/rfc6749#section-4.4)).

####The following grapic visualises the basic flow.
![](./Authorisation_Flow.png)

1) In order to authenticate for the authorization request, the client has to provide client credentials (i.e. ```client_id``` and ```client_secret```) as ```userid``` and ```password``` via HTTP Basic Authentication.

2) After successful authentication the client retrieves an authentication token as response. This access token comes in the form of a [JSON Web Token (JWT)](https://jwt.io/introduction/).

3) The access token can now be used to access Weather API Resources. The access token therefore is submitted as HTTP ```Authorization``` header of type ```Bearer```, when requesting against any resource.

The access token can be reused for multiple requests until it expires (which will happen one hour after it was issued).


#### Sample HTTP Request sequence

All HTTP communication to/from auth.weather.mg has to be secured, in order to protect credentials.
Hence, the server auth.weather.mg only offers the secure variant HTTPS.

__Authorization Request:__

```
POST /oauth/token HTTP/1.1
Authorization: ***** HTTP basic authentication with client_id and client_secret *****
Content-Type: application/x-www-form-urlencoded; charset=utf-8
Host: auth.weather.mg
Connection: close

grant_type=client_credentials
```

__Authorization Response:__

```
HTTP/1.1 200
Cache-Control: no-store
Pragma: no-cache
Content-Type: application/json;charset=UTF-8
Connection: close
{
    "access_token": "JWT_ACCESS_TOKEN",
    "token_type": "bearer",
    "expires_in": 3600,
    "scope": "space separated list of allowed scopes",
    "domain": "meteogroup",
    "jti": "id of JWT_ACCESS_TOKEN"
}
```
__Resource Request:__

```
GET /observation?location=53,13 HTTP/1.1
Authorization: Bearer JWT_ACCESS_TOKEN
Host: api.weather.mg
Connection: close
```
__Resource Response omitted__

```...
```

## Java Code Sample

For an implementation in Java we recommend the usage of Zalandos open source ```tokens``` library (see [https://github.com/zalando/tokens](https://github.com/zalando/tokens) for details).

```
AccessTokens accessTokens = Tokens.createAccessTokensWithUri(new URI("https://auth.weather.mg/oauth/token"))
        .usingClientCredentialsProvider(() -> new SimpleClientCredentials("your_client_id", "your_client_secret"))
        .usingUserCredentialsProvider(() -> null)
        .manageToken("token_name")
        .withGrantType("client_credentials")
        .done()
        .start();

```

The created ```accessTokens``` instance now handles the management for the tokens transparently.

By calling ```accessTokens.get("token_name")``` a valid token is provided. Per default that token has at least 40% of lifetime remaining (which in this case would mean 24 minutes). Whenever the lifetime falls below that (configurable) value a new token is requested in the background.

To use the token in a request, set the token as ```Bearer``` in the ```Authorization``` header.


```java
/**
*  example with Apache HTTP Client
*/
final String accessToken = accessTokens.get("token_name")

CloseableHttpClient httpClient = HttpClients.createDefault();

HttpGet get = new HttpGet("https://api.weather.mg/observation?location=53,13");
get.setHeader("Authorization", "Bearer " + accessToken);

CloseableHttpResponse httpResponse = httpClient.execute(get);
// process response...
```

```Java
/**
* example with Spring Resttemplate
*/
final String accessToken = accessTokens.get("token_name")

HttpHeaders headers = new HttpHeaders();
headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
HttpEntity entity = new HttpEntity<>(headers);

ResponseEntity<ResponseType> response = restTemplate.exchange(entity, ResponseType.class);
// process response...
```

## NodeJS Code Sample

Here is a simple implementation example in NodeJS with [request](https://www.npmjs.com/package/request).

```javascript
var request = require('request');

request({
    url: 'https://auth.weather.mg/oauth/token',
    method: 'POST',
    auth: {
        user: 'your_client_id',         // replace 'your_client_id' with your own ClientId
        pass: 'your_client_secret'      // replace 'your_client_secret' with your own ClientSecret
    },
    form: {
        'grant_type': 'client_credentials'
    }
}, function (error, response, body) {
    if (error) {
        console.error(error);
    } else {
        var body = JSON.parse(body);
        console.log(body);
    }
});

```

Response
```
{
  access_token: 'eyJraWQiOiJhdXRoLXNlcnZlci1iODA5MzIwMS03MjNkLTazZTgtYTgwMC1iOWU0ZTFjMDIxMGQiLCJhbGciOiJSUzI1NiJ1.eyJzdWIiOiJhbmRyZS5zY2hhZGUiLCJzY29wZSI6WyJwb2ludC1vYnNlcnZhdGlvbiIsInBvaW50LWZvcmVjYXN0Il0sImRvbWFpbiI6Im1ldGVvZ3JvdXAiLCJleHAiOjE0ODkxNDQyMTIsImp0aSI6IjZjZTJmYzlkLWUxYWEtNGNhMy04NzQ5LWI0NGU0ZWQ5MzdjZCIsImNsaWVudF8pZCI7ImFuZHJlLnNjaGFkZSJ9.XC8spP9mQICozXpmFx8PE8RFtKeZ1M0CNp0MWPMtqD3XiWp8ve1FJ_qoPImTbsS0QUlmvquGf3j0A8QeQ5QJyQ',
  token_type: 'bearer',
  expires_in: 3599,
  scope: 'point-observation point-forecast',
  domain: 'meteogroup',
  jti: '6ce2fc9d-e1aa-4ca3-8749-b44e4ed937cd'
}
```

That is a sample request then for the **point observation service** for the **latest values** only
which uses [dateformat](https://www.npmjs.com/package/dateformat) to calculate the necessary timestamp
for the observedFrom query parameter.
This example ask diectly for a MeteoGroup station instead of asking for a location with latitude and longitude.

```javascript
var request = require('request'),
    dateformat = require('dateformat'),
    observedFromDate = new Date();

observedFromDate.setHours(observedFromDate.getHours() - 1);

request({
    url: 'https://point-observation.weather.mg/search',
    auth: {
        'bearer': 'OAuth2_token' //replace that with the token you got from the auth process
    },
    qs: {
        meteoGroupStationIds: 10853,
        observedFrom: dateformat(observedFromDate, "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"),
        observedPeriod: "PT0S,PT1H",
        fields: "airTemperatureInCelsius,windGustInKnots"
    }
}, function (error, response, body) {
    if (error) {
        console.error(error)
    } else {
        console.log(JSON.parse(body));
    }
});

```

Response
```
{
  observations: [
    {
      meteoGroupStationId: '10853',
      observedFrom: '2017-03-09T20:00:00Z',
      observedUntil: '2017-03-09T20:00:00Z',
      observedPeriod: 'PT0S',
      airTemperatureInCelsius: 9.7,
      windSpeedInKnots: 13.606908,
    }
  ]
}
```

## Python Code Sample

This code snippet retrieves some air temperature observation data from near Berlin,
using OAuth authentication.
See [full source code](https://github.com/MeteoGroup/weather-api/blob/master/authorization/examples/python/README.md)

```python
from oauthlib.oauth2 import BackendApplicationClient
from requests_oauthlib import OAuth2Session

client_id = 'test-key'
client_secret = 'NSbpWeyfCrQR6K9kbpuuTBwsgLrOHtLm'  # SECRET! find a secure place to store, do NOT share

client = BackendApplicationClient(client_id=client_id)
client.prepare_request_body(scope=[])

# fetch an access token
session = OAuth2Session(client=client)
session.fetch_token(token_url='https://auth.weather.mg/oauth/token',
                    client_id=client_id,
                    client_secret=client_secret)

# fetch example observation data
# the OAuth2Session will automatically handle adding authentication headers
params = {
    'locatedAt': '13,52',
    'observedPeriod': 'PT0S',
    'fields': 'airTemperatureInCelsius'
}
data = session.get('https://point-observation.weather.mg/search', params=params)

print "RESPONSE DATA >>> " + data.text
```


## Postman Sample

Access token configuration in [Postman](https://www.getpostman.com).

| Configuration | Value |
| ----- | ----- |
| Token name | e.g. "my token" |
| Access Token URL | https://auth.weather.mg/oauth/token |
| Client ID | *your client id* |
| Client Secret | *your client secret* |
| Grant Type | Client Credentials |

![](./postman.png)
