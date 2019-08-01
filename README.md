# Cordova plugins - Group Storage
## Presentation

This plugin provides access to shared private storage for applications sharing an **AppGroupIdentifier** in iOS or applications sharing a **sharedUserId** in Android.

The plugin has two simple methods, one to save data and one to load data.

## Installation
### Pre Conditions
There are some pre conditions for this plugin to work.

#### iOS
On iOS, for **each application** that you need using the same shared storage, you should create a provision profile with the AppGroup capability. The group name should be the same as the **APP_GROUP_NAME** passed in the installation phase of the plugin.
* [How to configure provision profile](https://www.atomicbird.com/blog/sharing-with-app-extensions)

#### Android
On Android, the plugin implements this functionality using the SharedPreferences on a private context. For an application to access another application sharedPreferences they should both have the same **sharedUserId**. The plugin handles this in the installation phase with the variable **APP_GROUP_ANDROID**.

The final step to share the data is to have all apps that want to share the data this way **signed with the same certificate**.
* [SharedUserID in Android](https://androidcreativity.wordpress.com/2018/07/25/two-android-applications-with-the-same-user-id-linux-user-id)

### Cordova plugin install
Add as a cordova dependency, in config.xml (with Cordova 6.x or higher), replacing LATEST_VERSION by the desired commit hash (tag, branch, etc).

To install the plugin, the app group name must be given as variable. **(for IOS)**.

```bash
cordova plugin add https://github.com/OutSystemsExperts/cordova-plugin-appgroups-data.git#LATEST_VERSION
--variable APP_GROUP_NAME=group.com.foo.bar.zzz  --variable APP_GROUP_ANDROID=group.com.foo.bar.zzz
```

To install the plugin, the app group android must be given as variable. **(for Android)**.

```bash
cordova plugin add https://github.com/OutSystemsExperts/cordova-plugin-appgroups-data.git#LATEST_VERSION --variable APP_GROUP_ANDROID=group.com.foo.bar.zzz
```


## Using the plugin ##

All the functions described in this plugin reside in the `cordova.plugins.SharedAppGroupsData` namespace.

All the functions have 2 callbacks as their last 2 parameters, the first being the success callback, and the second being the error callback.
The `suite` parameter value should match the **APP_GROUP_NAME** given in the instalation phase on IOS and, on Android, the `suite` value should match the packageName of the application that is sharing the data. On android, if the application with the packageName `suite` is not installed, an error will be triggered on the error callback.

### Save
Writes to the data storage.
* @param options - Must contain an object with the parameters **key**, **value** and **suite**
* @param success - Optional callback when operation is successful
* @param error - Optional callback when the operation fails. Should contain an error object with code and message

```javascript
//cordova.plugins.SharedAppGroupsData.save(Object options,[Function success],[Function error])
cordova.plugins.SharedAppGroupsData.save(
    { 
        key:'myKey',
        value:'value to be saved in key',
        suite:'group.com.foo.bar.zzz'
        <!-- In Android the suite value should be the packageName of the application storing the data -->
    },
    function(){console.log("Success")},
    function(error){console.log("Error: " + error)}
)
```

### Load
Reads data from the storage.
* @param options - Must contain an object with the parameters **key** and **suite**
* @param success - Optional callback when operation is successful that receives the value stored in the key variable
* @param error - Optional callback when the operation fails. Should contain an error object with code and message

```javascript
//cordova.plugins.SharedAppGroupsData.load(Object options,[Function success],[Function error])
cordova.plugins.SharedAppGroupsData.load(
    { 
        key:'myKey',
        suite:'group.com.foo.bar.zzz'
        <!-- In Android the suite value should be the packageName of the application storing the data -->
    },
    function(value){console.log("Value: " + value)},
    function(error){console.log("Error: " + error)}
)
```

Please note that **every time an object with key X is saved, it erases the previously saved data on the key X**
