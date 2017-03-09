
var temp = 'aaa';

function scanOpenService () {

    alert("Success");

    var code = JSON.stringify(app._getLocalStorageJson('code'));
    var community = JSON.parse(app._getLocalStorage('comInfo'))
    var count = JSONLength(community.info);

    var success = function(message) {alert("Success" + message);};
    var error = function(message) { alert("Oopsie! " + message); };

    var personl = {code:code, count:count};

    /*var obj=new Object();
    obj.personl=personl;*/
    dataTransportPlugins.createEvent(personl, success, error);
}

/**
    掃描功能 var = getscreenshot;
    googleId var = getGoogleId;
    facebookId var = getFacebookId;
    教學導覽 var = openTourTeach;
*/
function getGoogleId (val) {

    temp = val;


    var personl = "";

    var success = function(message) {alert("Success" + message);};
    var error = function(message) { alert("Oopsie! " + message); };

    NativeFeatures.createEvent(personl, success, error);
}

function JSONLength(obj) {
    var size = 0, key;
    for (key in obj) {
    if (obj.hasOwnProperty(key)) size++;
    }
    return size;
    };

var dataTransportPlugins = {

            //在html被调用的方法
            createEvent: function(personl, successCallback, errorCallback) {

                cordova.exec(

                    //成功的回调
                    successCallback,

                    //失败的回调
                    errorCallback,

                    //java类插件名称
                    'DataTransportPlugin',

                    //执行插件的动作  DataTransportPlugin的Action名子
                    'dataTransport',

                    //数据组这里以json形式
                    [{
                      "personl": personl,
                    }]
                );
            }
        }

var NativeFeatures = {

                    //在html被调用的方法
                    createEvent: function(personl, successCallback, errorCallback) {

                        cordova.exec(

                            //成功的回调
                            successCallback,

                            //失败的回调
                            errorCallback,

                            //java类插件名称
                            'DataTransportPlugin',

                            //执行插件的动作  DataTransportPlugin的Action名子
                            temp,

                            //数据组这里以json形式
                            [{
                                "personl": personl,
                            }]
                        );
                    }
                }