## SETUP

Add the following to the project root:

Filename: `endpoint.properties` 
Content: 
```
DIGITRANSIT_KEY = "Your digitransit API key here"
DIGITRANSIT_URL = "Digitransit URL matching"
```

## Application requirements
- Minimum SDK version 26
- Google Play Services (location)

## General notes on code quality
- Code structure: code is structured somewhat loosely, not following any specific pattern to too closely. Clean architecture, or similar patterns, would be recommended if this were to be developed further. This is because currently some components are needlessly coupled and the structure would likely become untidy with further complexity.
- Code is testable, but no tests have been written
- Best practices, such as label strings, could be extracted appropriately to `strings.xml` files. At least if localisations are added
- Data types from network layer are leaked all the way to presentation layer. Fix: create separate presentation entities, so that presentation is not coupled with the network layer
- ViewModel data management structure is handled in an imperative manner, which will not be scalable to more inputs (without quite extreme nesting). Fix: move on to reactive style, extract location and stop data to their own variables and combine those
- Location data is only fetched once (latest know location), making it so that if it's not available the app will remain in Error state. Fix: fetch latest location, if not available then request for new location updates