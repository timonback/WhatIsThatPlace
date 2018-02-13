# WhatIsThatPlace

[![CircleCI](https://circleci.com/gh/timonback/WhatIsThatPlace.svg?style=svg)](https://circleci.com/gh/timonback/WhatIsThatPlace)

A simple Android application that works in combination with [WhatIsThatPlace-Proxy](https://github.com/timonback/WhatisthatPlace-Proxy).

It shows additional information for images you have taken - most importantly the location (if the underlying service [Google Vision API]) is able to detect it.

## Screenshots

MainView | DetailView
------------ | -------------
The MainView gets shown right after the app gets opened. It is a browseable list of all images on the phone ordered by date. | The DetailView shows all image related information - especially the place. In this case Martinitoren in Groningen, The Netherlands.
![](https://github.com/timonback/WhatIsThatPlace/blob/master/doc/screen_overview.png?raw=true) | ![](https://github.com/timonback/WhatIsThatPlace/blob/master/doc/screen_detail.png?raw=true)

## Configuration
The ```gradle.properties``` file requires two configuration that have to be set. First, the key for the Google Maps API have to be set and second also the key for the Google Vision API. The content of the ```gradle.properties``` in the root folder of the project could look like:
```
MAPS_API_KEY="myMapsKey"
VISION_API_KEY="myVisionKey"
```

The key for Google Maps has to be generated in the [Google Cloud Manager](https://console.cloud.google.com/apis/credentials). Also, the [Google Vision API](https://console.cloud.google.com/apis/api/vision.googleapis.com/overview) key can be generated - after the API was activated.