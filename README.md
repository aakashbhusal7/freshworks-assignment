
# Development
* Written in 100% Kotlin
* MVVM architecture
* Coroutines and retrofit for networking operations
* Room library for database
* Glide for image processing and display
* Dagger Hilt for dependency injection
* Mockk and jraska library for unit tests

# Setup
* You need to create a developer account from https://developers.giphy.com/ and obtain api key.
Add the api key to ~/.apikey.properties file
* Api calls can be inspected through chucker(for debug builds)

# Structure
* buildsrc -> separate module for dependencies
* data/ -> data layer where we have repositories and interfaces as well as relevant data classes for network calls and entities for database
          separated into local and remote
* ext/ -> Extension functions, Helper functions for network calls
* module/ -> Setup of dependency injection modules.
* ui/ -> UI layers where we have activity and fragments as well as view models.
* androidTest/ -> tests for room db
* test/ -> test for view models
