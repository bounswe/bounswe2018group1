import axios from "axios";
import Cookies from "js-cookie";
import constants from "../constants";

// Cookies.set('JSESSIONID', "5861644F58803FEE504C7974F92C750F");
const token = Cookies.get("JSESSIONID");
axios.defaults.headers.common["Content-Type"] = "application/json";
axios.defaults.withCredentials = true;

class UserRepository {
  static async user() {
    const response = await axios(
      {
        method: 'GET',
        url: `${constants.API}/user`,
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
          "Accept": "application/json",
        }
      }
    )
    return response.data;
  }

  static async updateProfile(user) {
    const response = await axios(
      {
        method: 'PUT',
        url: `${constants.API}/user/info`,
        withCredentials: true,
        crossdomain : true,
        headers: {
          "Content-Type": "application/json",
          "Accept": "application/json",
          // "Access-Control-Allow-Origin": 'http://localhost:1337',
          // "Access-Control-Allow-Methods": 'GET, POST, PATCH, PUT, DELETE, OPTIONS',
          // 'Access-Control-Allow-Headers': "Content-Type"
        },
        data: user
      }
    )
  }
}

export default UserRepository;
