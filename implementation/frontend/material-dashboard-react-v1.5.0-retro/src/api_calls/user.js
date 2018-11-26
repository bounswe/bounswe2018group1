import axios from "axios";
import Cookies from "js-cookie";

import constants from "../constants";

class UserRepository {
  static user(sessionID) {
    var userReq = {
     method: 'GET',
     url: `${constants.API}/user`,
     headers: {
         'JSESSIONID': sessionID
     },
     json: true
   };
   return dispatch => {
     return axios(userReq)
     .then(function(response){
       console.log(response.data);
       console.log(response.status);
     })
     .catch(function(error){
       console.log(error);
     });
   }
 }
}

export default UserRepository;
