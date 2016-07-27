module.exports = {
	request: function (action, message, successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback, "IOBlueApp", action, [message]);
	}
};