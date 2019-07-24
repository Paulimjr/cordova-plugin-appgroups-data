console.log("Running hook to apply shared user id to Manifest...");
module.exports = function (context) {
    var fs = context.requireCordovaModule('fs'),
        path = context.requireCordovaModule('path');

    var platformRoot = path.join(context.opts.projectRoot, 'platforms/android/app/src/main');
    var manifestFile = path.join(platformRoot, 'AndroidManifest.xml');

    var APP_GROUP_ANDROID = "";
    if (process.argv.join("|").indexOf("APP_GROUP_ANDROID=") > -1) {
        APP_GROUP_ANDROID = process.argv.join("|").match(/APP_GROUP_ANDROID=(.*?)(\||$)/)[1]
    } else {
        var config = fs.readFileSync("config.xml").toString()
        APP_GROUP_ANDROID = getPreferenceValue(config, "APP_GROUP_ANDROID")
    }

    if (fs.existsSync(manifestFile)) {
        fs.readFile(manifestFile, 'utf8', function (err, data) {
            if (err) {
                throw new Error('Unable to find AndroidManifest.xml: ' + err);
            }

            if (data.indexOf('android:sharedUserId') == -1) {
                var result = data.replace(/<manifest/g, '<manifest android:sharedUserId="' + APP_GROUP_ANDROID +'" android:sharedUserLabel="@string/cordova_appgroups_shared_label" ');
                fs.writeFile(manifestFile, result, 'utf8', function (err) {
                    if (err) {
                        throw new Error('Unable to write AndroidManifest.xml: ' + err);
                    }
                })
            } else {
                throw new Error('Manifest already contains a sharedUserId');
            }
        });
    } else {
        throw new Error('Unable to find file ' + manifestFile);
    }
};
