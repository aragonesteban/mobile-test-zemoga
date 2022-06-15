# Mobile Test Zemoga

## How it looks

https://user-images.githubusercontent.com/93212523/173438777-d946556d-95c3-4e07-b359-2fabe05c781e.mov

Data taken from https://jsonplaceholder.typicode.com/ 

## Architecture

I created the project follow MVVM architecture suggested by Google and Clean Architecture and I used Koin to has a easy implementation to work with Dependency Injection. 
The architecture has three main layers:

**data**: In this module it will be everything about datasources like network and cache 

**domain**: In this module lives everything about bussines logic working with use cases, also is the responsible to get data from data module to send it to the view

**features**: In this module lives all presentation 

![Screen Shot 2022-06-14 at 7 10 28 PM](https://user-images.githubusercontent.com/93212523/173709745-be16eb40-e9fd-4761-aced-37dd56a5dc95.png)

## Libraries Used:

* [Koin](https://insert-koin.io/)
* [Room](https://developer.android.com/jetpack/androidx/releases/room)
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) 
* [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
* [View Binding](https://developer.android.com/topic/libraries/view-binding) 
* [Retrofit2](https://square.github.io/retrofit/)
* [Coroutines](https://developer.android.com/kotlin/coroutines)
* [Mockito-Kotlin](https://github.com/mockito/mockito-kotlin#mockito-kotlin)
* [RefreshVersion](https://github.com/jmfayard/refreshVersions)

## How I run the app?
 - Clone the repository
 - Open it in Android Studio
 - Wait until dependencies are installed
 - Run app in your emulator or physical device
 
 If you don't have Android Studio and you don't want to clone the repository, you can downlad an apk [here](https://www.mediafire.com/file/otjrjb65576xw6o/ZemogaTest.apk/file) 

