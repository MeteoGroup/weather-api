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

# access tokens are valid for one hour an can be re-used
# print "ACCESS TOKEN (base64 encoded) >>> " + session.access_token

# fetch example observation data
# the OAuth2Session will automatically handle adding authentication headers
params = {
    'locatedAt': '13,52',
    'observedPeriod': 'PT0S',
    'fields': 'airTemperatureInCelsius'
}
data = session.get('https://point-observation.weather.mg/search', params=params)

print "RESPONSE DATA >>> " + data.text