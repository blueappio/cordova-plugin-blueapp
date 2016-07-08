var exec = require('cordova/exec');

exports.request = function(action, message, success, error) {
    exec(successCallback, errorCallback, "IOBlueApp", action, [message]);
};
