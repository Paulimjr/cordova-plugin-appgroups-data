# Cordova plugins - Group Storage
## Presentation

This plugin provides access to shared storage for applications sharing an AppGroupIdentifier.

## Features
- Save data by type
- Load the saved data

## Installation

Add as a cordova dependency, in config.xml (with Cordova 6.x or higher), replacing LATEST_VERSION by the desired commit hash (tag, branch, etc).

To install the plugin, the app group name must be given as variable. **(for IOS)**.

```bash
cordova plugin add https://github.com/OutSystemsExperts/cordova-plugin-appgroups-data.git#LATEST_VERSION --variable APP_GROUP_NAME=group.com.foo.bar.zzz
```

To install the plugin, the app group name must be given as variable. **(for Android)**.

```bash
cordova plugin add https://github.com/OutSystemsExperts/cordova-plugin-appgroups-data.git#LATEST_VERSION --variable APP_GROUP_ANDROID=group.com.foo.bar.zzz
```
## Using the plugin ##

### Namespace and API

All the functions described in this plugin reside in the `cordova.plugins.SharedAppGroupsData` namespace.

All the functions have 2 callbacks as their last 2 parameters, the first
being the success callback, and the second being the error callback.
The `suite` should match the **APP_GROUP_NAME** given in the instalation phase on IOS. In Android the value will be match the **APP_GROUP_ANDROID**. In additional, the value should be the packageName of the other application already installed.

```javascript
cordova.plugins.SharedAppGroupsData.save(
    { 
        key:'myKey',
        value:'value to be saved in key',
        suite:'group.com.foo.bar.zzz'
        <!-- In Android the suite value should be the packageName of the other application -->
    },
    function(){console.log("Success")},
    function(error){console.log("Error: " + error)}
)
```

```javascript
cordova.plugins.SharedAppGroupsData.load(
    { 
        key:'myKey',
        suite:'group.com.foo.bar.zzz'
    },
    function(value){console.log("Value: " + value)},
    function(error){console.log("Error: " + error)}
)
```

Please note that **every time an object of a is saved, it erases the previously saved data**

About Android: **You need to pass the packageName because is used the SharedPreferences to save the information**

