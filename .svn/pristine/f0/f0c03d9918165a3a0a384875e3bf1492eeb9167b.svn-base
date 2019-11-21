var MCS_URL = "http://localhost:8080";

// Monitoring Status
// Interface : GW
// URL : /tgw/statuses
// Request : {"Header":{"Method":"get","Time":"2019-10-22 16:42:28.423","MsgGroup":"0","MsgID":"1"},"Data":{}}
function requestApi(param) {
  return axios
    .post(MCS_URL + "/tgw/statuses", param)
    .then(function(response) {
      debugger;
      console.log(response);
    })
    .catch(function(error) {
      debugger;
      console.log(error);
    });
}

const json = {
  Header: {
    Method: "get",
    Time: "2019-10-22 16:42:28.423",
    MsgGroup: "0",
    MsgID: "1"
  },
  Data: {}
};

// jsonp2(MCS_URL + "/tgw/statuses/0", json);

// jsonp3(MCS_URL + "/tgw/statuses/0", JSON.stringify(json));
