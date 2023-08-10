# LocationApp

Test bed application for a number of Android Architectures and Services.

MVI Architecture

Unidirectional data flow

MviViewModel

Modular application utilising Roboelectric Unit tests to test Composable UI components.

App Module containing 2 Feature Modules.

```
Each Feature Module has Presentation --> Domain --> Data Modules

App Module includes just the Presentation modules of each of the features.
|
--> Location Presentation --> Location Domain --> LocationData
|
--> SpaceLaunch Presentation --> SpaceLaunch Domain --> SpaceLaunch Data
|
--> Authentication Presentation --> Authentication Domain --> Authentication Data
```

![Screenshot_20230627_173056](https://github.com/bennettandy/LocationApp/assets/1751538/b7cef1ef-ad12-48cc-8e34-c51719ae0577)
